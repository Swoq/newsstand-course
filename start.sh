#!/bin/bash
mvn clean install
mvn compile jib:build
minikube start
kubectl create secret generic pgpassword --from-literal=PGPASSWORD=1
minikube addons enable ingress
kubectl apply -f k8s/postgres
kubectl apply -f k8s/