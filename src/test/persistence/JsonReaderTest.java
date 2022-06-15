package persistence;

import model.Apartment;
import model.Building;
import model.MaintenanceReport;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// This references code from the JSON reference
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

public class JsonReaderTest extends JsonTest{

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Building bld = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyBuilding() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyBuilding.json");
        try {
            Building bld = reader.read();
            assertEquals(0, bld.buildingSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralBuilding() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralBuilding.json");
        try {
            Building bld = reader.read();
            List<Apartment> building = bld.getApartments();
            assertEquals(2, bld.buildingSize());

            Apartment apt1 = building.get(0);
            checkApartment(apt1, "John", 101, 1000, true);

            MaintenanceReport mr = apt1.getApartmentMaintenanceRecord().get(0);
            assertEquals(1, apt1.getApartmentMaintenanceRecord().size());
            checkMaintenanceReport(mr, mr.getDate(), mr.getDescription());

            Apartment apt2 = building.get(1);
            checkApartment(apt2, "", 102, 0, false);
            assertEquals(0, apt2.getApartmentMaintenanceRecord().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }


}
