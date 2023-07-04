# Example Java Application

This is an example Spring Boot 3.1 application, that features some of the most important tricks and patterns used to develop easily maintainable, extendable and scaling Spring Boot -applications.

Some of the stuff in this application is a bit stupid and hacky because this is a proof of concept project that is meant to demonstrate most important parts of enterprise java applications.

As a the most glaring example, this project does not have an actual front-end, just a simple PoC thymeleaf .html-files. In real life all thymeleaf-idiocy anmd viewControllers related to it would be removed and replaced with actual front-end library/framework, such as react/angular/vue.

The project is split into three services: common-backend, bookapp and userapp.

- common-backend houses abstract classes, and would house factories and other such necessary pieces of code that are used in multiple different modules.1
- bookapp runs by default on port 8080, and houses logic related to books. Basic crud's and such.
- userapp runs by default on port 8081, and houses logic related to user cruds.


- Both use similar spring security configs, where default base-configurations are housed in common-backend and used in other modules.
- Both use a PostgreSQL-database.

## Running the project:

1. Go to docker folder
2. run 'docker-compose up'
3. Navigate to localhost:8080 or localhost:8081 in browser

## Some notes on the implementation:
Of the top of my head, this is of course lacking a lot of things, but here are some that came to mind:

- Lacks JWT refreshing and handling it's expiration. If stuck, navigate to /v1/app/logout or clear JWT_TOKEN cookie.
- In general JWT parsing is not done with Authorization header, but with JWT_TOKEN named cookie. In real life it would be in the authorization header, but this is a PoC with thymeleaf and no actual front-end.
- The user authentication is pretty raw, and should be handled better in a real life application (caching / some other lightweight solution instead of dragging around the User-JPARepository to every project)
- Lombok is used in the project as it's a one-man demonstration, but in real life this should not be used because of clean code principles.
- Currently Hibernate is allowed to modify the database schema, in production this should be disallowed, making hibernate read-only and adding (as an example) flyway to perform the actual modification of the schema.
- Does not have a linter, which it definitely should have.
- More things should be read from environment variables, there is some hard-coding of stupid strings around here.
- Tests exist as an example only for Books, but should be implemented all around and extend base configurations to avoid repeating same configurations.
- In case of worker-tasks, we could add additional Spring Cloud Stream -application (https://spring.io/projects/spring-cloud-stream) and as an example implement a RabbitMQ-message senders/receivers (https://spring.io/guides/gs/messaging-rabbitmq/) to send tasks to the worker
- If adding this to cloud, upcoming JVM Checkpoint Restore + GraalVM (https://docs.spring.io/spring-framework/reference/6.1-SNAPSHOT/integration/checkpoint-restore.html) could do wonders when scaling to zero

## General notes about infrastructure

So, how would this application actually turn into a full-fledged web-application?

Well, first of all, above listed things should be implemented. Then, it should be ran in the cloud. Example configuration with AWS could be something like (Using AWS as example cause it's most familiar, even though all of the CP's have pretty much the same stuff with different names):

- Separate ASG's for front-ends and backends, thus providing horizontal scalability
- ALB to balance load between ASG's
- APIGW/lambda to handle invocation from another sources and triggering events in AWS (SES, SQS etc...)
- Cloudfront for caching
- DynamoDB for session data and such, RDS for hosting the PostgreSQL-db
- WAF on the ALB to prevent DDOS etc.
- VPC to run the application in
- AWS cognito could be used to handle authentication better
- MessageMQ to handle rabbitmq messaging
- Frontend would need webpack, minimization, jest etc. test frameworks and all the flashy lights
- Build pipelines and deployment configurations using something like Jenkins, codeBuild, codeDeploy etc.
- Setting up git-rules to prevent git push -f master
