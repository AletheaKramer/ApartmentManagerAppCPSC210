package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents an apartment building having a list of apartments
public class Building implements Writable {

    protected List<Apartment> apartments; // List of apartments in the building
    //protected EventLog eventLog;


    /*
     * MODIFIES: this
     * EFFECTS: Stores a list of apartments for the building
     */
    public Building() {
        this.apartments = new ArrayList<>();
    }

    // getter
    public List<Apartment> getApartments() {
        return apartments;
    }

    // Returns true if given apartment is in building, false otherwise
    public Boolean inBuilding(int aptNum) {
        for (Apartment apartment : apartments) {
            if (apartment.getNumber() == aptNum) {
                return true;
            }
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: Adds apartment to building
    public void addApartment(Apartment apt) {
        apartments.add(apt);
        EventLog.getInstance().logEvent(new Event("Apartment " + apt.getNumber() + " added to the building"));
    }

    // MODIFIES: this
    // EFFECTS: removes apartment from building
    public void removeApartment(int aptNum) {
        for (int i = 0; i < buildingSize(); i++) {
            if (aptNum == apartments.get(i).getNumber()) {
                EventLog.getInstance().logEvent(new Event("Apartment " + apartments.get(i).getNumber()
                        + " removed to the building"));
                apartments.remove(i);
                break;
            }
        }
    }

    // EFFECTS: returns the number apartments in the building
    public int buildingSize() {
        return apartments.size();
    }


    // EFFECTS: returns the maintenance record of a specific apartment
    //          if no apartment exists, returns an empty list
    public List<MaintenanceReport> getMaintenanceRecordOfApartment(int apartNum) {
        List<MaintenanceReport> empty = new ArrayList<>();
        for (Apartment apartment : apartments) {
            if (apartNum == apartment.getNumber()) {
                return apartment.getApartmentMaintenanceRecord();
            }
        }
        return empty;
    }

    // EFFECTS: adds a maintenance record to a specific apartment
    public void addMaintenanceRecordToApartment(int apartNum, MaintenanceReport report) {
        for (Apartment apartment : apartments) {
            if (apartNum == apartment.getNumber()) {
                apartment.addReport(report);
            }
        }
    }

    // EFFECTS: returns the tenant name of a given apartment #
    //          if no apartment exists, return ""
    public String getTenantNameOfApartment(int apartNum) {
        for (Apartment apartment : apartments) {
            if (apartNum == apartment.getNumber()) {
                return apartment.getTenantName();
            }
        }
        return "";
    }

    // EFFECTS: sets the tenant name of a given apartment #
    public void setTenantNameOfApartment(int apartNum, String name) {
        for (Apartment apartment : apartments) {
            if (apartNum == apartment.getNumber()) {
                apartment.setTenantName(name);
            }
        }
    }

    // EFFECTS: returns the rent of a given apartment #
    //          if no apartment exists, return -1
    public int getRentOfApartment(int apartNum) {
        for (Apartment apartment : apartments) {
            if (apartNum == apartment.getNumber()) {
                return apartment.getRent();
            }
        }
        return -1;
    }

    // EFFECTS: sets the rent status of a given apartment #
    public void setRentOfApartment(int apartNum, int rent) {
        for (Apartment apartment : apartments) {
            if (apartNum == apartment.getNumber()) {
                apartment.setRent(rent);
            }
        }
    }

    // EFFECTS: gets the vacancy status of a given apartment #
    //          if no apartment exists, return false
    public boolean getVacancyStatusOfApartment(int apartNum) {
        for (Apartment apartment : apartments) {
            if (apartNum == apartment.getNumber()) {
                return apartment.isOccupied();
            }
        }
        return false;
    }

    // EFFECTS: sets the vacancy status of a given apartment #
    public void setVacancyStatusOfApartment(int apartNum, boolean occupancy) {
        for (Apartment apartment : apartments) {
            if (apartNum == apartment.getNumber()) {
                apartment.setOccupancy(occupancy);
            }
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("apartments", apartmentsToJson());
        return json;
    }

    // EFFECTS: returns apartments in the building as a JSON array
    private JSONArray apartmentsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Apartment a : apartments) {
            jsonArray.put(a.toJson());
        }

        return jsonArray;
    }
}
