# JHipster 3.0 Demo application with Netflix Ribbon and Hystrix
A microservice demo project using JHipster 3.0 and applying Netflix Ribbon / Hystrix at service level


## What is in the box?

This is a project generated using [JHipster](https://jhipster.github.io) containing:

* a microservice application "foo" with a own Ribbon load balancer with Hystrix on top
* a simple microservice application "bar"
* a microservice gateway
* and a docker compose project
    
## How to run?

    $ git clone git@github.com:xetys/jhipster-ribbon-hystrix.git
    $ cd foo-service
    $ ./gradlew build -Pprod -x test buildDocker
    $ cd ../bar-service
    $ ./gradlew build -Pprod -x test buildDocker
    $ cd ../gateway
    $ ./gradlew build -Pprod -x test buildDocker
    $ cd ../docker
    $ docker-compose up -d
    
## Resources

* [JHipster 3.0 Tutorial and inter-service communication](http://stytex.de/blog/2016/03/25/jhipster3-microservice-tutorial/)
* [JHipster 3.0: Introducing Microservices](http://www.ipponusa.com/blog/jhipster-3-0-introducing-microservices/)
* [JHipster](https://jhipster.github.io)
