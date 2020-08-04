#!/bin/bash

echo "Performing a clean Maven build"
./mvnw clean package -DskipTests=true

echo "Building the Todo-Tool"
docker build --tag todo-tool .
