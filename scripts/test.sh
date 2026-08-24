#!/bin/bash

set -euo pipefail

ENV=${1:-}

if [ -n "$ENV" ]; then
    shift || true

    case "$ENV" in
        dev)  ENV_FILE=".env.dev" ;;
        stg)  ENV_FILE=".env.stg" ;;
        prod) ENV_FILE=".env.prod" ;;
        *)
            echo "Unknown environment: $ENV" >&2
            exit 1
            ;;
    esac

    # Ensure the requested environment file actually exists
    if [ ! -f "$ENV_FILE" ]; then
        echo "Error: $ENV_FILE not found" >&2
        exit 1
    fi

    set -o allexport
    source "$ENV_FILE"
    set +o allexport
else
    echo "No environment specified. Skipping .env files and using raw system variables."
fi

./mvnw test "$@"