#Purchase API
Purchase API is a REST based microservice created with Spring Boot. Leveraging Spring 5's WebFlux framework, the microservice 
implements a reactive programming paradigm to provide a non-blocking and asynchronous RESTful API. MongoDB is the backing 
data store, which provides a driver for reactive support. Using Docker and Minikube, a container image of the application 
can be deployed to a single-node Kubernetes cluster running locally.  


#Getting Started
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
minikube start --vm-driver=virtualbox
```

* Run Minikube dashboard
```
minikube dashboard
```

* Pull MongoDB Docker image
```
eval $(minikube docker-env)
docker pull frodenas/mongodb
```

* Build application
```
./mvnw clean package
make build
```

* Deploy application
```
kubectl create -f deployment.yaml
```

* Register application as a service
```
kubectl expose deployment purchase-deployment --type=NodePort
```

* Get application URL
```
minikube service purchase-deployment --url
```

* Show 'receiptPurchases' collection in MongoDB
```
mongo
use purchase
db.getCollection('receiptPurchases').find().forEach(printjson)
```

#REST API
The application defines the following endpoints
```
* POST /receiptPurchases - Create a new receipt purchase

* GET /receiptPurchases/{userId}?vendor={vendor}&purchaseDate={mm-dd-yyyy}&sku={sku} - Retrieve a receipt purchase by user id and filter by vendor, purchase date, or sku
```
