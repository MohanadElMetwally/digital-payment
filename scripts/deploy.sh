#!/usr/bin/env bash

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(dirname "$SCRIPT_DIR")"
cd "$PROJECT_ROOT"

ENV_FILE=".env.stg"
AWS_REGION="eu-central-1"
AWS_ACCOUNT_ID="921040037119"
ECR_URL="$AWS_ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com"
IMAGE_NAME="digital_payment"
TAG=$(date +%Y%m%d%H%M%S)

load_env_variables() {
    if [ -f "$ENV_FILE" ]; then
        echo "Loading environment variables from $ENV_FILE"
        set -a
        source "$ENV_FILE"
        set +a
        return 0
    else
        echo "Warning: Environment file $ENV_FILE not found"
        return 1
    fi
}

build_with_docker_compose() {
    echo "=== Building with Docker Compose ==="

    export COMPOSE_BAKE=true

    set -x
    docker compose build
    { set +x; } 2>/dev/null

    echo "Build completed!"
}

authenticate_with_aws() {
    echo "=== Authenticating with AWS ==="

    # Check if AWS CLI is installed, install if not
    if ! command -v aws &> /dev/null; then
        echo "AWS CLI not found, installing..."
        curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
        unzip awscliv2.zip
        sudo ./aws/install
        rm -rf awscliv2.zip aws/
    fi

    # Authenticate Docker with ECR using AWS credentials
    echo "=== Authenticating Docker with ECR ==="
    aws ecr get-login-password --region $AWS_REGION | \
        docker login --username AWS --password-stdin $ECR_URL

    echo "Authentication successful!"
}

push_to_ecr() {
    echo "=== Pushing image to ECR ==="

    set -x
    docker tag $ECR_URL/$IMAGE_NAME:latest $ECR_URL/$IMAGE_NAME:$TAG
    docker push $ECR_URL/$IMAGE_NAME:$TAG
    docker push $ECR_URL/$IMAGE_NAME:latest
    { set +x; } 2>/dev/null

    echo "Image pushed successfully!"
}

update_ecs_env_vars() {
    echo "=== Updating Environment Variables in ECS ==="

    load_env_variables

    ECS_CLUSTER="digital-payment-cluster"
    ECS_SERVICE="digital-payment-task-service"

    set -x
    aws ecs update-service \
        --cluster $ECS_CLUSTER \
        --service $ECS_SERVICE \
        --force-new-deployment \
        --region $AWS_REGION
    { set +x; } 2>/dev/null

    echo "ECS service updated successfully!"
}

deploy_to_aws() {
    build_with_docker_compose
    authenticate_with_aws

    read -p "Do you want to update environment variables in ECS from $ENV_FILE? (y/n): " update_env
    if [[ $update_env == "y" || $update_env == "Y" ]]; then
        update_ecs_env_vars
    fi

    push_to_ecr

    echo "=== Deployment completed successfully! ==="
    echo "Image: $ECR_URL/$IMAGE_NAME:$TAG"
}

deploy_to_aws