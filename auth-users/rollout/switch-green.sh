#!/bin/bash

V1_SERVICE="auth-users_auth-service"
V2_SERVICE="auth-users_auth-users--green"

IMAGE_REPO="miguel04cardoso/readers-service"
LATEST_TAG="latest"
GREEN_TAG="green"

echo "Scaling ${V2_SERVICE} To 2 Replicas..."
docker service update --replicas 2 ${V2_SERVICE}

echo "Scaling ${V1_SERVICE} To 0 Replicas..."
docker service update --replicas 0 ${V1_SERVICE}

echo "Tagging ${IMAGE_REPO}:${GREEN_TAG} As ${LATEST_TAG}..."
docker tag ${IMAGE_REPO}:${GREEN_TAG} ${IMAGE_REPO}:${LATEST_TAG}

echo "Pushing ${IMAGE_REPO}:${LATEST_TAG} To Docker Hub..."
docker push ${IMAGE_REPO}:${LATEST_TAG}

echo "Blue/Green Rollout Complete!"