kubectl apply -f postgres-secret.yaml
kubectl apply -f car-service-secret.yaml
kubectl apply -f car-service-configmap.yaml
kubectl apply -f postgres-service.yaml
kubectl apply -f postgres-statefulset.yaml
kubectl apply -f car-service-deployment.yaml
kubectl apply -f car-service-service.yaml