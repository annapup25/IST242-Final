import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

// Main class for the Car Dealership GUI
public class CarDealershipGUI extends JFrame {
    private DatabaseConnection dbConnection; // Object to handle the database connection
    private JTextArea textArea; // Text area to display results

    // Constructor to initialize the GUI with a database connection
    public CarDealershipGUI(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
        initializeUI();
    }

    // Method to initialize the User Interface
    private void initializeUI() {
        setTitle("Car Dealership Management System"); // Set the title of the window
        setSize(800, 600); // Set the window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set the default close operation
        setLayout(new BorderLayout()); // Use BorderLayout for the main frame layout

        textArea = new JTextArea(); // Create a text area for displaying information
        add(new JScrollPane(textArea), BorderLayout.CENTER); // Add the text area to the center of the frame

        JPanel panel = new JPanel(); // Create a panel to hold the buttons
        panel.setLayout(new GridLayout(1, 3)); // Use a grid layout with 1 row and 3 columns

        // Create buttons for the different functionalities
        JButton btnModelsByColor = new JButton("List Models by Color");
        JButton btnBrandsByDealer = new JButton("List Brands by Dealer");
        JButton btnBasePrices = new JButton("List Base Prices");

        // Attach action listeners to the buttons
        btnModelsByColor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                listModelsByColor(); // Call method to list car models by color
            }
        });

        btnBrandsByDealer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                listBrandsByDealer(); // Call method to list brands by dealer
            }
        });

        btnBasePrices.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                listBasePrices(); // Call method to list base prices of car models
            }
        });

        // Add the buttons to the panel
        panel.add(btnModelsByColor);
        panel.add(btnBrandsByDealer);
        panel.add(btnBasePrices);

        // Add the panel to the top of the frame
        add(panel, BorderLayout.NORTH);
    }

    // Method to list car models by their color
    private void listModelsByColor() {
        // Get database operations object
        DatabaseConnection.DatabaseOperations operations = dbConnection.getDatabaseOperations();
        // Fetch car models and their options from the database
        List<CarModel> carModels = operations.getCarModels();
        List<Object[]> carOptions = operations.getCarOptions();
        StringBuilder result = new StringBuilder(); // StringBuilder to accumulate the results

        // Create a map to store model IDs and their corresponding colors
        Map<Integer, String> modelColorMap = new HashMap<>();

        // Fill the map with model IDs and colors from carOptions
        for (Object[] option : carOptions) {
            int modelId = (int) option[1];
            String color = (String) option[2];
            modelColorMap.put(modelId, color);
        }

        // Loop through car models and append models with the desired colors to the result
        for (CarModel model : carModels) {
            String color = modelColorMap.get(model.getModelId());
            if (color != null && (color.equalsIgnoreCase("Red") || color.equalsIgnoreCase("Green") ||
                    color.equalsIgnoreCase("Blue") || color.equalsIgnoreCase("Yellow"))) {
                result.append(model.getModelName()).append(" - ").append(color).append("\n");
            }
        }

        // Display the results in the text area
        textArea.setText(result.toString());
    }

    // Method to list brands available at each dealer
    private void listBrandsByDealer() {
        // Get database operations object
        DatabaseConnection.DatabaseOperations operations = dbConnection.getDatabaseOperations();
        // Fetch dealers, dealer-brand relationships, and brand information from the database
        List<Dealer> dealers = operations.getDealers();
        List<Object[]> dealerBrands = operations.getDealerBrands();
        List<Object[]> brands = operations.getBrands();
        StringBuilder result = new StringBuilder(); // StringBuilder to accumulate the results

        // Create a map for quick lookup of brand names by brand_id
        Map<Integer, String> brandMap = new HashMap<>();
        for (Object[] brand : brands) {
            brandMap.put((int) brand[0], (String) brand[1]);
        }

        // For each dealer, list the brands they carry
        for (Dealer dealer : dealers) {
            result.append(dealer.getDealerName()).append(": ");
            for (Object[] dealerBrand : dealerBrands) {
                if ((int) dealerBrand[1] == dealer.getDealerId()) {
                    String brandName = brandMap.get((int) dealerBrand[0]);
                    result.append(brandName).append(", ");
                }
            }
            // Remove trailing comma and space, then append a newline
            result.setLength(result.length() - 2);
            result.append("\n");
        }

        // Display the results in the text area
        textArea.setText(result.toString());
    }

    // Method to list the base prices of car models
    private void listBasePrices() {
        // Get database operations object
        DatabaseConnection.DatabaseOperations operations = dbConnection.getDatabaseOperations();
        // Fetch car models from the database
        List<CarModel> carModels = operations.getCarModels();
        StringBuilder result = new StringBuilder(); // StringBuilder to accumulate the results

        // Loop through car models and append each model and its base price to the result
        for (CarModel model : carModels) {
            result.append(model.getModelName()).append(" - $").append(model.getModelBasePrice()).append("\n");
        }

        // Display the results in the text area
        textArea.setText(result.toString());
    }

    public static void main(String[] args) {
        //temporary connection to get the file path from user
        DatabaseConnection tempConnection = new DatabaseConnection("");
        //The path for the selected database is held here, for later use.
        String DbFile = tempConnection.selectDatabase();
        //temporary connection closed after file path is recorded
        tempConnection.close();

        //As long as a file has been chosen
        if (DbFile != null) {
            //create a new instance of the gui, calling the getCarDealershipGUI method
            CarDealershipGUI gui = getCarDealershipGUI(DbFile);
            // Make sure it actually pops up, then the connection should stay until the window is closed
            gui.setVisible(true);
        }
    }

    // getCarDealershipGUI takes DbFile as a parameter and returns an instance of CarDealershipGUI
    private static CarDealershipGUI getCarDealershipGUI(String DbFile) {
        // The file path is used to create the usable connection to the database
        DatabaseConnection dbConnection = new DatabaseConnection(DbFile);

        // New instance of DatabaseConnection calls createConnection method to establish a connection
        dbConnection.createConnection();

        // Create an instance of the databaseOperations inner class for this connection.
        DatabaseConnection.DatabaseOperations databaseOperations = dbConnection.getDatabaseOperations();

        // Create an instance of the gui using that connection
        CarDealershipGUI gui = new CarDealershipGUI(dbConnection);
        return gui;
    }
}