FROM nginx:1.31.0-alpine3.23-slim

COPY conf.d /etc/nginx/conf.d
COPY templates /etc/nginx/templates

HEALTHCHECK --interval=5s --timeout=3s --start-period=2s --retries=5 \
    CMD wget --spider -q http://127.0.0.1/health
