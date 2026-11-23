"""
HakiCheck AI RAG Corpus Indexer
Ingests, cleans, chunks (~500 tokens, 10-15% overlap), embeds, and indexes
all official Kenyan legal documents into the Vector Store.
Matches the Data Ingestion Pipeline architecture diagram:
Documents -> Load & Clean -> Chunking -> Embedding Model -> Vector Database
"""

import os
import glob
import json
import time
import re
from typing import List, Dict, Any, Tuple
import pypdf
import numpy as np
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.decomposition import TruncatedSVD
from langchain_text_splitters import RecursiveCharacterTextSplitter

BASE_DIR = os.path.dirname(os.path.abspath(__file__))
DOCUMENTS_DIR = os.path.join(BASE_DIR, "documents")
RAG_DOCS_DIR = os.path.join(DOCUMENTS_DIR, "RAG_CONTEXT_DOCUMENTS")
DB_DIR = os.path.join(BASE_DIR, "db")
CHUNKS_FILE = os.path.join(DB_DIR, "rag_chunks.json")
VECTOR_INDEX_FILE = os.path.join(DB_DIR, "vector_index.json")

# Ensure db directory exists
os.makedirs(DB_DIR, exist_ok=True)


def clean_text(text: str) -> str:
    """Strips noise, excessive newlines, line hyphens, and whitespace."""
    if not text:
        return ""
    # Remove hyphenated linebreaks (e.g., elec- \n tion -> election)
    text = re.sub(r'(\w+)-\s*\n\s*(\w+)', r'\1\2', text)
    # Replace multiple whitespace and newlines
    text = re.sub(r'\s+', ' ', text)
    return text.strip()


def extract_document_type(filename: str) -> str:
    lower = filename.lower()
    if "constitution" in lower:
        return "CONSTITUTION"
    elif "act" in lower or "cap" in lower:
        return "STATUTE"
    elif "regulation" in lower or "manual" in lower or "plan" in lower:
        return "REGULATION"
    elif "ruling" in lower or "judgment" in lower or "petition" in lower:
        return "JUDGMENT"
    elif "manifesto" in lower or "policy" in lower:
        return "POLICY"
    return "STATUTE"


def load_and_clean_documents() -> List[Dict[str, Any]]:
    """
    STAGE 1 & 2: Load & Clean
    Loads all PDFs and TXT files, strips noise, and attaches metadata.
    """
    print("[1/4] Loading and cleaning legal documents...")
    raw_docs = []

    # 1. Load Text files
    txt_files = glob.glob(os.path.join(DOCUMENTS_DIR, "*.txt"))
    for fpath in txt_files:
        fname = os.path.basename(fpath)
        try:
            with open(fpath, "r", encoding="utf-8") as f:
                content = f.read()
            cleaned = clean_text(content)
            if cleaned:
                raw_docs.append({
                    "source": fname,
                    "title": fname.replace(".txt", "").replace("_", " "),
                    "page": 1,
                    "document_type": extract_document_type(fname),
                    "text": cleaned
                })
        except Exception as e:
            print(f"  Error reading {fname}: {e}")

    # 2. Load PDF files from RAG_CONTEXT_DOCUMENTS
    pdf_files = glob.glob(os.path.join(RAG_DOCS_DIR, "*.pdf"))
    for fpath in pdf_files:
        fname = os.path.basename(fpath)
        doc_type = extract_document_type(fname)
        try:
            reader = pypdf.PdfReader(fpath)
            for page_idx, page in enumerate(reader.pages):
                page_text = page.extract_text() or ""
                cleaned = clean_text(page_text)
                if len(cleaned) > 40:  # Skip blank pages or decorative covers
                    raw_docs.append({
                        "source": fname,
                        "title": fname.replace(".pdf", "").replace("_", " "),
                        "page": page_idx + 1,
                        "document_type": doc_type,
                        "text": cleaned
                    })
        except Exception as e:
            print(f"  Error reading PDF {fname}: {e}")

    print(f"  Loaded {len(raw_docs)} document pages/sections.")
    return raw_docs


def chunk_documents(raw_docs: List[Dict[str, Any]]) -> List[Dict[str, Any]]:
    """
    STAGE 3: Chunking
    ~500 tokens, 10-15% overlap.
    Uses RecursiveCharacterTextSplitter with chunk_size=1500 chars (~500 tokens)
    and chunk_overlap=150 chars (~10-15% overlap).
    """
    print("[2/4] Chunking documents (~500 tokens, 10-15% overlap)...")
    splitter = RecursiveCharacterTextSplitter(
        chunk_size=1500,
        chunk_overlap=150,
        separators=["\n\n", "\n", ". ", "; ", " ", ""]
    )

    chunks = []
    chunk_counter = 0

    for doc in raw_docs:
        sub_chunks = splitter.split_text(doc["text"])
        for idx, sub_text in enumerate(sub_chunks):
            chunk_counter += 1
            # Extract first sentence for citation snippet title
            first_sent = sub_text.split(". ")[0].strip()
            citation_title = f"{doc['title']} (p. {doc['page']})"
            
            # Keywords extraction for fast semantic pre-filtering
            words = [w.lower() for w in re.findall(r'\b[a-zA-Z]{4,}\b', sub_text)]
            freq: Dict[str, int] = {}
            for w in words:
                freq[w] = freq.get(w, 0) + 1
            top_keywords = [k for k, _ in sorted(freq.items(), key=lambda x: x[1], reverse=True)[:15]]

            chunks.append({
                "id": f"chunk_{chunk_counter}",
                "source": doc["source"],
                "title": first_sent[:80] if len(first_sent) > 15 else citation_title,
                "citation": citation_title,
                "document_type": doc["document_type"],
                "page": doc["page"],
                "text": sub_text,
                "keywords": top_keywords
            })

    print(f"  Created {len(chunks)} chunks.")
    return chunks


def build_dense_vectors(chunks: List[Dict[str, Any]]) -> Tuple[List[List[float]], Dict[str, Any]]:
    """
    STAGE 4: Embedding Model
    Generates normalized dense vector embeddings for each chunk.
    Uses scikit-learn TF-IDF + SVD (128-dimensional dense semantic vectors).
    """
    print("[3/4] Generating dense vector embeddings (D=128)...")
    texts = [c["text"] for c in chunks]

    # Fit TF-IDF Vectorizer
    vectorizer = TfidfVectorizer(
        max_features=4000,
        ngram_range=(1, 2),
        stop_words="english",
        sublinear_tf=True
    )
    tfidf_matrix = vectorizer.fit_transform(texts)

    # Dimensionality reduction to 128 dense semantic dimensions
    n_components = min(128, tfidf_matrix.shape[1] - 1)
    svd = TruncatedSVD(n_components=n_components, random_state=42)
    dense_matrix = svd.fit_transform(tfidf_matrix)

    # L2 normalize vectors for direct dot-product cosine similarity
    norms = np.linalg.norm(dense_matrix, axis=1, keepdims=True)
    norms[norms == 0] = 1.0
    normalized_dense = (dense_matrix / norms).tolist()

    # Model metadata for embedding query runtime (ensure native Python types for JSON)
    model_metadata = {
        "vocabulary": {str(k): int(v) for k, v in vectorizer.vocabulary_.items()},
        "idf": [float(x) for x in vectorizer.idf_],
        "components": [[float(x) for x in row] for row in svd.components_]
    }

    print(f"  Dense embedding matrix generated: shape ({len(normalized_dense)}, {n_components})")
    return normalized_dense, model_metadata


def build_index_cache(force: bool = False) -> Dict[str, Any]:
    """
    STAGE 5: Store in Vector Database
    Saves chunks and vector index to disk cache.
    """
    if not force and os.path.exists(CHUNKS_FILE) and os.path.exists(VECTOR_INDEX_FILE):
        print(f"Loaded existing vector index from {VECTOR_INDEX_FILE}")
        with open(CHUNKS_FILE, "r", encoding="utf-8") as f:
            chunks = json.load(f)
        with open(VECTOR_INDEX_FILE, "r", encoding="utf-8") as f:
            index_data = json.load(f)
        return {"chunks": chunks, "index": index_data}

    start_time = time.time()
    raw_docs = load_and_clean_documents()
    chunks = chunk_documents(raw_docs)
    vectors, model_metadata = build_dense_vectors(chunks)

    print("[4/4] Storing in Vector Database...")
    # Save chunks
    with open(CHUNKS_FILE, "w", encoding="utf-8") as f:
        json.dump(chunks, f, ensure_ascii=False, indent=2)

    # Save vector index
    index_data = {
        "vectors": vectors,
        "model": model_metadata,
        "total_chunks": len(chunks),
        "created_at": time.strftime("%Y-%m-%dT%H:%M:%SZ")
    }
    with open(VECTOR_INDEX_FILE, "w", encoding="utf-8") as f:
        json.dump(index_data, f, ensure_ascii=False)

    duration = time.time() - start_time
    print(f"Vector Database successfully built in {duration:.2f}s!")
    print(f"  Chunks file: {CHUNKS_FILE}")
    print(f"  Vector index: {VECTOR_INDEX_FILE}")

    return {"chunks": chunks, "index": index_data}


if __name__ == "__main__":
    build_index_cache(force=True)
