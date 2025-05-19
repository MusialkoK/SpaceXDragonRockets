[SpaceX_Dragon_Rockets_Repository.pdf](SpaceX_Dragon_Rockets_Repository.pdf)

# SpaceX Dragon Rockets Repository

This is a simple Java library that models a repository for SpaceX Dragon rockets and their missions.

## Assumptions & Design Decisions
- `Rocket` and `Mission` are modelled as separate domain entities.
- Statuses are implemented via enums to ensure type safety.
- No external libraries or frameworks were used.
- All services are lazy singletons and are accessible via getInstance() static method.
- Every service implements its own interface.
- Collections that stores `Missions` and `Rockets` implemented as `Sets` as no access via `name`, `id` etc. is required
- As bot `Rocket` and `Mission` creation are straight-forward factories won't be in use.
- In summary rockets within a mission are printer in alphabetical order.
- As no information provided on how or when `Mission` comes to an end, it is up to user to invoke `setStatusToEnded` on mission.
- Code author identifies as human therefore `In build` rocket status will not be implemented. :-)


## Features
The library supports the following functionality:

1. **Add rocket** – default status: `"On ground"`.
2. **Assign rocket to a mission** – each rocket can only be in one mission at a time.
3. **Change rocket status** – allowed statuses:
    - `"On ground"` (default)
    - `"In space"` (changed automatically in assigning `Rocket` to `Mission`)
    - `"In repair"` (causes mission status to become `"Pending"`)
4. **Add mission** – default status: `"Scheduled"`.
5. **Assign rockets to a mission** – missions can have multiple rockets.
6. **Change mission status** automatically (except for status `Ended`) – allowed statuses:
    - `Scheduled` 
    - `Pending`
    - `In progress` (when rockets are assigned and no rocket is `In repair`)
    - `Ended`
7. **Get mission summary** – sorted by:
    - Descending number of rockets.
    - Then descending alphabetical order (if rocket count is equal).

## Tests
- All major operations are covered by unit tests.
- Edge cases handled:
    - Reassigning a rocket already assigned to another mission.
    - Adding duplicate rocket or mission names.
    - Null or empty names of rockets and missions
    - 

## Use of AI
- AI used for finding solution for Singleton testing.
- Readme.md structure
- Debugging

## Future development 
- A facade can be added to restrict user access to specific methods

## How to Use
1. Clone the repository
2. Open in any Java IDE (e.g. IntelliJ)
3. Run the tests