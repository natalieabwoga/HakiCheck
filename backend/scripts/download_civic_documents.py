"""
HakiCheck Civic Documents Automated Downloader
Downloads official Kenyan legal and electoral statutory documents from Kenya Law (eKLR),
IEBC, and Parliament repositories to enrich the RAG knowledge corpus.

Saves PDFs into: backend/documents/RAG_CONTEXT_DOCUMENTS/
"""

import os
import sys
import time
import argparse
import requests
from typing import List, Dict

DOCS_DIR = os.path.join(os.path.dirname(os.path.dirname(__file__)), "documents", "RAG_CONTEXT_DOCUMENTS")

# Authoritative Kenyan Civic and Electoral Documents Registry
KENYAN_LEGAL_DOCUMENTS: List[Dict[str, str]] = [
    {
        "filename": "Elections_Act_Cap_7.pdf",
        "title": "Elections Act No. 24 of 2011 (Cap. 7)",
        "primary_url": "http://kenyalaw.org/kl/fileadmin/pdfdownloads/Acts/ElectionsAct_No24of2011.pdf",
        "mirror_url": "https://new.kenyalaw.org/akn/ke/act/2011/24/eng@2017-12-28/source.pdf",
        "category": "STATUTE",
        "description": "Governs conduct of elections, voter registration, qualification of candidates, and tallying."
    },
    {
        "filename": "IEBC_Act_Cap_7C.pdf",
        "title": "Independent Electoral and Boundaries Commission Act No. 9 of 2011 (Cap. 7C)",
        "primary_url": "http://kenyalaw.org/kl/fileadmin/pdfdownloads/Acts/IndependentElectoralandBoundariesCommissionAct_No9of2011.pdf",
        "mirror_url": "https://www.iebc.or.ke/uploads/resources/IEBC_ACT_2011.pdf",
        "category": "STATUTE",
        "description": "Establishment, mandate, commissioners, and operational independence of the IEBC."
    },
    {
        "filename": "National_Cohesion_and_Integration_Act_Cap_79B.pdf",
        "title": "National Cohesion and Integration Act No. 12 of 2008 (Cap. 79B)",
        "primary_url": "http://kenyalaw.org/kl/fileadmin/pdfdownloads/Acts/NationalCohesionandIntegrationAct_No12of2008.pdf",
        "mirror_url": "https://www.cohesion.or.ke/index.php/resources/acts/category/1-acts?download=1:nci-act-2008",
        "category": "STATUTE",
        "description": "Prohibits hate speech, ethnic contempt, and incitement ahead of elections."
    },
    {
        "filename": "Public_Order_Act_Cap_56.pdf",
        "title": "Public Order Act (Cap. 56 Laws of Kenya)",
        "primary_url": "http://kenyalaw.org/kl/fileadmin/pdfdownloads/Acts/PublicOrderActCap56.pdf",
        "mirror_url": "https://new.kenyalaw.org/akn/ke/act/1950/56/eng@2014-12-15/source.pdf",
        "category": "STATUTE",
        "description": "Regulates public gatherings, political rallies, marches, and security notifications."
    },
    {
        "filename": "County_Governments_Act_2012.pdf",
        "title": "County Governments Act No. 17 of 2012",
        "primary_url": "http://kenyalaw.org/kl/fileadmin/pdfdownloads/Acts/CountyGovernmentsActNo17of2012.pdf",
        "mirror_url": "https://new.kenyalaw.org/akn/ke/act/2012/17/eng@2019-12-19/source.pdf",
        "category": "STATUTE",
        "description": "Powers and operations of County Assemblies, Governors, and citizen participation."
    }
]

HEADERS = {
    "User-Agent": "HakiCheck-CivicAssistant/1.0 (Academic Research; Strathmore University SCES; contact: info@strathmore.edu)",
    "Accept": "application/pdf,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"
}


def download_file(url: str, dest_path: str, timeout: int = 40) -> bool:
    """Downloads a file with streaming, progress tracking, and validation."""
    try:
        response = requests.get(url, headers=HEADERS, stream=True, timeout=timeout)
        if response.status_code == 200:
            with open(dest_path, "wb") as f:
                for chunk in response.iter_content(chunk_size=8192):
                    if chunk:
                        f.write(chunk)
            
            # Verify file has non-trivial size
            size = os.path.getsize(dest_path)
            if size > 1024:  # At least 1 KB
                return True
            else:
                os.remove(dest_path)
                return False
        else:
            return False
    except Exception as e:
        return False


def run_downloader(force: bool = False, trigger_reindex: bool = False):
    os.makedirs(DOCS_DIR, exist_ok=True)
    print("=" * 70)
    print("HakiCheck Kenya Civic & Electoral Documents Downloader")
    print(f"Destination: {DOCS_DIR}")
    print("=" * 70)

    downloaded = 0
    already_exist = 0
    failed = 0

    for doc in KENYAN_LEGAL_DOCUMENTS:
        filename = doc["filename"]
        dest_path = os.path.join(DOCS_DIR, filename)

        if os.path.exists(dest_path) and not force:
            size_kb = os.path.getsize(dest_path) / 1024
            print(f"[EXISTS] {doc['title']} ({size_kb:.1f} KB)")
            already_exist += 1
            continue

        print(f"[DOWNLOADING] {doc['title']}...")
        success = download_file(doc["primary_url"], dest_path)
        if not success and doc.get("mirror_url"):
            print(f"  Attempting mirror: {doc['mirror_url']}...")
            success = download_file(doc["mirror_url"], dest_path)

        if success:
            size_kb = os.path.getsize(dest_path) / 1024
            print(f"  [SUCCESS] Saved {filename} ({size_kb:.1f} KB)")
            downloaded += 1
        else:
            print(f"  [SKIPPED/UNAVAILABLE] Could not reach remote server for {filename}.")
            failed += 1

    print("\n" + "=" * 70)
    print(f"Download Summary: {downloaded} downloaded, {already_exist} already present, {failed} pending.")
    print("=" * 70)

    # Optional re-indexing trigger
    if trigger_reindex and (downloaded > 0 or force):
        print("\nTriggering RAG Vector Index rebuild...")
        try:
            from backend.build_index import build_index_cache
            build_index_cache(force=True)
        except Exception as e:
            print(f"Re-index error: {e}")


if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="Download official Kenyan civic and legal documents for RAG.")
    parser.add_argument("--force", action="store_true", help="Force redownload existing files.")
    parser.add_argument("--reindex", action="store_true", help="Trigger index rebuild after download.")
    args = parser.parse_args()

    run_downloader(force=args.force, trigger_reindex=args.reindex)
