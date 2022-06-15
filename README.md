# Apartment Manager App

## Stores data and maintenance records for apartments in a building

For the past two years I have been helping with bookkeeping and operations for the small business that my family owns.
The business is an apartment building management company. I noticed a need for a central system to track the status of 
the apartments including a list of maintenance records that accumulates despite changes in vacancy status. This 
program allows users to add apartments to their building and then update the fields of the apartment and keep 
track of maintenance performed in that suite. This program is built to be used by building managers or landlords as 
a single source to store, update, and access information about their suites. 

User Stories:
- as a user, I want to add an apartment to my building
- as a user, I want to update an apartment in my building
- as a user, I want to add a maintenance report to my building
- as a user, I want to view the status of an apartment, including all parameters and the maintenance record
- as a user, I want to change an apartment to vacant without loosing a record of its maintenance
- as a user, I want to store all the information for the building's apartments in one place
- as a user, I want to be able to load and save the state of the application

Event Log Sample:
- Tue Nov 23 18:01:31 PST 2021
- Apartment 101 added to the building
- Tue Nov 23 18:01:35 PST 2021
- Apartment 102 added to the building
- Tue Nov 23 18:01:41 PST 2021
- Apartment 103 added to the building
- Tue Nov 23 18:01:55 PST 2021
- Apartment 104 added to the building
- Tue Nov 23 18:01:58 PST 2021
- Apartment 104 removed to the building
- Tue Nov 23 18:02:00 PST 2021
- Apartment 103 removed to the building
- Tue Nov 23 18:02:02 PST 2021
- Apartment 102 removed to the building
- Tue Nov 23 18:02:03 PST 2021
- Apartment 101 removed to the building

Phase 4: Task 3:
- There is a lot of shared behaviour between an Apartment and a building including: address/unit address, square footage, vacancy status, maintenance needs
- I'd introduce an abstract accommodation class that the Apartment and Building Class extend
- This would reduce duplication in the code as there is a lot of shared behaviour and similar constructors
- I'd also introduce the Composite Pattern
- This would reduce the duplication in the building class when adding Apartments and Maintenance Records
- Note: The composite is the Building class and Apartment and Maintenance Records are treated as leaves
- Apartment can also have a list of Maintenance Records. It does not have a list of Composites because an apartment cannot have a list of Buildings. 
- See partly-complete UML diagram for a high-level idea 


