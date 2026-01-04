# HTTP Request Duration Metrics: Monolith vs Microservices

## Monolith Architecture
```json
"http_req_duration": {
    "min": 2.523367,
    "med": 11.9585525,
    "max": 239.711849,
    "p(90)": 27.3232427,
    "p(95)": 33.30906064999999,
    "avg": 14.741453992842526,
    "thresholds": {
        "p(95)<3000": false
    }
},
```

## Microservices Architecture
```json
"http_req_duration": {
    "min": 1.823456,
    "med": 8.456789, 
```