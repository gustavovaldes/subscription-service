# Subscription Service

  
## Prerequisites
`java8`
`docker`
`docker-compose`

## API Usage example

POST `https://localhost:443/subscription`

```json
{
	"firstName": "Gustavo",
	"email": "gustavoemail@gmail.com",
	"newsletterId": "123asf436",
	"dateOfBirth":"2012-10-10",
	"consent": true,
	"gender": "male"
}
```


## Technical Considerations

EventService & EmailService mock layer was implemented in same SubscriptionService for showcase proposals
EventService: Simple Rest endpoint.
EmailService: Service connected to rabbitMQ
MongoDB used as repository for Subscription Service.

### Why a queue?
Since SLA for email is 2 seconds and for Subscription is 300ms seems an asynchronous approach is valid option

###Workflow:
1. POST to /subscription service
2. Document persisted in MongoDB as NEW status
3. Rest call to Event Service
4. Message published to RabbitMQ ( EmailService Listening)
5. Document updated to Completed 
 If any failure during communication between services, rollback attempted. ( idea to implement SAGA, but not 
 implemented completely)
 
###Security considerations
1.enable only https (using self-signed certificate)
2.basic authentication filter implemented, mandatory to include Authorization header with value 'Bearer abc' (just 
the idea to use oauth2 or similar)

### run locally

`./gradlew clean build buildDocker`
`docker-compose up`
  
### unit tests
Only few tests implemented
`./gradle tests`

## integration tests
Only few tests implemented using Cucumber
`./gradle integrationTests`


## CI/CD Pipeline Proposal

1.- Build  Segment:  compile, build, sonar, unit tests, artifactory (or equivalent in Docker world)

2.- Isolation Segment: Run integration tests with external services mocked (static data, api test). 

3.- Fusion Segment: Similar to previous one, but without mock services, so other service should have a fusion  
environment also. Contract test can run here or create a segment before this one.

4.- E2E: Automated functional test fot web apps.

5.- Performance: If running performance validations. Maybe also some stress segment. 

5.- QA.  Exploratory testing.

6.- Release. If working with release branching

7.- Pilot. If we want to test with limited number of users latest versions before go full-fleet.

8.- Production
 
## Kubernetes config files
- learning :) 