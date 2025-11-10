.PHONY: build run run-dist

build:
	bash -c "./gradlew build"

run-dist:
	bash -c "./gradlew installDist"
	bash -c "./build/install/app/bin/app"

run:
	bash -c "./gradlew run"