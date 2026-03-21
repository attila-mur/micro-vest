#!/bin/sh
# Replace nginx listen port with Render's PORT (defaults to 10000)
PORT="${PORT:-10000}"
sed -i "s/listen 80/listen ${PORT}/" /etc/nginx/http.d/default.conf

exec supervisord -c /etc/supervisord.conf
