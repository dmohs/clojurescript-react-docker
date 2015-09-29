#!/bin/bash

CORS_DIRECTIVES=$(cat << 'EOF'
add_header 'Access-Control-Allow-Origin' '--ALLOWED_ORIGIN--';
add_header 'Access-Control-Allow-Credentials' 'true';
add_header 'Access-Control-Allow-Methods' 'GET, POST, PATCH, PUT, DELETE';
add_header 'Access-Control-Allow-Headers' '--ALLOWED_HEADERS--';
EOF
)

DEFAULT_CONF=$(cat << 'EOF'
server {
  listen       80;
  server_name  localhost;

  location / {
    proxy_pass https://api.github.com/;

    proxy_hide_header 'Access-Control-Allow-Origin';
    proxy_hide_header 'Access-Control-Allow-Credentials';
    proxy_hide_header 'Access-Control-Allow-Methods';
    proxy_hide_header 'Access-Control-Allow-Headers';

    if ($request_method = 'OPTIONS') {
      --CORS_DIRECTIVES--

      # Tell client that this pre-flight info is valid for 20 days
      add_header 'Access-Control-Max-Age' 1728000;
      add_header 'Content-Type' 'text/plain charset=UTF-8';
      add_header 'Content-Length' 0;
      return 204;
    }
    if ($request_method = 'POST') {
      --CORS_DIRECTIVES--
    }
    if ($request_method = 'GET') {
      --CORS_DIRECTIVES--
    }
  }
}
EOF
)

CORS_DIRECTIVES=${CORS_DIRECTIVES//'--ALLOWED_ORIGIN--'/"$ALLOWED_ORIGIN"}
CORS_DIRECTIVES=${CORS_DIRECTIVES//'--ALLOWED_HEADERS--'/"$ALLOWED_HEADERS"}
DEFAULT_CONF=${DEFAULT_CONF//'--CORS_DIRECTIVES--'/"$CORS_DIRECTIVES"}

echo "$DEFAULT_CONF" > /etc/nginx/conf.d/default.conf

exec nginx -g 'daemon off;'
