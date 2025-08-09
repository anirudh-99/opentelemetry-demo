- docker build --build-arg JAR_FILE="my-producer-0.0.1-SNAPSHOT.jar" -t producer-image .
- docker build --build-arg JAR_FILE="my-consumer-0.0.1-SNAPSHOT.jar" -t consumer-image .

- kind create cluster --config kind-config.yaml
- kind load docker-image producer-image consumer-image
- helm install my-release ./my-chart

