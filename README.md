# How should I start
1. check if TheApplication is starting.
2. run tests by using mvn wrapper to check if everything works fine.

## Scenario
Let’s imagine you are working on API for backend of online payment system.
The actors are: users (festival participants), merchants, event organiser.
We want to enable backoffice operations to the event organiser.

Currently implemented:
- Users can make a payment (with a virtual currency) to a specific merchant.
- Our system has payment log.
- It means that information about payments are stored in database.
- This data can be used for reporting.

# Tasks
## Task 1:
Please make a code review of the currently implemented solution.
## Task 2:
Add new endpoint which give total income for payments for selected time period for given merchant.
