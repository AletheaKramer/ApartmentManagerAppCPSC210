package model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MaintenanceReportTest {

    private MaintenanceReport mr1;

    @BeforeEach

    public void maintenanceReport() {
        this.mr1 = new MaintenanceReport(LocalDate.parse("2021-01-01"), "Paint touch-ups");
    }


    @Test
    public void setDateTest() {
        mr1.setDate("2021-01-02");

        assertEquals(LocalDate.parse("2021-01-02"), mr1.getDate());
    }


    @Test
    public void setDescriptionTest() {
        mr1.setDescription("Painted bedroom");

        assertEquals("Painted bedroom", mr1.getDescription());
    }


}
