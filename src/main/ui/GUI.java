package ui;

import model.*;
import model.Event;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;


// Constructs elements of GUI
public class GUI extends JFrame implements ActionListener, ListSelectionListener {


    // Citation: https://www.youtube.com/watch?v=5o3fMLPY7qY
    // Citation: https://www.youtube.com/watch?v=KOI1WbkKUpQ
    // Citation: https://github.com/BranislavLazic/SwingTutorials.git

    JList<Integer> list = new JList<>();
    DefaultListModel<Integer> model = new DefaultListModel<>();
    JLabel label = new JLabel();
    JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

    private JPanel infoPanel;
    private JFrame frame;
    private JPanel mainPanel;
    private Building building = new Building();
    private JButton addApartment;
    private JButton removeApartment;
    private JButton load;
    private JButton save;
    private static final String JSON_STORE = "./data/landlord.json";
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;
    private JButton maintenanceReport;

    // Constructs initial elements of GUI
    public GUI() {

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        buttons();

        mainPanel();

        infoPanel();

        frame();


        splitPane.setTopComponent(new JScrollPane(list));
        splitPane.setBottomComponent(mainPanel);
        splitPane.getBottomComponent().setMinimumSize(new Dimension(200, 200));
        splitPane.getTopComponent().setMinimumSize(new Dimension(200, 300));

        list.setModel(model);
        list.getSelectionModel().addListSelectionListener(this);


        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        //printLog();
    }

    // Constructs buttons
    private void buttons() {
        addApartment = new JButton("Add or Update");
        addApartment.addActionListener(this);
        addApartment.setActionCommand("addApartment");
        addApartment.setPreferredSize(new Dimension(150, 30));

        removeApartment = new JButton("Remove");
        removeApartment.addActionListener(this);
        removeApartment.setActionCommand("removeApartment");
        removeApartment.setPreferredSize(new Dimension(150, 30));

        load = new JButton("Load Building");
        load.addActionListener(this);
        load.setActionCommand("loadBuilding");
        load.setPreferredSize(new Dimension(150, 30));

        save = new JButton("Save Building");
        save.addActionListener(this);
        save.setActionCommand("saveBuilding");
        save.setPreferredSize(new Dimension(150, 30));

        maintenanceReport = new JButton("Add Report");
        maintenanceReport.addActionListener(this);
        maintenanceReport.setActionCommand("maintenanceReport");
        maintenanceReport.setPreferredSize(new Dimension(150, 30));
    }

    // Constructs Frame
    private void frame() {
        frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.add(mainPanel, BorderLayout.WEST);
        frame.add(infoPanel, BorderLayout.EAST);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                onExit();
            }
        });
        frame.setTitle("myBuilding");
        frame.add(splitPane);
    }

    // citation: https://stackoverflow.com/questions/60516720/java-how-to-print-message-when-a-jframe-is-closed
    // prints events to console
    public void onExit() {
        System.out.println("default close operation");
        for (Event event : EventLog.getInstance()) {
            System.out.println(event);
        }
        System.exit(0);
    }


    // Constructs panel that holds button and JList
    private void mainPanel() {
        mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));
        mainPanel.add(addApartment);
        mainPanel.add(removeApartment);
        mainPanel.add(maintenanceReport);
        mainPanel.add(load);
        mainPanel.add(save);
        mainPanel.add(splitPane);
        mainPanel.setPreferredSize(new Dimension(400, 600));
        ImageIcon pic = new ImageIcon("./data/Artboard 1.jpg");
        mainPanel.add(new JLabel(pic)).setPreferredSize(new Dimension(400, 400));
    }

    // Constructs panel that displays info
    private void infoPanel() {
        infoPanel = new JPanel();
        infoPanel.add(label);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));
        infoPanel.setLayout(new GridLayout(2, 1));
        infoPanel.setPreferredSize(new Dimension(300, 600));

    }

    // Constructs pop-up that supports user input
    // Citation: http://www.java2s.com/Tutorials/Java/Swing_How_to/JsOptionPane/Create_Multiple_input_in_JOptionPane_showInputDialog.htm
    public void addApartmentPanel() {
        JTextField tenantName = new JTextField(5);
        JTextField number = new JTextField(5);
        JTextField rent = new JTextField(5);
        JCheckBox occupied = new JCheckBox("Vacant");

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Suite #:"));
        myPanel.add(number);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(occupied);
        myPanel.add(Box.createHorizontalStrut(20)); // a spacer
        myPanel.add(new JLabel("Tenant Name:"));
        myPanel.add(tenantName);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("Rent $:"));
        myPanel.add(rent);

        objectMakeUpdate(tenantName, number, rent, occupied, myPanel);
    }

    // Constructs pop-up where people add reports
    public void addReportPanel() {
        JTextField report = new JTextField(30);
        JTextField date = new JTextField(10);

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Description:"));
        myPanel.add(report);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("YYYY-MM-DD:"));
        myPanel.add(date);


        int index = list.getSelectedIndex();
        Integer apartment = list.getSelectedValue();

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please Enter Report Details", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {

            MaintenanceReport mr = new MaintenanceReport(LocalDate.parse(date.getText()), report.getText());

            building.addMaintenanceRecordToApartment(apartment, mr);
            updateLabel();
        }

    }

    // refreshes apartment status
    private void objectMakeUpdate(JTextField tenantName, JTextField number, JTextField rent, JCheckBox occupied,
                                  JPanel myPanel) {
        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please Enter Apartment Details", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            if (!building.inBuilding(parseInt(number.getText()))) {
                makeApartment(tenantName, number, rent, occupied);
            } else {
                updateApartment(tenantName, rent, occupied);
            }
        }
    }

    // updates apartment if entering apartment with same number
    private void updateApartment(JTextField tenantName, JTextField rent, JCheckBox occupied) {
        if (!occupied.isSelected()) {
            Integer apartment = list.getSelectedValue();
            building.setRentOfApartment(apartment, parseInt(rent.getText()));
            building.setTenantNameOfApartment(apartment, tenantName.getText());
            building.setVacancyStatusOfApartment(apartment, true);
            updateLabel();
        } else {
            Integer apartment = list.getSelectedValue();
            building.setRentOfApartment(apartment, 0);
            building.setTenantNameOfApartment(apartment, "");
            building.setVacancyStatusOfApartment(apartment, false);
            updateLabel();
        }
    }

    // Constructs a new Apartment and adds to JList
    private void makeApartment(JTextField tenantName, JTextField number, JTextField rent, JCheckBox occupied) {
        if (!occupied.isSelected()) {
            Apartment apartment = new Apartment(tenantName.getText(), parseInt(number.getText()),
                    parseInt(rent.getText()), true);
            model.addElement(apartment.getNumber());
            building.addApartment(apartment);
        } else {
            Apartment apartment = new Apartment("", parseInt(number.getText()), 0, false);
            model.addElement(apartment.getNumber());
            building.addApartment(apartment);
        }
    }

    // initiates action when button is clicked
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("addApartment")) {
            addApartmentPanel();
        }
        if (e.getActionCommand().equals("removeApartment")) {
            int index = list.getSelectedIndex();
            Integer apartment = list.getSelectedValue();
            model.remove(index);
            building.removeApartment(apartment);
            list.validate();
            list.repaint();
        }
        if (e.getActionCommand().equals("maintenanceReport")) {
            if (!list.isSelectionEmpty()) {
                addReportPanel();
            }
        }
        if (e.getActionCommand().equals("saveBuilding")) {
            try {
                jsonWriter.open();
                jsonWriter.write(building);
                jsonWriter.close();
                System.out.println("Saved to " + JSON_STORE);
            } catch (FileNotFoundException exception) {
                System.out.println("Unable to write to file: " + JSON_STORE);
            }
        }
        if (e.getActionCommand().equals("loadBuilding")) {
            try {
                building = jsonReader.read();
                System.out.println("Loaded from " + JSON_STORE);
                for (Apartment apartment : building.getApartments()) {
                    if (!model.contains(apartment.getNumber())) {
                        model.addElement(apartment.getNumber());
                    }
                }
            } catch (IOException exception) {
                System.out.println("Unable to read from file: " + JSON_STORE);
            }
        }
    }

    // sends to method to avoid using parameter
    @Override
    public void valueChanged(ListSelectionEvent e) {
        updateLabel();
    }

    // Updates the label for so changes are visible
    private void updateLabel() {
        Integer apartment = list.getSelectedValue();
        try {
            label.setText(convertToMultiline("Name: " + building.getTenantNameOfApartment(apartment) + "\n"
                    + "      Rent: $" + building.getRentOfApartment(apartment)) + "\n"
                    + "\n" + "Maintenance:" + "\n" + reportsToString(apartment) + "\n");
        } catch (NullPointerException e) {
            label.setText("");
        }
    }

    // Converts Maintenance Report to a String
    private List<String> reportsToString(Integer apartment) {
        List<String> reportList = new ArrayList<>();
        for (MaintenanceReport mr : building.getMaintenanceRecordOfApartment(apartment)) {
            String s = "\n" + "Date: " + mr.getDate() + "\n" + "Description: " + mr.getDescription() + "\n";
            reportList.add(s);
        }
        return reportList;
    }

    // Helper method that formats label to multi-line
    // Citation: https://stackoverflow.com/questions/2152742/java-swing-multiline-labels
    public static String convertToMultiline(String orig) {
        return "<html>" + orig.replaceAll("\n", "<br>");
    }
}
