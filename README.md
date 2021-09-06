# Bodamed USSD
USSD by Maxports Merchants Limited

## Problem Definition  & Recognition

- Boda boda riders need to pay for both medical insurance and at least third party insurance but luck the money on a one of basis.
- The platform allows them to save as low as Ksh 50 per day for their insurance covers

## Problem Solution
- Boda Care includes 2 insurance covers NHIF and Third Party Insurance that will be payed by the boda boda rider through Boda Care

## Software Design
- We have 3 system Account Service, USSD

# Docker Image Creation\
- Build a project jar file and builds an image it
```
.\gradlew docker
```
# Run docker container
```
  docker run -p containerPort:mappedPort -t ussd

```
