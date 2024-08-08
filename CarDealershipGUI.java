import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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

        ButtonHandler action = new ButtonHandler();


        // Attach listeners to the buttons
        btnModelsByColor.addActionListener(action);

        btnBrandsByDealer.addActionListener(action);

        btnBasePrices.addActionListener(action);

        // Add the buttons to the panel
        panel.add(btnModelsByColor);
        panel.add(btnBrandsByDealer);
        panel.add(btnBasePrices);

        // Add the panel to the top of the frame
        add(panel, BorderLayout.NORTH);
    }

    // Inner class to handle the operation happening when clicked
    private class ButtonHandler implements ActionListener {

        public void actionPerformed(ActionEvent e)
        {
            String action = e.getActionCommand();
            switch (action)
            {
                case "List Models by Color":
                    listModelsByColor();
                    break;
                case "List Brands by Dealer":
                    listBrandsByDealer();
                    break;
                case "List Base Prices":
                    listBasePrices();
                    break;

            }
        }

    }

    // Method to list car models by their color
    private void listModelsByColor()
    {

        textArea.setText("Models by Color:\n");
        String[] colorsOfInterest = {"red", "green", "blue", "yellow"};


        // Retrieve data from the database
        List<CarModel> carModels = dbConnection.getDatabaseOperations().getCarModels();
        List<Object[]> carOptions = dbConnection.getDatabaseOperations().getCarOptions();

        // Arrays that are used to connect the colors of interest to the proper indexes
        List[] colorLists;
        colorLists = new List[colorsOfInterest.length];
        for (int i = 0; i < colorLists.length; i++) {
            colorLists[i] = new ArrayList<>();
        }

        // Arrays used to store both model names and model IDs
        String[] modelNames = new String[carModels.size()];
        int[] modelIds = new int[carModels.size()];

        // Arrays are populated with model information
        for (int i = 0; i < carModels.size(); i++) {
            CarModel model = carModels.get(i);
            modelIds[i] = model.getModelId();
            modelNames[i] = model.getModelName();
        }

        // Car options are processes, as well as the cars are grouped by color
        for (Object[] option : carOptions) {
            int modelId = (int) option[1];
            String color = ((String) option[2]).toLowerCase();

            // The index for the color is found
            int colorIndex = -1;
            for (int i = 0; i < colorsOfInterest.length; i++) {
                if (color.equals(colorsOfInterest[i])) {
                    colorIndex = i;

                }
            }

            if (colorIndex != -1) {
                // Model name is found with the model ID
                String modelName = null;
                for (int i = 0; i < modelIds.length; i++) {
                    if (modelIds[i] == modelId) {
                        modelName = modelNames[i];

                    }
                }

                if (modelName != null) {
                    // The model name and color are formatted into the correct color list
                    String first = color.substring(0,1).toUpperCase();
                    String rest = color.substring(1);
                    colorLists[colorIndex].add(modelName + " - " + first+rest);
                }
            }
        }

        // Results are displayed
        for (int i = 0; i < colorsOfInterest.length; i++) {
            for (Object modelInfo : colorLists[i]) {
                textArea.append(modelInfo + "\n");
            }
        }
    }


    //Method to list brands by their dealer
    private void listBrandsByDealer()
    {
        textArea.setText("Brands by Dealer:\n");

        List<Dealer> dealers = dbConnection.getDatabaseOperations().getDealers();
        List<Object[]> brands = dbConnection.getDatabaseOperations().getBrands();
        List<Object[]> dealerBrands = dbConnection.getDatabaseOperations().getDealerBrands();


        for (Dealer dealer: dealers)
        {
            //Each dealer is found and displayed
            textArea.append(dealer.getDealerName() + ": ");

            // list used to store the names to be able to add commas between them later
            List<String> formattedNames = new ArrayList<>();

            //brands associated with the different dealers are found
            for(Object[] dealerBrand: dealerBrands)
            {
                int brandID = (int) dealerBrand[0];
                int dealerID  = (int) dealerBrand[1];

                if(dealer.getDealerId() == dealerID)
                {
                    for (Object[] brand : brands)
                    {
                        if ((int)brand[0] == brandID)
                        {

                            formattedNames.add((String) brand[1]); //brand name is added to the formattedName list if it is part of that dealership



                        }
                    }
                }
            }
            if(!formattedNames.isEmpty())
            {
                String formatOutput = String.join(", ", formattedNames); // commas added between all brands, except the last brand in each line
                textArea.append(formatOutput + "\n");
            }
        }
    }

    //Method to list cars alongside their base prices
    private void listBasePrices()
    {
        textArea.setText("Base Prices:\n");


        List<CarModel> carModels = dbConnection.getDatabaseOperations().getCarModels();

        // car models and their bases prices are found
        for (CarModel model : carModels) {

            double formattedPrice = model.getModelBasePrice() * 1.0; //each price is formatted to be a double with one decimal place

            textArea.append(model.getModelName() + " - $"+ formattedPrice + "\n"); //models and their prices are appended with dollar sign and one decimal place
        }
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

        // Create an instance of the gui using that connection
        return new CarDealershipGUI(dbConnection);
    }
}