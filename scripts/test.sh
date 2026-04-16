#!/bin/bash
ENV=${1:-dev}

set -o allexport
source .env.$ENV
set +o allexport

./mvnw test "$@"