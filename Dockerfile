FROM lakruzz/lamj:latest

ENV PORT=8080

COPY src /src
COPY pom.xml /pom.xml
RUN set -ex; \
     mvn -f /pom.xml clean package; \
     mv /target/*.jar /app/; \
     rm -rf /target; \
     rm -rf /src; \
     rm -rf /pom.xml;

EXPOSE $PORT

CMD set -eux; \
    lamj.init.sh; \
    java -jar /app/*.jar;
