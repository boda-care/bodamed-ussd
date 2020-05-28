# bodamed-ussd
USSD by INUKA CONCEPTS

## Problem Definition  & Recognition

- Boda boda drivers need a cover to help them cater for healthcare bills and motor cycles. They are dissatisfied with the current policy as it takes longer to claim and eventully claims are not processed.
- Boda boda industry drives the economy accounting for more than a million jobs.

## Problem Solution
- Boda med includes 2 insurance covers Private Custom Insurance and Third Party Insurance that will be Co Payed by an under writer and the boda boda ride through Boda Med

## Software Design
- We have 3 system Account Service, Payments Service, USSD

# Docker Image Creation
- Build a project jar file and builds an image it
```
.\gradlew docker
```
# Run docker container
```
  docker run -p containerPort:mappedPort -t ussd

```
