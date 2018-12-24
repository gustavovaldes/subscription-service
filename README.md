# Subscription Service

  
## Prerequisites
`java8`
`docker`
`docker-compose`

## API Usage example

POST `https://localhost:443/subscription`

Headers 

`Content-Type: application/json`

`Authorization: Bearer abc`

Body
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

Response:
HTTP200
```json
{
    "id": "5c2048a808813b0001f739e1",
    "email": "gustavoemail@gmail.com",
    "firstName": "Gustavo",
    "gender": "male",
    "dateOfBirth": "2012-10-10",
    "consent": true,
    "newsletterId": "123asf436",
    "status": "COMPLETED"
}
```

### Usage notes
-Email is Primary key, so it can't be used more than once
-Some fields are mandatories, 


## Technical Considerations

EventService & EmailService mock basic layer was implemented in same SubscriptionService for showcase proposals.

EventService: Simple Rest endpoint.

EmailService: Service connected to rabbitMQ

MongoDB used as repository for Subscription Service.

### Why queue messages?
Since SLA for email is 2 seconds and for Subscription is 300ms it seems an asynchronous approach works better than  
synchronous communication between the services. One trade-off is the additional coordination between the services; in 
current implementation I'm not adding events from emailService to SubscriptionService, but that can be an option to 
finally complete the "subscription" process and be sure all steps were successfully executed) 

### Why mongodb?
Based on the information provided, there is no need to relational databases here, information can be stored in a single 
document, and we get single digit ms response time.

###Workflow:
1. POST to /subscription service
2. Document persisted in MongoDB as NEW status
3. Rest call to Event Service
4. Message published to RabbitMQ ( EmailService Listening)
5. Document updated to Completed 
 If any failure during communication between services, rollback attempted. ( idea to implement SAGA, but not 
 implemented completely)
 
###Security considerations
1. Enabled only https (using self-signed certificate)
2. Basic token authentication filter implemented. So is mandatory to include Authorization header with value 'Bearer 
abc'

### Run locally

This will create the docker image and run in docker the app, mongodb and rabbitmq, exposing the proper ports.

`./gradlew clean build buildDocker`

`docker-compose up`
  
(*) Alternatively the app can run with gradle only, but external dependencies need to be installed in the local host: 
mongodb and rabbitmq (and update properly the application-local.yml file)

` ./gradlew bootRun -Dspring.profiles.active=local `   
 

## Swagger 
 
`https://localhost/swagger-ui.html` 
  
### Unit Tests
Only few tests implemented.

`./gradlew test`

## Integration tests

Only few tests implemented using Cucumber. 

update:Test broken, app not starting because rabbit dependency (@WIP) 
 
 
## CI/CD Pipeline Proposal

1. Build  Segment:  compile, build, sonar, unit tests, artifactory (or equivalent in Docker world)
2. Isolation Segment: Run integration tests with external services mocked (static data, api test). 
3. Fusion Segment: Similar to previous one, but without mock services, so other service should have a fusion  
environment also. Contract test can run here or create a segment before this one.
4. E2E: Automated functional test fot web apps.
5. Performance: If running performance validations. Maybe also some stress segment. 
6. QA.  Exploratory testing.
7. Release. If working with release branching
8. Pilot. If we want to test with limited number of users latest versions before go full-fleet.
9. Production
 
## Kubernetes config files
- learning :/ 