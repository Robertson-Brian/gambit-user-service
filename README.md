# Hydra Client Module

Create a single Hydra Client module that will standardize all API calls for all services in every module of the Janus web application, which include Caliber, BAM, AssignForce, and TrackForce.

## User Service (Trainee and Trainer)
The Hydra User Microservice was created by the 1801 January USF batch. It contains the Trainer and Trainee microservices that are being used by the Janus web application. 
We decided to combine these microservices mainly because the Entity Relationship Diagram we were given had both Trainer and Trainee be tightly coupled to a User table. 
This microservice also contains many User endpoints that aren't actually used in the Janus web application but are mainly for testing. 
A list of the old endpoints that were used can be found on the Janus web application wiki. 
Every single endpoint for this microservice (besides User) has a comprehensive test suite on the Janus web application.
There is also a comprehensive suite of JUnit testing for our microservice that utilizes RESTAssured to mock the API calls.
