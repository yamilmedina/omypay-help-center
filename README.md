# help-center-service (project bkp)

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## ⚙️ Configuring your dev station

Before start coding, you have to configure your station with the following commands:
```shell script
make mk-init
make hook/setup-commit-msg
make setup/artifactory 
```

## 🚀 Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
make run-dev
```

or running on docker:

```shell script
make run-docker
```

## 🔨 Packaging and running the application

The application can be packaged using:
```shell script
make build-artifact
```
It produces the `help-center-service-1.0.0-SNAPSHOT-runner.jar` file in the `/build` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `build/lib` directory.

If you want to build an _über-jar_, execute the following command:
```shell script
./gradlew build -Dquarkus.package.type=uber-jar
```

The application is now runnable using `java -jar build/help-center-service-1.0.0-SNAPSHOT-runner.jar`.

## 🧪 Experimental zone: Creating a native executable

To build and run the project as a native executable you'll need Java 11 and GraalVM 20+ or Mandrel.

You can create a native executable using: 
```shell script
./gradlew build -Dquarkus.package.type=native
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./gradlew build -Dquarkus.package.type=native -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./build/help-center-service-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/gradle-tooling.

## ⚙ Stack

- Service written in Java 11 and using Quarkus
- `Dependency Injection`: Quarkus ARC based on [JSR 365](https://docs.jboss.org/cdi/spec/2.0/cdi-spec.html)
- `REST`: [RESTEasy](https://resteasy.github.io/) implementation of JAX-RS

If you want to learn more about other available extensions, or get a quick base project, please consult https://code.quarkus.io/.
