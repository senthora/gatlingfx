compose-core := "./core/compose.test.yml"
compose-proxy := "./proxy/compose.yml:./proxy/compose.test.yml"
compose-file := compose-core + ":" + compose-proxy

# Execute gatlingfx docker compose command
compose +args:
    COMPOSE_FILE={{compose-file}} \
    docker compose -p gatlingfx {{args}}
