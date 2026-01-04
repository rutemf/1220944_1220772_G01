#!/bin/bash

V1_SERVICE="readers_readers-v1"
V2_SERVICE="readers_readers-v2"

IMAGE_REPO="miguel04cardoso/readers-service"
CANARY_TAG="canary"
LATEST_TAG="latest"

echo "Scaling ${V2_SERVICE} To 3 Replicas..."
docker service update --replicas 3 ${V2_SERVICE}

echo "Scaling ${V1_SERVICE} To 0 Replicas..."
docker service update --replicas 0 ${V1_SERVICE}

echo "Tagging ${IMAGE_REPO}:${CANARY_TAG} As ${LATEST_TAG}..."
docker tag ${IMAGE_REPO}:${CANARY_TAG} ${IMAGE_REPO}:${LATEST_TAG}

echo "Pushing ${IMAGE_REPO}:${LATEST_TAG} To Docker Hub..."
docker push ${IMAGE_REPO}:${LATEST_TAG}

echo "Canary Rollout Complete!"