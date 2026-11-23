"""
HakiCheck AI RAG Backend API Server
Serves the RAG Verification Pipeline to the Android Mobile Application.
Compatible with both FastAPI (if installed) and Flask (built-in).
Runs on port 8000 (Android emulator localhost alias: 10.0.2.2:8000).
"""

import os
import sys
import json
from typing import List, Dict, Any, Optional
from dotenv import load_dotenv

BASE_DIR = os.path.dirname(os.path.abspath(__file__))
sys.path.insert(0, BASE_DIR)
load_dotenv(os.path.join(BASE_DIR, ".env"))
load_dotenv(os.path.join(os.path.dirname(BASE_DIR), ".env"))

from rag_engine import verify_claim_engine, load_vector_store

# Check if FastAPI is available
HAS_FASTAPI = False
try:
    from fastapi import FastAPI, HTTPException
    from fastapi.middleware.cors import CORSMiddleware
    from pydantic import BaseModel
    HAS_FASTAPI = True
except ImportError:
    HAS_FASTAPI = False

# ==============================================================================
# 1. FLASK IMPLEMENTATION (Lightweight, zero-config, pre-installed)
# ==============================================================================
def create_flask_app():
    from flask import Flask, request, jsonify, make_response
    app = Flask("HakiCheckBackend")

    # Add CORS Headers to all responses
    @app.after_request
    def add_cors_headers(response):
        response.headers["Access-Control-Allow-Origin"] = "*"
        response.headers["Access-Control-Allow-Headers"] = "Content-Type,Authorization"
        response.headers["Access-Control-Allow-Methods"] = "GET,POST,OPTIONS"
        return response

    @app.route("/", methods=["GET"])
    def root():
        chunks, vectors, _ = load_vector_store()
        return jsonify({
            "app": "HakiCheck AI Civic Assistant Backend",
            "version": "1.0.0",
            "status": "online",
            "institution": "Strathmore University • SCES",
            "knowledge_corpus": {
                "total_chunks": len(chunks),
                "embedding_dimensions": int(vectors.shape[1]),
                "documents_dir": "backend/documents/RAG_CONTEXT_DOCUMENTS"
            }
        })

    @app.route("/api/v1/health", methods=["GET"])
    def health():
        chunks, vectors, _ = load_vector_store()
        has_openai = bool(os.getenv("OPENAI_API_KEY") and len(os.getenv("OPENAI_API_KEY")) > 20)
        return jsonify({
            "status": "healthy",
            "engine": "LangChain RAG (GPT-4o-mini)" if has_openai else "Dense Vector Semantic RAG + Grounded Kenyan Legal Reasoner",
            "indexed_chunks": len(chunks),
            "vector_dimensions": int(vectors.shape[1]),
            "openai_configured": has_openai
        })

    @app.route("/api/v1/verify", methods=["POST", "OPTIONS"])
    def verify():
        if request.method == "OPTIONS":
            return make_response("", 200)

        data = request.get_json(silent=True) or {}
        claim = data.get("claim", "").strip()

        if not claim or len(claim) < 3:
            return jsonify({"error": "Claim text is too short. Please provide at least 3 characters."}), 400

        print(f"\n[INCOMING QUERY] '{claim}' (County: {data.get('county')})", flush=True)
        result = verify_claim_engine(
            claim=claim,
            county=data.get("county", "47 - Nairobi City"),
            language=data.get("language", "English")
        )
        print(f"[VERDICT RESULT] {result.get('verdict')} ({result.get('confidence_score')}%) -> {result.get('summary')[:80]}...\n", flush=True)
        return jsonify(result)

    @app.route("/api/v1/trending", methods=["GET"])
    def trending():
        trending_samples = [
            "Kenyan citizens must pay Ksh 500 fee to register as a voter in 2027.",
            "IEBC announced electronic voting replaces ballot papers completely.",
            "Any registered Kenyan citizen aged 18+ can run for County Assembly without a degree.",
            "Voting day is declared a mandatory public holiday in Kenya.",
            "Presidential election results declared on Form 34A at the polling station are final."
        ]
        results = [verify_claim_engine(claim) for claim in trending_samples]
        return jsonify(results)

    @app.route("/api/v1/documents", methods=["GET"])
    def documents():
        chunks, _, _ = load_vector_store()
        doc_summary: Dict[str, Dict[str, Any]] = {}
        for c in chunks:
            src = c["source"]
            if src not in doc_summary:
                doc_summary[src] = {
                    "source": src,
                    "title": c.get("title", src),
                    "document_type": c.get("document_type", "STATUTE"),
                    "chunks": 0,
                    "sample_page": c.get("page", 1)
                }
            doc_summary[src]["chunks"] += 1
        return jsonify(list(doc_summary.values()))

    return app

# ==============================================================================
# 2. FASTAPI IMPLEMENTATION (If FastAPI is installed)
# ==============================================================================
if HAS_FASTAPI:
    fastapi_app = FastAPI(
        title="HakiCheck AI RAG Backend API",
        description="Grounded Fact-Checking against the Constitution of Kenya 2010 and electoral statutes.",
        version="1.0.0"
    )

    fastapi_app.add_middleware(
        CORSMiddleware,
        allow_origins=["*"],
        allow_credentials=True,
        allow_methods=["*"],
        allow_headers=["*"],
    )

    class VerificationRequest(BaseModel):
        claim: str
        county: Optional[str] = "47 - Nairobi City"
        language: Optional[str] = "English"
        include_sources: Optional[bool] = True

    @fastapi_app.get("/")
    def read_root():
        chunks, vectors, _ = load_vector_store()
        return {
            "app": "HakiCheck AI Civic Assistant Backend",
            "version": "1.0.0",
            "status": "online",
            "institution": "Strathmore University • SCES",
            "knowledge_corpus": {
                "total_chunks": len(chunks),
                "embedding_dimensions": int(vectors.shape[1]),
                "documents_dir": "backend/documents/RAG_CONTEXT_DOCUMENTS"
            }
        }

    @fastapi_app.get("/api/v1/health")
    def health_check():
        chunks, vectors, _ = load_vector_store()
        has_openai = bool(os.getenv("OPENAI_API_KEY") and len(os.getenv("OPENAI_API_KEY")) > 20)
        return {
            "status": "healthy",
            "engine": "LangChain RAG (GPT-4o-mini)" if has_openai else "Dense Vector Semantic RAG + Grounded Kenyan Legal Reasoner",
            "indexed_chunks": len(chunks),
            "vector_dimensions": int(vectors.shape[1]),
            "openai_configured": has_openai
        }

    @fastapi_app.post("/api/v1/verify")
    def verify_endpoint(req: VerificationRequest):
        if not req.claim or len(req.claim.strip()) < 3:
            raise HTTPException(status_code=400, detail="Claim is too short.")
        return verify_claim_engine(claim=req.claim, county=req.county, language=req.language)

    @fastapi_app.get("/api/v1/trending")
    def trending_endpoint():
        trending_samples = [
            "Kenyan citizens must pay Ksh 500 fee to register as a voter in 2027.",
            "IEBC announced electronic voting replaces ballot papers completely.",
            "Any registered Kenyan citizen aged 18+ can run for County Assembly without a degree.",
            "Voting day is declared a mandatory public holiday in Kenya.",
            "Presidential election results declared on Form 34A at the polling station are final."
        ]
        return [verify_claim_engine(claim) for claim in trending_samples]


if __name__ == "__main__":
    port = int(os.getenv("PORT", 8000))
    print("=" * 70)
    print("HakiCheck AI RAG Backend API Server")
    print(f"Listening on: http://0.0.0.0:{port} (Emulator alias: http://10.0.2.2:{port})")
    has_key = bool(os.getenv("OPENAI_API_KEY") and len(os.getenv("OPENAI_API_KEY")) > 20)
    print(f"OpenAI GPT-4o-mini Integration: {'ENABLED (Live LLM)' if has_key else 'DISABLED'}")
    print("Pre-loading vector database into memory...")
    chunks, vectors, _ = load_vector_store()
    print(f"Loaded {len(chunks)} legal chunks with {vectors.shape[1]}-dimensional embeddings.")
    print("=" * 70)

    if HAS_FASTAPI:
        import uvicorn
        uvicorn.run(fastapi_app, host="0.0.0.0", port=port)
    else:
        flask_app = create_flask_app()
        flask_app.run(host="0.0.0.0", port=port, debug=False)
