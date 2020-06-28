#!/usr/bin/env bash

set -eo pipefail

modules=( account-api customer-api gateway-service order-api )

for module in "${modules[@]}"; do
    A="$(cut -d'-' -f1 <<< ${module})"
    docker build -t "00934068/mboaeat-${A}-repository:latest" ${module}
done
