

## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)
* [APIs](#apis)

## General info
As a cautious action after Covid-19, VaccNow is an healthcare organization managing the process of Covid-19 vaccine to public,
so that the VaccNow is planning to build multi their digital channels for consuming a modern API for basic features. This is
planned to be API first approach, of well-tested functions and enabling agility of later modifications
	
## Technologies
Project is created with:
* Java: 8
* Springboot: 2.4.1 with Rest, JPA, Scheduler and Cache
* H2 Database
* iText
	
## Setup
To run this project:
Import Project as Gradle project
```
Start Application with VaccNowApplication.java
```
OR

```
$ cd JAR_PATH
$ java -jar VaccNow.jar 
```
Sample data:
 1. Branches  -Delhi and Gurgaon
 2. Vaccines  - Bharat Vaccine and Moderna
 3. Entries - Appointment user1@user.com, user2@user.com and user3@user.com
 4. Vaccination is done for user2@user.com and his invoice & certificate will be located in root directory
 5. You can run post(Schedule vaccination) and put(Apply vaccination) commands for testing - user5@user.com


## APIs

## Availability

 * Get a list of all branches
http://localhost:8080/availability/list-branches
 * Get a list of all available vaccines per branch
http://localhost:8080/availability/list-vaccines
 * Get a specific availability by branch
http://localhost:8080/availability/list-vaccines/201
 * Get available time for a branch
http://localhost:8080/availability/available-branch-time/201

## Vaccination

 * Schedule vaccination timeslot (15 minutes)
```
curl -X POST http://localhost:8080/vaccination/schedule-vaccination-timeslot --header "Content-Type: application/json" -d '{"email":"user5@user.com","branchId":"201","vaccineId":"101","slotDate":"2020-12-31 10:15"}'
```
 * Choose Payment Method Cash, Credit, or Fawry
NA
 * Confirm scheduled vaccination by email
Print in logs after booking time slot instead of mail configuration
 * Apply vaccination - Scheduler will generate invoice and certificate sample in 10 second in root directory.
```
curl -X PUT http://localhost:8080/vaccination/confirm-vaccination/user5@user.com
```

## Reporting
 * Get a list of all applied vaccination per branch
http://localhost:8080/reporting/applied-vaccination-per-branch/201
 * Get a list of all applied vaccination per day/period
http://localhost:8080/reporting/applied-vaccination-per-day
http://localhost:8080/reporting/applied-vaccination-per-day/2020-12-26/2020-12-31
 * Show all confirmed vaccinations over a time period 
http://localhost:8080/reporting/confirmed-vaccinations/2020-12-26/2020-12-31

## BONUS POINTS
 * Invoice Generation
Scheduler - SchedularServiceUtil
File Path - Project directory

 * Vaccine Certificate Generation
Scheduler - SchedularServiceUtil
File Path - Project directory

 * Unit Tests
VaccNowApplicationTests.java

