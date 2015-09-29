FROM phusion/baseimage

# How to install OpenJDK 8 from:
# http://ubuntuhandbook.org/index.php/2015/01/install-openjdk-8-ubuntu-14-04-12-04-lts/
# Standard apt-get cleanup from phusion/baseimage.
# Run these all together to make this layer as small as possible.
RUN \
  add-apt-repository ppa:openjdk-r/ppa \
  && apt-get update \
  && apt-get install -y -qq --no-install-recommends \
    openjdk-8-jdk \
    php5-cli \
    rlfe \
  && apt-get -yq autoremove \
    && apt-get -yq clean \
    && rm -rf /var/lib/apt/lists/* \
    && rm -rf /tmp/* \
    && rm -rf /var/tmp/*

RUN curl https://raw.githubusercontent.com/technomancy/leiningen/stable/bin/lein > /usr/bin/lein
RUN chmod 755 /usr/bin/lein
# Tell lein that running as root is okay.
ENV LEIN_ROOT=1
# Actually install leiningen.
RUN lein --version

RUN mkdir /app
WORKDIR /app
COPY example/project.clj project.clj
# Install cljsbuild and basic dependencies.
RUN lein cljsbuild once

CMD [ "lein", "cljsbuild", "once" ]
