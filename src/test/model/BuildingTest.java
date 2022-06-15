package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BuildingTest {

    private Building building;

    private MaintenanceReport mr1;
    private MaintenanceReport mr2;
    private MaintenanceReport mr3;
    private MaintenanceReport mr4;
    private MaintenanceReport mr5;


    @BeforeEach
    public void setUpBuilding() {
        this.building = new Building();
        Apartment apartmentOccupied = new Apartment("John Smith", 101, 2000, true);
        Apartment apartmentEmpty = new Apartment("", 102, 0, false);

        initializeMaintenanceReports();

        apartmentOccupied.addReport(mr1);
        apartmentOccupied.addReport(mr2);
        apartmentOccupied.addReport(mr3);
        apartmentOccupied.addReport(mr4);
        apartmentOccupied.addReport(mr5);

        apartmentEmpty.addReport(mr1);
        apartmentEmpty.addReport(mr2);
        apartmentEmpty.addReport(mr3);

        building.addApartment(apartmentEmpty);
        building.addApartment(apartmentOccupied);
    }

    public void initializeMaintenanceReports() {
        this.mr1 = new MaintenanceReport(LocalDate.parse("2021-01-01"), "Paint touch-ups");
        this.mr2 = new MaintenanceReport(LocalDate.parse("2021-01-02"), "New fridge");
        this.mr3 = new MaintenanceReport(LocalDate.parse("2021-01-03"), "Fixed leak");
        this.mr4 = new MaintenanceReport(LocalDate.parse("2021-01-04"), "Sanded floor");
        this.mr5 = new MaintenanceReport(LocalDate.parse("2021-01-05"), "Fixed dishwasher");
    }

    @Test
    public void testBuildingSize() {
        assertEquals(2, building.buildingSize());
    }

    @Test
    public void testGetBuilding() {
        List<Apartment> myBuilding = building.getApartments();
        Apartment vacant = myBuilding.get(0);
        Apartment john = myBuilding.get(1);

        assertEquals(2000, john.getRent());
        assertEquals(101, john.getNumber());
        assertTrue(john.isOccupied());
        assertEquals("John Smith", john.getTenantName());

        assertEquals(0, vacant.getRent());
        assertEquals(102, vacant.getNumber());
        assertFalse(vacant.isOccupied());
        assertEquals("", vacant.getTenantName());
    }

    @Test
    public void testGetMaintenanceRecordOfApartment() {
        List<MaintenanceReport> vacant = building.getMaintenanceRecordOfApartment(102);
        List<MaintenanceReport> john = building.getMaintenanceRecordOfApartment(101);
        List<MaintenanceReport> doesNotExist = building.getMaintenanceRecordOfApartment(200);


        MaintenanceReport vacant1 = vacant.get(0);
        assertEquals(LocalDate.parse("2021-01-01"), vacant1.getDate());
        assertEquals("Paint touch-ups", vacant1.getDescription());

        MaintenanceReport vacant2 = vacant.get(1);
        assertEquals(LocalDate.parse("2021-01-02"), vacant2.getDate());
        assertEquals("New fridge", vacant2.getDescription());

        MaintenanceReport john1 = john.get(3);
        assertEquals(LocalDate.parse("2021-01-04"), john1.getDate());
        assertEquals("Sanded floor", john1.getDescription());

        MaintenanceReport john2 = john.get(4);
        assertEquals(LocalDate.parse("2021-01-05"), john2.getDate());
        assertEquals("Fixed dishwasher", john2.getDescription());

        assertEquals(0, doesNotExist.size());
    }

    @Test
    public void testAddMaintenanceRecordToApartment() {
        MaintenanceReport mr6 = new MaintenanceReport(LocalDate.parse("2021-12-12"), "Patched hole");
        building.addMaintenanceRecordToApartment(101, mr6);
        List<MaintenanceReport> john = building.getMaintenanceRecordOfApartment(101);

        MaintenanceReport john1 = john.get(5);
        assertEquals(LocalDate.parse("2021-12-12"), john1.getDate());
        assertEquals("Patched hole", john1.getDescription());
    }

    @Test
    public void testGetRentOfApartment() {
        assertEquals(building.getRentOfApartment(101), 2000);
        assertEquals(building.getRentOfApartment(200), -1);
    }

    @Test
    public void testSetRentOfApartment() {
       building.setRentOfApartment(101, 2005);
       assertEquals(building.getRentOfApartment(101), 2005);

       building.setRentOfApartment(200, 2000);
       assertEquals(building.getRentOfApartment(200), -1);
    }

    @Test
    public void testGetVacancyStatusOfApartment() {
        assertTrue(building.getVacancyStatusOfApartment(101));
        assertFalse(building.getVacancyStatusOfApartment(102));
        assertFalse(building.getVacancyStatusOfApartment(200));
    }

    @Test
    public void testSetVacancyStatusOfApartment() {
        building.setVacancyStatusOfApartment(101, false);
        assertFalse(building.getVacancyStatusOfApartment(101));

        building.setVacancyStatusOfApartment(200, true);
        assertEquals(building.getRentOfApartment(200), -1);
    }

    @Test
    public void testGetTenantNameOfApartment() {
        assertEquals(building.getTenantNameOfApartment(101), "John Smith");
        assertEquals(building.getTenantNameOfApartment(200), "");
    }

    @Test
    public void testSetTenantNameOfApartment() {
        building.setTenantNameOfApartment(101, "Bob Dylan");
        assertEquals(building.getTenantNameOfApartment(101), "Bob Dylan");

        building.setTenantNameOfApartment(200, "Bob Dylan");
        assertEquals(building.getTenantNameOfApartment(200), "");
    }

    @Test
    public void testInBuilding() {
        assertTrue(building.inBuilding(101));
        assertFalse(building.inBuilding(200));
    }

    @Test
    public void testRemoveApartment() {
        building.removeApartment(101);
        assertFalse(building.inBuilding(101));
        building.removeApartment(200);
        assertFalse(building.inBuilding(200));
    }

}
