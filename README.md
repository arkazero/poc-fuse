# Spring Boot Camel Avianca

This project is generate with archetype for redhat, the same allow you to create diferents options
the general architecture will be describe in the next paragraphs.

Configurador:  Contain the classes for configuration purposes.
Model: Contain the classes for business purposes.
Properties: Contain the classes for manage files of properties.
Routes: Contain the classes for the routes, at first moment you will be three Consumer, Transformation and Producer
this mean every project generated with this archetype have the same structure.
Transformationes: Contain the classes to transform the message if is necessary in the integration.

### Building

The example can be built with:

    $ mvn install

This automatically generates the application resource descriptors and builds the Docker image, so it requires access to a Docker daemon, relying on the `DOCKER_HOST` environment variable by default.

### Running the example locally

The example can be run locally using the following Maven goal:

    $ mvn spring-boot:run

Alternatively, you can run the application locally using the executable JAR produced:

    $ java -jar -Dspring.profiles.active=dev target/spring-boot-camel-rest-sql-1.0-SNAPSHOT.jar

This uses an embedded in-memory HSQLDB database. You can use the default Spring Boot profile in case you have a MySQL server available for you to test.

You can then access the REST API directly from your Web browser, e.g.:

- <http://localhost:8080/camel-rest-sql/books>
- <http://localhost:8080/camel-rest-sql/books/order/1>

### Running the example in Kubernetes / OpenShift

It is assumed a Kubernetes / OpenShift platform is already running. If not, you can find details how to [get started](http://fabric8.io/guide/getStarted/index.html).

Besides, it is assumed that a MySQL service is already running on the platform. You can deploy it using the provided deployment by executing in Kubernetes:

    $ kubectl create -f mysql-deployment.yml

or in OpenShift:

    $ oc create -f https://raw.githubusercontent.com/openshift/origin/master/examples/db-templates/mysql-ephemeral-template.json
    $ oc new-app --template=mysql-ephemeral

More information can be found in [using the MySQL database image](https://docs.openshift.com/container-platform/3.3/using_images/db_images/mysql.html). You may need to pass `MYSQL_RANDOM_ROOT_PASSWORD=true` as environment variable to the deployment.

The example can then be built and deployed using a single goal:

    $ mvn fabric8:run -Dmysql-service-username=<username> -Dmysql-service-password=<password>

The `username` and `password` system properties correspond to the credentials
used when deploying the MySQL database service.

You can use the Kubernetes or OpenShift client tool to inspect the status, e.g.:

- To list all the running pods:
    ```
    $ kubectl get pods
    ```

- or on OpenShift:
    ```
    $ oc get pods
    ```

- Then find the name of the pod that runs this example, and output the logs from the running pod with:
    ```
    $ kubectl logs <pod_name>
    ```

- or on OpenShift:
    ```
    $ oc logs <pod_name>
    ```

You can also use the Fabric8 [Web console](http://fabric8.io/guide/console.html) to manage the running pods, view logs and much more.

### Accessing the REST service

When the example is running, a REST service is available to list the books that can be ordered, and as well the order statuses.

If you run the example on a local Fabric8 installation using Vagrant, then the REST service is exposed at <http://qs-camel-rest-sql.vagrant.f8>.

Notice: As it depends on your OpenShift setup, the hostname (route) might vary. Verify with `oc get routes` which hostname is valid for you. Add the `-Dfabric8.deploy.createExternalUrls=true` option to your Maven commands if you want it to deploy a Route configuration for the service.

The actual endpoint is using the _context-path_ `camel-rest-sql/books` and the REST service provides two services:

- `books`: to list all the available books that can be ordered,
- `books/order/{id}`: to output order status for the given order `id`.

The example automatically creates new orders with a running order `id` starting from 1.

You can then access these services from your Web browser, e.g.:

- <http://qs-camel-rest-sql.vagrant.f8/camel-rest-sql/books>
- <http://qs-camel-rest-sql.vagrant.f8/camel-rest-sql/books/order/1>

### Swagger API

The example provides API documentation of the service using Swagger using the _context-path_ `camel-rest-sql/api-doc`. You can access the API documentation from your Web browser at <http://qs-camel-rest-sql.vagrant.f8/camel-rest-sql/api-doc>.

### More details

You can find more details about running this [quickstart](http://fabric8.io/guide/quickstarts/running.html) on the website. This also includes instructions how to change the Docker image user and registry.
