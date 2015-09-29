# clojurescript-react-docker
Docker images for ClojureScript/React-based projects.

## Building

Pull the docker image to build your code:
```bash
docker pull dmohs/cljs-react-build
```

From your project's root directory (which should contain your `project.clj`, see the `example` folder in this repo for an example):
```bash
docker run --rm -it -v "$PWD":/app dmohs/cljs-react-build lein cljsbuild once
```

Also build index.html for development:
```bash
docker run --rm -it -v "$PWD":/app dmohs/cljs-react-build php src/php/index.php > target/index.html
```

## Serving

Pull the docker image to serve files via nginx:
```bash
docker pull dmohs/nginx
```

Start the server with your build directory as the document root:
```bash
docker run --rm -v "$PWD"/target:/usr/share/nginx/html:ro -p 80:80 dmohs/nginx
```

Your application is now visible at:
```
http://docker-machine-hostname-or-ip/
```

## Hot-Reloading Using Figwheel

Start with a clean build directory, then recreate index.html:
```bash
rm -rf target/*
docker run --rm -it -v "$PWD":/app dmohs/cljs-react-build php src/php/index.php > target/index.html
```
*Note that removing `target` itself would require an nginx restart, since nginx is serving from within the build directory.*

Start Figwheel:
```bash
docker run --rm -it -v "$PWD":/app -p 3449:3449 -e BUILD_HOST=docker-machine-hostname-or-ip dmohs/cljs-react-build rlfe lein figwheel
```

Reload your browser to connect to Figwheel's websocket. Check the console for connection status.

## CORS Proxy

Want to use a REST API that doesn't support CORS? No problem, just proxy it:
```bash
docker pull dmohs/cors-proxy
docker run --rm -it -p 8088:80 -e ALLOWED_ORIGIN='http://docker-machine-hostname-or-ip' -e ALLOWED_HEADERS='X-My-Header-1, X-My-Header-2' dmohs/cors-proxy
```
