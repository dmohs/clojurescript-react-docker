FROM nginx

COPY src/docker/proxy/run-nginx.sh /bin/run-nginx
RUN chmod +x /bin/run-nginx

ENV ALLOWED_ORIGIN='FIXME'
ENV ALLOWED_HEADERS='FIXME'

CMD ["run-nginx"]
