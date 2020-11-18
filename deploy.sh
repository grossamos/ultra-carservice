
# Build project
mvn package
cd ./src/main/angular/dist/angular
npm run build --prod
cp -r ./* ../../../../../target/angular/

# Start minikube
minikube start
minikube addons enable ingress
eval "$(minikube docker-env --shell=bash)"

# Build Docker images
cd ../../../../../
docker build -f ./deployment/Dockerfile-angular -t ultra-service-angular .
docker build -f ./deployment/Dockerfile-springboot -t ultra-service-springboot .

# Apply
kubectl delete -f ./deployment/kubernetes-config.yml
kubectl apply -f ./deployment/kubernetes-config.yml
kubectl delete -f ./deployment/kubernetes.yml
kubectl apply -f ./deployment/kubernetes.yml

# Setup Prometheus
kubectl apply -f ./deployment/kubernetes-prometheus.yml
helm repo add stable https://charts.helm.sh/stable
helm repo update
helm install prometheus-monitoring stable/prometheus-operator --namespace monitoring-ns


# Success message
URL=$(minikube service angular-k8s-service --url)

echo "--------------------------------------"
echo "Started successfully in ${SECONDS} sec"
echo "visit at: http://minikube/ or ${URL}"
echo "--------------------------------------"
