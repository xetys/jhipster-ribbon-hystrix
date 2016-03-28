# JHipster 3.0 Demo application with Netflix Ribbon and Hystrix and a prototype for JHipsterUAA

*this WIP: this repository contains an alternate implementation for authorization using an additional auth service*

A microservice demo project using JHipster 3.0 and applying Netflix Ribbon / Hystrix at service level


## What is in the box?

This is a project generated using [JHipster](https://jhipster.github.io) containing:

* a simple JHipster User Authentication and Authorization service (JHipsterUAA)
* a microservice application "foo" with a own Ribbon load balancer with Hystrix on top
* a simple microservice application "bar"
* a microservice gateway
* and a docker compose project

In this version authorization is bypassing the JHipster JWT implementation and works with the default tools
from spring. This is no longer done inside the gateway, but in a own service called JHipsterUAA. 

Note: The bar resource is protected is ADMIN authority
    
## How to run?

    $ git clone git@github.com:xetys/jhipster-ribbon-hystrix.git 
    $ git checkout origin/oauth2
    $ cd auth-service
    $ ./gradlew -x test build -Pprod buildDocker
    $ cd foo-service
    $ ./gradlew -x test build -Pprod buildDocker
    $ cd ../bar-service
    $ ./gradlew -x test build -Pprod buildDocker
    $ cd ../gateway
    $ ./gradlew -x test build -Pprod buildDocker
    $ cd ../docker
    $ docker-compose up -d
    
## Resources

* [JHipster 3.0 Tutorial and inter-service communication](http://stytex.de/blog/2016/03/25/jhipster3-microservice-tutorial/)
* [JHipster 3.0: Introducing Microservices](http://www.ipponusa.com/blog/jhipster-3-0-introducing-microservices/)
* [JHipster](https://jhipster.github.io)

