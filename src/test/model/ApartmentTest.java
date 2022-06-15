package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class ApartmentTest {

    private MaintenanceReport mr1;
    private MaintenanceReport mr2;
    private MaintenanceReport mr3;
    private MaintenanceReport mr4;
    private MaintenanceReport mr5;

    private Apartment apartmentOccupied;
    private Apartment apartmentEmpty;


    @BeforeEach

    public void setUpApartment() {
        apartmentOccupied = new Apartment("John Smith", 101, 2000, true);
        initializeMaintenanceReports();

        apartmentOccupied.addReport(mr1);
        apartmentOccupied.addReport(mr2);
        apartmentOccupied.addReport(mr3);
        apartmentOccupied.addReport(mr4);
        apartmentOccupied.addReport(mr5);


        apartmentEmpty = new Apartment("", 102, 0, false);
        initializeMaintenanceReports();

        apartmentEmpty.addReport(mr1);
        apartmentEmpty.addReport(mr2);
        apartmentEmpty.addReport(mr3);

    }

    public void initializeMaintenanceReports() {
        this.mr1 = new MaintenanceReport(LocalDate.parse("2021-01-01"), "Paint touch-ups");
        this.mr2 = new MaintenanceReport(LocalDate.parse("2021-01-02"), "New fridge");
        this.mr3 = new MaintenanceReport(LocalDate.parse("2021-01-03"), "Fixed leak");
        this.mr4 = new MaintenanceReport(LocalDate.parse("2021-01-04"), "Sanded floor");
        this.mr5 = new MaintenanceReport(LocalDate.parse("2021-01-05"), "Fixed dishwasher");
    }


    @Test
    public void testAddReport() {
        assertEquals(5, apartmentOccupied.reportNum());
        assertEquals(3, apartmentEmpty.reportNum());
    }

    @Test
    public void testSetOccupancy() {
        apartmentEmpty.setOccupancy(true);
        apartmentOccupied.setOccupancy(false);

        assertTrue(apartmentEmpty.isOccupied());
        assertFalse(apartmentOccupied.isOccupied());
    }

    @Test
    public void testSetTenantName() {
        apartmentEmpty.setTenantName("Paul Jones");
        apartmentOccupied.setTenantName("John Doe");

        assertEquals("Paul Jones", apartmentEmpty.getTenantName());
        assertEquals("John Doe", apartmentOccupied.getTenantName());
    }

    @Test
    public void testSetRent() {
        apartmentEmpty.setRent(1995);
        apartmentOccupied.setRent(2005);

        assertEquals(1995, apartmentEmpty.getRent());
        assertEquals(2005, apartmentOccupied.getRent());
    }

    @Test
    public void testSetNumber() {
        apartmentEmpty.setNumber(103);
        apartmentOccupied.setNumber(104);

        assertEquals(103, apartmentEmpty.getNumber());
        assertEquals(104, apartmentOccupied.getNumber());
    }

    @Test
    public void testGetApartmentMaintenanceRecord() {
        List<MaintenanceReport> john = apartmentOccupied.getApartmentMaintenanceRecord();
        List<MaintenanceReport> empty = apartmentEmpty.getApartmentMaintenanceRecord();

        MaintenanceReport vacant1 = empty.get(0);
        assertEquals(LocalDate.parse("2021-01-01"), vacant1.getDate());
        assertEquals("Paint touch-ups", vacant1.getDescription());

        MaintenanceReport vacant2 = empty.get(1);
        assertEquals(LocalDate.parse("2021-01-02"), vacant2.getDate());
        assertEquals("New fridge", vacant2.getDescription());

        MaintenanceReport john1 = john.get(3);
        assertEquals(LocalDate.parse("2021-01-04"), john1.getDate());
        assertEquals("Sanded floor", john1.getDescription());

        MaintenanceReport john2 = john.get(4);
        assertEquals(LocalDate.parse("2021-01-05"), john2.getDate());
        assertEquals("Fixed dishwasher", john2.getDescription());
    }

}