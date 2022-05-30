# challenge-lemon

Lemon's [L2 Coding Challenge](https://thorn-paperback-665.notion.site/L2-Coding-Challenge-f55f26875e1c4871b528f07e109c0e52)

## How to run server

```
./gradlew bootRun
```

This project uses Gradle Wrapper for convenience. 

## How to request a message

```
curl -X 'GET' 'http://localhost:8080/message' -H 'x-username: Admin'
```

A header "x-username" is required to bear a value of "Admin" to be allowed

## Rate Limiting

As the challenge requires, a rate limiter algorithm was implemented. 
The whole code is located into the class HomemadeRateLimiter. 
The strategy used is a combination of Token Bucket and Sliding Window. 
For the sake of simplicity tokens are not borrowed but added, and an eviction policy was made in place based on current time minus 10 seconds.
Both the amount of requests and size of the time-window are configurable through properties at application.yml

For a production-ready system, a fully working solution would be applied such as Bucket4j, Resilience4j or Guava

## Authentication

A very simple approach would have been to just check the x-username header in the controller's code.
Indeed, I've intentionally left the code there in place despite it's not necessary. 

Before reaching the actual controller access control is happening inside the VeryBasicAuthFilter class which is a OncePerRequestFilter being evaluated for any controller
Some urls have been whitelisted to not require authentication. I.e the Swagger-UI.

I could have implemented a fully working Spring Security solution, but it was not required by the challenge.
For a fully working Spring Security solution see [my solution to this other challenge](https://github.com/gmarti28/challenge-fintech-cl)

## Configurable Properties

Customizable properties are defined in src/main/resources/application.yml

They are kind of self-explanatory:

```
ratelimit:
  maxRequests: 5
  windowSizeSeconds: 10

fooas:
  endpoint: https://www.foaas.com
  operation: because
```

## Swagger

To display the Swagger-UI open up any browser and go to http://localhost:8080

The server will **redirect** to the proper Swagger endpoint.

## Tests 

Neither unit nor integration tests were required by the challenge. 
To automate testing of the solution to some extent I've made available a Postman Collection inside the folder "Postman"
This collection checks the unauthorized scenario, then 5 identical requests in a row, and a 6th one which is intended to fail with HTTP error 429
After sleeping for 10 seconds another request is made to check that rate limit is properly reset. 

All those requests have their tests scripts, so the status code and some part of the response is validated.

They are intended to be run as a whole collection due to timing requirements. Running them manually would not properly exercise the rate limiting algorithm.


