## SETUP DEV ENVIRONMENT
include $(shell [ ! -f ".entrypoint.mk" ] && curl --silent -o .entrypoint.mk "https://git.company.cl/projects/OMYPAY/repos/make-tools/raw/scripts/entrypoint.mk?at=refs%2Fheads%2Fdevelop"; echo .entrypoint.mk)

DOCKER_IMAGE_NAME=os/os-help-center-service
DOCKER_IMAGE_VERSION=$(ARTIFACT_VERSION)
ifeq ($(DOCKER_IMAGE_VERSION),)
DOCKER_IMAGE_VERSION := local
endif

build-artifact:
	./gradlew clean build -x test

run-dev:
	./gradlew clean quarkusDev

build-image: build-artifact
	docker build -f src/main/docker/Dockerfile.jvm -t $(DOCKER_IMAGE_NAME):$(DOCKER_IMAGE_VERSION) --compress .

build-image-native:
	./gradlew build -Dquarkus.package.type=native -Dquarkus.native.container-build=true && \
	docker build -f src/main/docker/Dockerfile.native -t $(DOCKER_IMAGE_NAME):$(DOCKER_IMAGE_VERSION) .

run-docker: build-image
	docker run -i --rm -p 8080:8080 -p 5005:5005 \
	-e AWS_REGION="us-east-1" \
	-e QUARKUS_PROFILE="dev" \
	 $(DOCKER_IMAGE_NAME):$(DOCKER_IMAGE_VERSION)

run-docker-native: build-image-native
	docker run -i --rm -p 8080:8080 $(DOCKER_IMAGE_NAME):$(DOCKER_IMAGE_VERSION)

## TESTING

run-tests:
	./gradlew test
