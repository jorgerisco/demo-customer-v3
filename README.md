# demo-customer-v3

## Generate JAR file

```sh
cd demo-customer-v3/

mvn package
```

## Generate Docker image

```sh
docker build -f src/main/docker/Dockerfile --build-arg JAR_FILE=target/*.jar -t jariscog89/demo-customer-v3:v3.0 .

docker push jariscog89/demo-customer-v3:v3.0
```

## Deploy

```sh
cd src/main/k8s/

kubectl create -f demo-customer-v3-pod.yaml
kubectl create -f demo-customer-v3-deploy.yaml
kubectl create -f demo-customer-v3-service.yaml
```