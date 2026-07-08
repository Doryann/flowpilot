FROM docker.io/library/node:24-alpine AS build

WORKDIR /app

RUN apk add --no-cache openjdk21-jre

COPY frontend/react/package*.json ./
RUN npm ci

COPY openapi /openapi
COPY frontend/react .

ENV OPENAPI_DIR=/openapi

RUN npm run generate:api
RUN npm run build

FROM docker.io/library/nginx:alpine

COPY frontend/react/nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /app/dist /usr/share/nginx/html

EXPOSE 80