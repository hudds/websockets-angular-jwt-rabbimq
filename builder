#!/bin/sh
docker build -t hudsonprojects/wajr-backend ./backend
docker build -t hudsonprojects/wajr-api ./api
docker build -t hudsonprojects/wajr-backend-nginx ./docker/backend-nginx
docker build -t hudsonprojects/wajr-api-nginx ./docker/courses-api-nginx