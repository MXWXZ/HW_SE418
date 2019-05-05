# EXE7-Double dockerized calculator
## Use docker
first pull

    sudo docker pull imwxz/calculator-auth
    sudo docker pull imwxz/calculator-calc

then run

    sudo docker run --name="calcauth" -p 1926:1926 -p 8080:8080 -d imwxz/calculator-auth
    sudo docker run --net=container:calcauth -d imwxz/calculator-calc

The project will be run in "http://localhost:8080". Enjoy!

## Direct build & run
Go /auth and run

    ./gradlew bootRun

Auth service will be run in "http://localhost:1926" by default.

Go /calc and run

    ./gradlew bootRun

Calc service will be run in "http://localhost:8080" by default.

## example
POST: http://localhost:8080/calc

Body: expression=1%2B1&username=admin&password=123456

This will perform `1+1` and return `2`

## User
test admin:
- username: admin
- password: 123456

## API
POST `/calc`
- Calculate expression.
- Param:
  - `expression`: math expression(**URLEncode is needed!**)
  - `ans`: [OPTION] last answer, default is `0`
  - `username`: username
  - `password`: password
- Return JSON:
  - `code`: `-1` not inited, `0` success, `1` empty expression, `2` ans error, `3` expression error, `4` wrong username or password
  - `ans`: not empty if success, calculate answer
  - `msg`: error message

## TEST
Run `CalculatorApplicationTests` for MockMvc test.