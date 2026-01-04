# HTTP Request Duration Metrics: Monolith vs Microservices

The performance test was designed to measure the HTTP request duration when fetching a book by its ISBN. We ran the same 
test on both architectures — the monolith and the microservices — to compare their response times and efficiency.

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

In the monolith architecture, the median request duration was around 12ms, with a 95th percentile of ~33ms. The maximum 
request took ~240ms, showing some outliers under high load.

## Microservices Architecture
```json
"http_req_duration": {
    "min": 1.823456,
    "med": 8.456789, 
```