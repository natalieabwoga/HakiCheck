import urllib.request
import json

queries = [
    "Kenyan citizens must pay Ksh 500 fee to register as a voter in 2027.",
    "Any registered Kenyan citizen aged 18+ can run for County Assembly without a degree.",
    "I can run for the president as a woman",
    "Linda Mama free maternity healthcare has been abolished under SHIF",
    "Senator Edwin Sifuna sponsored a bill to ban public protests"
]

for q in queries:
    req = urllib.request.Request(
        'http://127.0.0.1:8000/api/v1/verify',
        data=json.dumps({'claim': q}).encode('utf-8'),
        headers={'Content-Type': 'application/json'}
    )
    try:
        with urllib.request.urlopen(req, timeout=15) as res:
            data = json.loads(res.read().decode('utf-8'))
            print(f"==================================================")
            print(f"QUERY: {q}")
            print(f"VERDICT: {data.get('verdict')}")
            print(f"SUMMARY: {data.get('summary')}")
    except Exception as e:
        print(f"[ERROR] -> {q}: {e}")
