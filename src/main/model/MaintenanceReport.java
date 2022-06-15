package model;

import org.json.JSONObject;
import persistence.Writable;

import java.time.LocalDate;

// Represents a maintenance report having a date and a description
public class MaintenanceReport implements Writable {

    private LocalDate date; // date that maintenance was conducted in YYYY-MM-DD format
    private String description; // description of work preformed

    /*
     * REQUIRES: date is in the format "YYYY-MM-DD"
     * MODIFIES: this
     * EFFECTS: date of maintenance is set to date
     *          description of maintenance is set to description
     */

    public MaintenanceReport(LocalDate date, String description) {
        this.date = date;
        this.description = description;
    }

    // getters
    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    // setters

    // REQUIRES: date is in the format "YYYY-MM-DD"
    // MODIFIES: MaintenanceReport
    // Effects: sets MaintenanceReport to the given string
    public void setDate(String date) {
        this.date = LocalDate.parse(date);
    }

    // MODIFIES: MaintenanceReport
    // Effects: sets MaintenanceReport to the given string
    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("date", date);
        json.put("description", description);
        return json;
    }
}