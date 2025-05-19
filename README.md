4 services with following methods:
RocketService
    addNewRocket
    assignRocketToMission
    getRocketSummary
RocketStatusService
    setOnGroundStatus
    setInSpaceStatus
    setInRepairStatus
MissionService
    addNewMission
    assignRockets
    getMissionsSummary
MissionStatusService
    setScheduledStatus
    setPendingStatus
    setInProgressStatus
    setEndedStatus

~~Services accessible via injection. (Spring-like approach)~~
Injected using static method (lazy singleton)
Interface for every service.

Used AI to find solution for Singleton testing
First approach was to create maps(rocketName, rocket) but changed to set(rocket) as no access via name is mentioned


For tests:
isSingleton - for every service
