FROM nginx:1.31.0-alpine3.23-slim

RUN apk add --no-cache openssl

RUN mkdir -p /etc/nginx/certs

RUN openssl req -x509 -nodes -days 365 \
    -newkey rsa:2048 \
    -keyout /etc/nginx/certs/localhost.key \
    -out /etc/nginx/certs/localhost.crt \
    -subj "/CN=localhost"

COPY ./nginx/conf /etc/nginx/conf
COPY ./nginx/conf.d /etc/nginx/conf.d
COPY ./nginx/templates /etc/nginx/templates

HEALTHCHECK --interval=5s --timeout=3s --start-period=2s --retries=5 \
    CMD wget --spider -q http://127.0.0.1/health
