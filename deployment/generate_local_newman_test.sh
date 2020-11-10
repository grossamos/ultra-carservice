cd ../src/test/newman/
cp ./test_ulta_carservice.kubernetes.postman_collection.json test_ulta_carservice.local.postman_collection.json
sed -i 's/minikube/localhost:8080/g' test_ulta_carservice.local.postman_collection.json
