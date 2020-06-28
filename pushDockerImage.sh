#!/usr/bin/env bash

set -eo pipefail

modules=( account customer gateway order )
DOCKER_USERNAME="00934068"
DOCKER_PASSWORD="170182tresor"
echo "${DOCKER_PASSWORD}" | docker login -u "${DOCKER_USERNAME}" --password-stdin
for module in "${modules[@]}"; do
    docker push "00934068/mboaeat-${module}-repository:latest"
done
