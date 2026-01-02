#!/bin/bash

SUMMARY="k6-summary.json"
SERVICE="library-management-system_authors-service"

P95=$(jq '.metrics.http_req_duration.p95' $SUMMARY)

echo "k6 p95 = ${P95}ms"

if (( $(echo "$P95 < 500" | bc -l) )); then
  REPLICAS=1
elif (( $(echo "$P95 < 2500" | bc -l) )); then
  REPLICAS=2
else
  REPLICAS=3
fi

echo "Scaling Authors Service To $REPLICAS Replicas..."

docker service scale ${SERVICE}=${REPLICAS}
