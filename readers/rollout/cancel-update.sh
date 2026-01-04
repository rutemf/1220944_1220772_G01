#!/bin/bash

V1_SERVICE="readers_readers-v1"
V2_SERVICE="readers_readers-v2"

echo "Scaling ${V1_SERVICE} To 0 Replicas..."
docker service update --replicas 2 ${V1_SERVICE}

echo "Scaling ${V2_SERVICE} To 3 Replicas..."
docker service update --replicas 0 ${V2_SERVICE}

echo "Cancel Update Complete!"