ARG BUILDPLATFORM

FROM --platform=$BUILDPLATFORM lscr.io/linuxserver/firefox:amd64-latest AS build

RUN apk add --no-cache openjdk17

WORKDIR /app

COPY ./build/libs/data-extraction-twitch.jar /app

CMD ["java", "-jar", "/app/data-extraction-twitch.jar"]
