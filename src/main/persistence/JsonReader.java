package persistence;


import model.Apartment;
import model.Building;
import model.MaintenanceReport;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.stream.Stream;

// This references code from the JSON reference
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

// Represents a reader that reads building from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads Building from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Building read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseBuilding(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses building from JSON object and returns it
    private Building parseBuilding(JSONObject jsonObject) {
        Building bld = new Building();
        addApartments(bld, jsonObject);
        return bld;
    }

    private void addApartments(Building bld, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("apartments");
        for (Object json : jsonArray) {
            JSONObject nextApartment = (JSONObject) json;
            addApartment(bld, nextApartment);
        }
    }

    // MODIFIES: bld
    // EFFECTS: parses apartment from JSON object and adds it to building
    private void addApartment(Building bld, JSONObject jsonObject) {
        Apartment apt = new Apartment((jsonObject.getString("tenantName")),
                        (jsonObject.getInt("number")),
                        (jsonObject.getInt("rent")),
                        (jsonObject.getBoolean("occupied")));
        JSONArray jsonArray = jsonObject.getJSONArray("maintenanceReport");
        for (Object json : jsonArray) {
            JSONObject nextReport = (JSONObject) json;

            MaintenanceReport report = new MaintenanceReport(LocalDate.parse((CharSequence) nextReport.get("date")),
                    (nextReport.getString("description")));
            apt.addReport(report);
        }
        bld.addApartment(apt);
    }
}
