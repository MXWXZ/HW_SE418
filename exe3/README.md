# EXE3-RESTful Calculator
## Getting Started
Developed with IntelliJ IDEA, consider build by yourself if you use other IDEs...

- Open the project
- Click 'Build Project'
- Click 'Run'

The project will be run in "http://localhost:8080" by default, if you change configuration, the port number may be different!

## API
POST `/calc`
- Calculate expression.
- Param:
  - `expression`: math expression(**URLEncode is needed!**)
  - `ans`: [OPTION] last answer, default is `0`
- Return JSON:
  - `code`: `-1` not inited, `0` success, `1` empty expression, `2` ans error, `3` expression error
  - `ans`: not empty if success, calculate answer
  - `msg`: error message

## TEST
Run `CalculatorApplicationTests` for MockMvc test.