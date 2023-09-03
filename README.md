# prime-finder-api


This is a rest api to find prime numbers for a given number.

Swagger for this api is provided in 'output' directory as prime-finder-api.yml

## How to run
The application needs java 17
To run the application one can run 'prime-finder-api-0.0.1-SNAPSHOT.jar' present in 'output' folder with bellow commands
- java -jar prime-finder-api-0.0.1-SNAPSHOT.jar
- java -jar prime-finder-api-0.0.1-SNAPSHOT.jar conf.properties

The jars also can be generated using 'mvn clean install', following which a prime-finder-api-0.0.1-SNAPSHOT.jar would be generated in target folder.

The application can also be using docker.
There is a Dockerfile present in 'output' directory

Docker image can built using following command from terminal in 'output' directory
docker image build -t  prime-finder-api:latest .

built image can be run with following commands
- docker run -p 9080:9080 prime-finder-api:latest
- docker container run -p 9080:9080 prime-finder-api:latest
- docker run -p 9080:9080 prime-finder-api:latest conf.properties
- docker container run -p 9080:9080 prime-finder-api:latest conf.properties



## Internals

'conf.properties' contains application specific properties
i.e. log level, application port etc, can be used for different intended environments. If no 'conf.properties' is provided default conf.properties for development environment would be used
present in the jar file.

'conf.properties' can be customized as per need and its location can be provided as a first argument as part of command line while running the application. 


To get the primes for a given number it can be accessed with path /prime-finder-api/v1.0/prime-finder-api/v1.0/prime/{initial}
Application port can be specified in conf.properties in 'serverPort', 
Application can be accessed with the complete url as 
http://{host-ip-address}:{serverPort}/prime-finder-api/v1.0/prime/{initial}

initial is mandatory to get the prime numbers up to the initial.
It supports different 'strategy' to calculate prime numbers.
Supported value of 'strategy'
- BRUTE_FORCE
- SQUARE_ROOT
- SIEVE_OF_ERATOSTHENES
- TWO_TO_TEN_AND_PRIME

'strategy' is optional field if no strategy is selected default SIEVE_OF_ERATOSTHENES would be selected.

Returned response is supported in 'json' and 'xml' format, which can be specified in request header as 
- Accept:application/json
- Accept:application/xml


Sample curl commands
- curl --location 'localhost:9080/prime-finder-api/v1.0/prime/10' \
  --header 'Accept: application/json' \
  --header 'strategy: SQUARE_ROOT'
- curl --location 'localhost:9080/prime-finder-api/v1.0/prime/10' \
  --header 'Accept: application/xml' \
  --header 'strategy: TWO_TO_TEN_AND_PRIME'