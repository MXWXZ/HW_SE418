#!/bin/bash
echo Building...
rm -rf build
mkdir build
mkdir build/consumer
mkdir build/container
mkdir build/producer
javac -d build/consumer consumer/src/*.java
javac -d build/container container/src/*.java
javac -d build/producer producer/src/*.java