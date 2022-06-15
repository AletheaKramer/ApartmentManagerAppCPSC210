package persistence;

import model.Apartment;
import model.Building;
import model.MaintenanceReport;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// This references code from the JSON reference
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

public class JsonWriterTest extends JsonTest{

    @Test
    void testWriterInvalidFile() {
        try {
            Building bld = new Building();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyBuilding() {
        try {
            Building bld = new Building();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyBuilding.json");
            writer.open();
            writer.write(bld);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyBuilding.json");
            bld = reader.read();
            assertEquals(0, bld.buildingSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralBuilding() {
        try {
            Building bld = new Building();
            Apartment john = new Apartment("John", 101, 1000, true);
            bld.addApartment(john);
            MaintenanceReport mrFixed = new MaintenanceReport(LocalDate.parse("2021-01-01"), "Fixed things");
            john.addReport(mrFixed);
            Apartment empty = new Apartment("", 102, 0, false);
            bld.addApartment(empty);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralBuilding.json");
            writer.open();
            writer.write(bld);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralBuilding.json");
            bld = reader.read();
            List<Apartment> building = bld.getApartments();
            assertEquals(2, building.size());

            checkApartment(building.get(0), john);
            assertEquals(1, building.get(0).getApartmentMaintenanceRecord().size());
            MaintenanceReport mr = building.get(0).getApartmentMaintenanceRecord().get(0);
            checkMaintenanceReport(mr, mrFixed);

            checkApartment(building.get(1), empty);

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}


