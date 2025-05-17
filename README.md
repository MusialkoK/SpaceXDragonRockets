4 services with following methods:
RocketService
    addNewRocket
    assignToMission
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


For tests:
isSingleton - for every service
