#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(dirname "$SCRIPT_DIR")"
cd "$PROJECT_ROOT"

ENV_FILE=".env.stg"
AWS_REGION="eu-central-1"
AWS_ACCOUNT_ID="921040037119"
ECR_URL="$AWS_ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com"
IMAGE_NAME="digital_payment"
TAG=$(date +%Y%m%d%H%M%S)-$(git rev-parse --short HEAD)

ECS_CLUSTER="digital-payment-cluster"
ECS_SERVICE="digital-payment-task-service"     # the running service
ECS_TASK_FAMILY="digital-payment-task"          # the task definition family (NOT the same as service name)
ECS_CONTAINER_NAME="${ECS_CONTAINER_NAME:-digital-payment}"

# y = overwrite the container's environment vars in the new task def from .env.stg
# n (default) = leave environment vars as they currently are in the task definition
UPDATE_ENV="${UPDATE_ENV:-n}"

FULL_IMAGE_TAGGED="$ECR_URL/$IMAGE_NAME:$TAG"
FULL_IMAGE_LATEST="$ECR_URL/$IMAGE_NAME:latest"

build_with_docker_compose() {
    echo "=== Building with Docker Compose (tag: $TAG) ==="
    export COMPOSE_BAKE=true
    export IMAGE_TAG="$TAG"
    set -x
    docker compose build
    { set +x; } 2>/dev/null
    docker tag "$FULL_IMAGE_TAGGED" "$FULL_IMAGE_LATEST"
    echo "Build completed!"
}

authenticate_with_aws() {
    echo "=== Authenticating with AWS ==="
    if ! command -v aws &> /dev/null; then
        echo "AWS CLI not found, installing..."
        curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
        unzip -q awscliv2.zip
        sudo ./aws/install
        rm -rf awscliv2.zip aws/
    fi
    echo "=== Authenticating Docker with ECR ==="
    aws ecr get-login-password --region "$AWS_REGION" | \
        docker login --username AWS --password-stdin "$ECR_URL"
    echo "Authentication successful!"
}

push_to_ecr() {
    echo "=== Pushing images to ECR ==="
    set -x
    docker push "$FULL_IMAGE_TAGGED"
    docker push "$FULL_IMAGE_LATEST"
    { set +x; } 2>/dev/null
    echo "Images pushed successfully!"
}

# Parses KEY=VALUE lines from .env.stg into a JSON array of {name, value},
# skipping blank lines and comments, stripping surrounding quotes from values.
build_env_json_from_file() {
    if [ ! -f "$ENV_FILE" ]; then
        echo "Error: UPDATE_ENV=y but $ENV_FILE not found" >&2
        exit 1
    fi

    grep -vE '^\s*#|^\s*$' "$ENV_FILE" | sed -E 's/^export[[:space:]]+//' | jq -R -n '
        [inputs
         | capture("^(?<name>[^=]+)=(?<value>.*)$")? 
         | select(. != null)
         | {name, value: (.value | gsub("^\"|\"$"; ""))}]
    '
}

deploy_new_task_definition() {
    echo "=== Registering new ECS task definition ==="

    if ! command -v jq &> /dev/null; then
        echo "jq not found, installing..."
        sudo apt-get update -qq && sudo apt-get install -y -qq jq
    fi

    CURRENT_TASK_DEF=$(aws ecs describe-task-definition \
        --task-definition "$ECS_TASK_FAMILY" \
        --region "$AWS_REGION" \
        --query 'taskDefinition')

    JQ_FILTER='(.containerDefinitions[] | select(.name == $NAME) | .image) = $IMAGE'

    if [[ "$UPDATE_ENV" == "y" || "$UPDATE_ENV" == "Y" ]]; then
        echo "=== UPDATE_ENV=y: syncing environment vars from $ENV_FILE ==="
        ENV_JSON=$(build_env_json_from_file)
        NEW_TASK_DEF=$(echo "$CURRENT_TASK_DEF" | jq \
            --arg IMAGE "$FULL_IMAGE_TAGGED" \
            --arg NAME "$ECS_CONTAINER_NAME" \
            --argjson NEWENV "$ENV_JSON" \
            "$JQ_FILTER
             | (.containerDefinitions[] | select(.name == \$NAME) | .environment) = \$NEWENV
             | del(.taskDefinitionArn, .revision, .status, .requiresAttributes,
                   .compatibilities, .registeredAt, .registeredBy)")
    else
        echo "=== UPDATE_ENV=n: leaving existing environment vars as-is ==="
        NEW_TASK_DEF=$(echo "$CURRENT_TASK_DEF" | jq \
            --arg IMAGE "$FULL_IMAGE_TAGGED" \
            --arg NAME "$ECS_CONTAINER_NAME" \
            "$JQ_FILTER
             | del(.taskDefinitionArn, .revision, .status, .requiresAttributes,
                   .compatibilities, .registeredAt, .registeredBy)")
    fi

    NEW_REVISION_ARN=$(aws ecs register-task-definition \
        --region "$AWS_REGION" \
        --cli-input-json "$NEW_TASK_DEF" \
        --query 'taskDefinition.taskDefinitionArn' \
        --output text)

    echo "Registered new task definition: $NEW_REVISION_ARN"

    echo "=== Updating ECS service to new revision ==="
    aws ecs update-service \
        --cluster "$ECS_CLUSTER" \
        --service "$ECS_SERVICE" \
        --task-definition "$NEW_REVISION_ARN" \
        --region "$AWS_REGION" > /dev/null

    echo "Waiting for service to stabilize..."
    aws ecs wait services-stable \
        --cluster "$ECS_CLUSTER" \
        --services "$ECS_SERVICE" \
        --region "$AWS_REGION"

    echo "Service stable on: $NEW_REVISION_ARN"
}

deploy_to_aws() {
    build_with_docker_compose
    authenticate_with_aws
    push_to_ecr
    deploy_new_task_definition
    echo "=== Deployment completed successfully! ==="
    echo "Deployed image: $FULL_IMAGE_TAGGED"
    echo "Environment vars updated: $UPDATE_ENV"
}

deploy_to_aws
