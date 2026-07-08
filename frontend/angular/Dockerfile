FROM docker.io/library/node:24-alpine AS build

WORKDIR /app

RUN apk add --no-cache openjdk21-jre

COPY frontend/angular/package*.json ./
RUN npm ci

COPY openapi /openapi
COPY frontend/angular .

ENV OPENAPI_DIR=/openapi

RUN npm run generate:api
RUN npm run build -- --configuration production --output-path=/app/dist/frontend

FROM docker.io/library/nginx:alpine

COPY frontend/angular/nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /app/dist/frontend/browser /usr/share/nginx/html

EXPOSE 80
