package ui;

import model.Apartment;
import model.Building;
import model.MaintenanceReport;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

// Landlord Application
// This LandlordApp references code from the TellerApp
// Link: https://github.students.cs.ubc.ca/CPSC210/TellerApp.git
public class LandlordApp {
    private static final String JSON_STORE = "./data/landlord.json";
    private Building building;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the landlord application
    public LandlordApp() throws FileNotFoundException {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runLandlord();
    }

    // MODIFIES: This
    // EFFECTS: processes user input
    private void runLandlord() {
        boolean keepGoing = true;
        String command;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        switch (command) {
            case "a":
                addApartment();
                break;
            case "u":
                updateApartment();
                break;
            case "m":
                addApartmentMaintenance();
                break;
            case "v":
                viewStatus();
                break;
            case "s":
                saveWorkRoom();
                break;
            case "l":
                loadWorkRoom();
                break;
            default:
                System.out.println("Selection not valid...");
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes accounts
    private void init() {
        this.building = new Building();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add apartment");
        System.out.println("\tu -> update apartment");
        System.out.println("\tm -> add maintenance record to apartment");
        System.out.println("\tv -> view status of apartment");
        System.out.println("\ts -> save building to file");
        System.out.println("\tl -> load building from file");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: adds an apartment to the building
    private void addApartment() {
        Apartment apartment = new Apartment("", 0, 0, false);

        System.out.print("Enter apartment number: #");
        int number = input.nextInt();
        apartment.setNumber(number);

        if (!building.inBuilding(number)) {

            System.out.println("Enter y if apartment is occupied, enter n if it is vacant");
            String occupancy = input.next();
            if (occupancy.equals("y")) {
                addOccupiedApartment(apartment, number);
            } else {
                building.addApartment(apartment);
                apartment.setOccupancy(false);
                apartment.setRent(0);
                apartment.setTenantName("");

                System.out.println("You have successfully added a vacant apartment: #" + number);
            }
        } else {
            System.out.println("Cannot add an apartment that already exists in the building");
        }
    }

    private void addOccupiedApartment(Apartment apartment, int number) {
        building.addApartment(apartment);
        apartment.setOccupancy(true);

        System.out.println("Enter the rent amount: $");
        int rent = input.nextInt();
        apartment.setRent(rent);

        System.out.println("Enter the tenant name:");
        String name = input.next();
        apartment.setTenantName(name);

        System.out.println("You have successfully added an occupied apartment: #" + number);
    }

    // MODIFIES: this
    // EFFECTS: updates an apartment in the building
    public void updateApartment() {
        System.out.println("Enter apartment number to update: #");
        int apartNum = input.nextInt();

        if (building.inBuilding(apartNum)) {

            System.out.println("Enter y if apartment is occupied, enter n if it is vacant");
            String occupancy = input.next();
            if (occupancy.equals("y")) {
                building.setVacancyStatusOfApartment(apartNum, true);

                System.out.println("Enter the rent amount: $");
                int rent = input.nextInt();
                building.setRentOfApartment(apartNum, rent);

                System.out.println("Enter the tenant name:");
                String name = input.next();
                building.setTenantNameOfApartment(apartNum, name);

                System.out.println("you have successfully updated an occupied apartment: #" + apartNum);

            } else {
                building.setVacancyStatusOfApartment(apartNum, false);
                building.setTenantNameOfApartment(apartNum, "");
                building.setRentOfApartment(apartNum, 0);
                System.out.println("you have successfully made #" + apartNum + " vacant");
            }

        } else {
            System.out.println("Cannot updated because that apartment doesn't exist in your building");
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a maintenance report to an apartment
    private void addApartmentMaintenance() {
        MaintenanceReport maintenanceReport = new MaintenanceReport(LocalDate.parse("1000-01-01"), "");

        System.out.println("Enter apartment number to enter maintenance report: #");
        int apartNum = input.nextInt();

        if (building.inBuilding(apartNum)) {

            System.out.println("Enter date of maintenance in form YYYY-MM-DD");
            String date = input.next();
            maintenanceReport.setDate(date);

            System.out.println("Enter description of maintenance report");
            String description = input.next();
            maintenanceReport.setDescription(description);

            building.addMaintenanceRecordToApartment(apartNum, maintenanceReport);

            System.out.println("You have successfully added a maintenance report to #" + apartNum);

        } else {
            System.out.println("Cannot add report because that apartment doesn't exist in your building");
        }
    }

    // EFFECTS: displays current parameters of an apartment and list of maintenance reports
    private void viewStatus() {
        System.out.println("Enter apartment number to view status: #");
        int apartNum = input.nextInt();
        System.out.println("Tenant Name: " + building.getTenantNameOfApartment(apartNum));
        System.out.println("Rent: " + building.getRentOfApartment(apartNum));
        System.out.println("The apartment is occupied? " + building.getVacancyStatusOfApartment(apartNum));
        System.out.println("Maintenance Record:");
        for (MaintenanceReport maintenanceReport : building.getMaintenanceRecordOfApartment(apartNum)) {
            System.out.println(maintenanceReport.getDate());
            System.out.println(maintenanceReport.getDescription());
        }
    }

    // EFFECTS: saves the workroom to file
    private void saveWorkRoom() {
        try {
            jsonWriter.open();
            jsonWriter.write(building);
            jsonWriter.close();
            System.out.println("Saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadWorkRoom() {
        try {
            building = jsonReader.read();
            System.out.println("Loaded from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
