#!/bin/bash

ENV=${1:-dev}

set -o allexport
if [ "$ENV" == "dev" ]; then
    source .env.dev
elif [ "$ENV" == "stg" ]; then
    source .env.stg
elif [ "$ENV" == "prod" ]; then
    source .env.prod
else
    echo "Unknown environment: $ENV"
    exit 1
fi
set +o allexport

./mvnw spring-boot:run