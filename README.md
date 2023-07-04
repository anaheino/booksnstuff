# Example Java Application

This is an example Spring Boot 3.1 application with Java 17, that features some of the most important tricks and patterns used to develop easily maintainable, extendable and scaling Spring Boot -applications.

Some of the stuff in this application is a bit stupid and hacky because this is a proof of concept project that is meant to demonstrate most important parts of enterprise java applications.

As a the most glaring example, this project does not have an actual front-end, just a simple PoC thymeleaf .html-files. In real life all thymeleaf-idiocy and viewControllers related to it would be removed and replaced with actual front-end library/framework, such as react/angular/vue.

The project is split into three services: common-backend, bookapp and userapp.

- common-backend houses abstract classes, and would house factories and other such necessary pieces of code that are used in multiple different modules.
- bookapp runs by default on port 8080, and houses logic related to books. Basic crud's and such.
- userapp runs by default on port 8081, and houses logic related to user cruds.


- Both use similar spring security configs, where default base-configurations are housed in common-backend and used in other modules.
- Both use a PostgreSQL-database.

## Running the project:

1. Run 'mvn clean install -DskipTests && cd docker && docker-compose up' in the root directory.
2. Navigate to localhost:8080 or localhost:8081 in browser (I've heard that for someone this required incognito mode in browser, but for me normal browser was sufficient.)

Note: This is a bit older version, and thus does not use docker buildkit. To fix possible issues, set DOCKER_BUILDKIT environment variable to 0, and restart docker daemon.

## Some notes on the implementation:
Of the top of my head, this is of course lacking a lot of things, but here are some that came to mind:

- This app still lacks a lot of input and object validation, and doesn't take advantage of annotations such as @NotNull, @IsEmpty etc not to mention custom validators. In real life these would of course be added to the project. This also means it's pretty easy to break the application, if you try. (As an example, try to register same user twice! You can't login after that with that user, because there are no unique restrictions on db-level or in the java-level.)
- Lacks JWT refreshing and handling it's expiration. If receiving error when relaunching the application, first try to navigate to /api/v1/logout or clear JWT_TOKEN cookie. I haven't implemented proper JWT-failure handling, and thus when application tries to parse non-existent user from valid cookie it will fail.
- In general JWT parsing is not done with Authorization header, but with JWT_TOKEN named cookie. In real life it would be in the authorization header, but this is a PoC with thymeleaf and no actual front-end.
- The user authentication is pretty raw, and should be handled better in a real life application (caching / some other lightweight solution instead of dragging around the User-JPARepository to every project)
- Currently only the user password is encrypted, but depending on requirements I could see a case for encrypting all other PII's as well, such as names, emails and other such things. Could also see an use case of encrypting this PII in the front-end, even before it's sent to the backend to avoid MITM-attacks.
- Lombok is used in the project as it's a one-man demonstration, but in real life this should not be used because of clean code principles.
- Currently Hibernate is allowed to modify the database schema, in production this should be disallowed, making hibernate read-only and adding (as an example) flyway to perform the actual modification of the schema. Also taking advantage of PostgreSQL's jsonb-field would be recommended.
- Does not have a linter, which it definitely should have.
- More things should be read from environment variables, there is some hard-coding of stupid strings around here.
- Tests exist as an example only for Books, but should be implemented all around and extend base configurations to avoid repeating same configurations. Also they should be using their own test database, to avoid causing issues with development data.
- In case of worker-tasks, we could add additional [Spring Cloud Stream -application](https://spring.io/projects/spring-cloud-stream) and as an example implement a [RabbitMQ-message senders/receivers](https://spring.io/guides/gs/messaging-rabbitmq/) to send tasks to the worker
- If adding this to cloud, upcoming [Coordinated Restore at Checkpoint](https://github.com/CRaC/docs) or [GraalVM](https://www.graalvm.org/) could do wonders when scaling to zero or trying to keep the application even more lightweight.

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
- Everything should be configured with some IAC tool, such as terraform or cloudformation
- And numerous other things, but this is just a simple example.
