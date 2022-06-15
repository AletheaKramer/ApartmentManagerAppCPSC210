package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;


// Represents an apartment having a unique unit number, tenant's name, occupancy status, and rent (in dollars),
// and a list of maintenance reports (the maintenance record of an apartment)
public class Apartment implements Writable {

    private String tenantName; // Name of tenant occupying the suite
    private int number; // Unique apartment number
    private int rent; // rent (in dollars) that tenant is paying per month, rent must be >= 0
    private boolean occupied; // status of if suite is currently occupied or not
    private List<MaintenanceReport> apartmentMaintenanceRecord; // list of maintenance reports associated with apartment

    /*
     * REQUIRES: Apartment number must be unique and rent must be number >= 0
     * MODIFIES: this
     * EFFECTS: Sets parameters and returns parameters
     * 		    Compiles list of Maintenance Records
     */
    public Apartment(String tenantName, int number, int rent, boolean occupied) {
        this.tenantName = tenantName;
        this.number = number;
        this.rent = rent;
        this.occupied = occupied;
        this.apartmentMaintenanceRecord = new ArrayList<>();
    }

    // getters

    public String getTenantName() {
        return tenantName;
    }

    public int getRent() {
        return rent;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public int getNumber() {
        return number;
    }

    public List<MaintenanceReport> getApartmentMaintenanceRecord() {
        return apartmentMaintenanceRecord;
    }

    // setters

    public void setTenantName(String name) {
        tenantName = name;
    }

    public void setRent(int rent) {
        this.rent = rent;
    }

    public void setOccupancy(Boolean occupied) {
        this.occupied = occupied;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    // EFFECTS: Adds MaintenanceReport to buildingMaintenanceRecord
    public void addReport(MaintenanceReport apartmentMaintenance) {
        apartmentMaintenanceRecord.add(apartmentMaintenance);
    }

    // EFFECTS: Returns the number of Maintenance Reports in a given apartment
    public int reportNum() {
        int reportNum = 0;

        for (MaintenanceReport ignored : apartmentMaintenanceRecord) {
            reportNum++;
        }

        return reportNum;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("tenantName", tenantName);
        json.put("number", number);
        json.put("rent", rent);
        json.put("occupied", occupied);
        json.put("maintenanceReport", maintenanceToJson());
        return json;
    }

    // EFFECTS: returns MaintenanceReports in the apartment as a JSON array
    private JSONArray maintenanceToJson() {
        JSONArray jsonArray = new JSONArray();

        for (MaintenanceReport m : apartmentMaintenanceRecord) {
            jsonArray.put(m.toJson());
        }

        return jsonArray;
    }
}

