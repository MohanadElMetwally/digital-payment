#!/bin/bash

set -euo pipefail

ENV=${1:-}

if [ -z "$ENV" ]; then
    ENV_FILE=".env"
else
    shift || true

    case "$ENV" in
        dev)  ENV_FILE=".env.dev" ;;
        stg)  ENV_FILE=".env.stg" ;;
        prod) ENV_FILE=".env.prod" ;;
        *)
            echo "Unknown environment: $ENV"
            exit 1
            ;;
    esac
fi

if [ ! -f "$ENV_FILE" ]; then
    echo "Error: $ENV_FILE not found" >&2
    exit 1
fi

set -o allexport
source "$ENV_FILE"
set +o allexport

./mvnw test "$@"
