
# Overview

This yaml can be used to install the kubernetes dashboard onto Kind.

# Prequesite

You have a kubernetes cluster configured and are using the proper context with `kubectl`

# Kind Installation

1. Install `kind` by running `brew install kind`
2. Use only one of the provided strategy to create eiher a single note cluster or multi node cluster
    1. Create multinode cluster by running `kind create cluster --config=config.yaml`
    2. Create single node cluster by running `kind create cluster`
3. Check kubernetes config by running `kubectl get nodes -o wide`
4. Now let's apply the Dashboard. Run `kubectl apply -f https://raw.githubusercontent.com/kubernetes/dashboard/v2.0.0/aio/deploy/recommended.yaml`
5. Run `kubectl apply -f dashboard-adminuser.yaml`
6. Run `kubectl apply -f clusterrolebinding.yaml`
7. Run `kubectl proxy`
8. Run `kubectl -n kubernetes-dashboard describe secret $(kubectl -n kubernetes-dashboard get secret | grep admin-user | awk '{print $1}')`
9. Copy the token from the output.
10. Go to [http://localhost:8001/api/v1/namespaces/kubernetes-dashboard/services/https:kubernetes-dashboard:/proxy/](http://localhost:8001/api/v1/namespaces/kubernetes-dashboard/services/https:kubernetes-dashboard:/proxy/)
11. Select the `token` option to log in and paste the token you copied into the field.
12. Hit sign in button and boom you're into the dashboard

# Application Build

1. you have atleast jdk 11, maven and docker installed
2. run `mvn clean install -DskipTests`
3. run `docker build -t abhiesa/knote:latest .`
    1. instead of `abhiesa/knote` you might want to use your own namespace `<your-docker-namespace>/knote`
4. run `docker push abhiesa/knote:latest`

# Deployment

Let's just deploy mongo and our knote application first to our cluster:

1. Run `kubectl apply -f ./k8s/mongo.yaml`
2. Run `kubectl get pods -o wide`
3. Now let's expose our mongo deployment. Run `kubectl expose deployment mongo --type NodePort`
4. Run `kubectl apply -f ./k8s/knote.yaml`
5. Run `kubectl get pods -o wide`
6. Now let's expose our mongo deployment. Run `kubectl expose deployment knote --type NodePort`
7. Through port forwarding we can access our application. First run `kubectl get svc`
Now run `kubectl port-forward service/knote 8000:8080`
(Assuming nothing else is running on port 8080)
8. In your browser, go to [http://localhost:8000/knote](http://localhost:8000/knote)

# Clean up

You can honeslty just run `kind delete cluster` and it'll delete the default cluster by the name of `kind`. If you gave your cluster a name just run `kind delete cluster --name <name of cluster>`.

Enjoy!
