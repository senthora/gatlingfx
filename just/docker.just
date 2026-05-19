compose-base := "./docker-compose.yml"
compose-proxy := "./docker-compose.yml:./docker-compose.proxy.yml"

# Execute docker compose using base topology
compose +args:
    COMPOSE_FILE={{compose-base}}
    docker compose {{args}}

# Execute docker compose using proxy topology
compose-proxy +args:
    COMPOSE_FILE={{compose-proxy}} \
    just compose {{args}}
