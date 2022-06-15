package persistence;

import model.Apartment;
import model.MaintenanceReport;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

// This references code from the JSON reference
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

public class JsonTest {

    // EFFECTS: helper method for tests
    protected void checkApartment(Apartment apartment, String tenantName, int number, int rent, boolean occupancy) {
        assertEquals(tenantName, apartment.getTenantName());
        assertEquals(number, apartment.getNumber());
        assertEquals(rent, apartment.getRent());
        assertEquals(occupancy, apartment.isOccupied());
    }

    // EFFECTS: helper method for tests
    protected void checkApartment(Apartment expect, Apartment have) {
        assertEquals(expect.getRent(), have.getRent());
        assertEquals(expect.getNumber(), have.getNumber());
        assertEquals(expect.getTenantName(), have.getTenantName());
        assertEquals(expect.isOccupied(), have.isOccupied());
    }

    // EFFECTS: helper method for tests
    protected void checkMaintenanceReport(MaintenanceReport mr, LocalDate date, String description) {
        assertEquals(date, mr.getDate());
        assertEquals(description, mr.getDescription());
    }
    // EFFECTS: helper method for tests
    protected void checkMaintenanceReport(MaintenanceReport expect, MaintenanceReport have) {
        assertEquals(expect.getDate(), have.getDate());
        assertEquals(expect.getDescription(), have.getDescription());
    }

}
