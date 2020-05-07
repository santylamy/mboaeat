#!/usr/bin/env bash

set -eo pipefail

modules=( account-api customer-api gateway-service)

for module in "${modules[@]}"; do
    docker build -t "mboaeat/${module}:latest" ${module}
done