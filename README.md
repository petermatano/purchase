# Purchase API
Purchase API is a REST based microservice created with Spring Boot. Leveraging Spring 5's WebFlux framework, the microservice 
implements a reactive programming paradigm to provide a non-blocking and asynchronous RESTful API. MongoDB is the backing 
data store, which provides a driver for reactive support. Using Docker and Minikube, a container image of the application 
can be deployed to a single-node Kubernetes cluster running locally.  


# Getting Started
**Prerequisites**

The items below must be installed locally

* Java - 1.8.x

* Maven - 3.x.x

* Docker - 17.x.x

* Minikube - 0.24.x

**Build and Deploy** 

Follow the steps below to build the Docker container image of the application and deploy the application to Minikube

* Clone application
``` 
git clone git@github.com/petermatano/purchase.git
```

* Start MiniKube
```
minikube start --vm-driver=virtualbox --disk-size=40g
eval $(minikube docker-env)
```

* Run Minikube dashboard
```
minikube dashboard
```

* Pull MongoDB Docker image
```
docker pull frodenas/mongodb
```

* Build application
```
./mvnw clean package
make build
```

* Deploy application
```
kubectl create -f purchase-deployment.yaml
```

* Register application as a service
```
kubectl expose deployment purchase-deployment --type=NodePort
```

* Get application URL
```
minikube service purchase-deployment --url
```