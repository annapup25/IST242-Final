
// This requires JAR dependencies and the Database file to function.

import javax.swing.*;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
// Import needed packages

    public class DatabaseConnection {
        private Connection connection;
        // Initialize connection variable
        private String DbFile;
        // DbFile will hold the value for the path to the database

        // Constructor method DatabaseConnection takes DbFile as a parameter.
        public DatabaseConnection(String DbFile) {
            // Ensure that the instance of DbFile holds the value for the path.
            this.DbFile = DbFile;
        }

        // Method createConnection established the connection to the database
        public Connection createConnection() {
            // Try catch in case any error occurs
            try {
                // As long as the connection doesn't exist or isn't open
                if (connection == null || connection.isClosed()) {
                    // A connection to the selected file is established
                    connection = DriverManager.getConnection("jdbc:sqlite:" + DbFile);
                    // A message confirming this is printed to the console
                    System.out.println("Connection established to: " + DbFile);
                }
                // Catches SQLException and prints out the message
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            // Established connection to the file is returned
            return connection;
        }

        // Method closes the connection once it is no longer needed
        public void close() {
            // Try-catch to print out an error message id something goes wrong
            try {
                // If the connection is open and exists
                if (connection != null && !connection.isClosed()) {
                    // Close the connection and print out a message to the console
                    connection.close();
                    System.out.println("Connection closed");
                    // Else print out a message saying it is already closed
                } else{
                    System.out.println("Connection already closed");
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        // Method selectDatabase allows the user to choose the file they want to access.
        public String selectDatabase(){
            // Once called, a message to notify the user to wait while a new window appears is printed to the console.
            System.out.println("Please wait for the file choosing window to appear.");
            // Create a JFileChooser to let the user select the database file
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select Database File");
            // A window appears for the user to choose a file, in the middle of the screen.
            int userSelection = fileChooser.showOpenDialog(null);

            // As long as the user has confirmed their selection
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                // Create a local variable dbFile to retrieve the file selected in fileChooser.
                File dbFile = fileChooser.getSelectedFile();
                // Store the complete path of the selected file in DbFile, which allows the entire class access to it.
                this.DbFile = dbFile.getAbsolutePath();
                // Try-catch to establish a connection to the selected database.
                try{
                    // For the current instance of DatabaseConnection, establish a connection.
                    this.createConnection();
                    // If an error occurs, print out a message
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
                // Otherwise if the user hasn't confirmed their selection, or exits the window prematurely
            } else {
                // Output that nothing has been selected and terminate the program.
                JOptionPane.showMessageDialog(null, "No database file selected.");
                System.exit(0);
            }
            // Return the path of the database we want to connect to.
            return this.DbFile;
        }

        // Method to return an instance of DatabaseOperations,
        // This allows us to have better control of when the inner class is accessed
        public DatabaseOperations getDatabaseOperations(){
            return new DatabaseOperations();
        }

        // Inner class to separate responsibility of the database query methods from the connection methods
        public class DatabaseOperations {

            // Method getCarModels creates an ArrayList of CarModel objects.
            public List<CarModel> getCarModels() {
                List<CarModel> carModels = new ArrayList<>();
                // A query to select the needed items from the Models table
                String query = "SELECT model_id, model_name, model_base_price, brand_id FROM Models";

                // Try to execute the query, using statement to contact the database and resultset to execute and save the results.
                try (Statement statement = connection.createStatement();
                     ResultSet resultSet = statement.executeQuery(query)) {

                    // As long as there is something in the database left to process
                    while (resultSet.next()) {
                        // Assign each variable with the appropriate item from the database
                        int modelId = resultSet.getInt("model_id");
                        String modelName = resultSet.getString("model_name");
                        int modelBasePrice = resultSet.getInt("model_base_price");
                        int brandId = resultSet.getInt("brand_id");

                        // Then build a new CarModel object and add it to the arraylist
                        carModels.add(new CarModel(modelId, modelName, modelBasePrice, brandId));
                    }
                    // If an error occurs print out a message
                } catch (SQLException e) {
                    System.out.println("Error fetching models: " + e.getMessage());
                }

                return carModels;
            }

            // Method getDealers is similar to getCarModels, however it uses Dealer objects instead.
            public List<Dealer> getDealers() {
                // Create an arraylist of dealer objects
                List<Dealer> dealers = new ArrayList<>();
                // Formulate a query to select what is needed from the Dealer table in the database
                String query = "SELECT dealer_id, dealer_name FROM Dealer";

                // Try to execute the query, statement contacts the database and result set executes and stores the information.
                try (Statement statement = connection.createStatement();
                     ResultSet rs = statement.executeQuery(query)) {

                    // As long as there is something in the database left to read
                    while (rs.next()) {
                        // Assign local variables with their appropriate counterparts from the database
                        int dealerId = rs.getInt("dealer_id");
                        String dealerName = rs.getString("dealer_name");

                        // Then create a new Dealer object using those variable and add it to the list.
                        dealers.add(new Dealer(dealerId, dealerName));
                    }
                    // If an error occurs print out a message.
                } catch (SQLException e) {
                    System.out.println("Error getting dealers: " + e.getMessage());
                }

                return dealers;
            }

            // Method getBrands uses List<Object[]> where it can store brand information as an object array without needing a defined class.
            public List<Object[]> getBrands() {
                List<Object[]> brands = new ArrayList<>();
                // Query selects the needed information from the Brands table of the database
                String query = "SELECT brand_id, brand_name FROM Brands";

                // Try to execute the query and catalog the results
                try (Statement statement = connection.createStatement();
                     ResultSet rs = statement.executeQuery(query)) {
                    // As long as something is left in the database
                    while (rs.next()) {
                        // Assign values to the local variables using the data in the database
                        int brandId = rs.getInt("brand_id");
                        String brandName = rs.getString("brand_name");

                        // Create a new object for the brand and add it to the list.
                        brands.add(new Object[]{brandId, brandName});
                    }
                    // If something goes wrong print an error message.
                } catch (SQLException e) {
                    System.out.println("Error getting brands: " + e.getMessage());
                }
                return brands;
            }

            // getDealerBrands method accesses the DealerBrands data table
            public List<Object[]> getDealerBrands() {
                // Create a new array list of dealerBrands objects
                List<Object[]> dealerBrands = new ArrayList<>();
                // Query selects the brandId and dealerId from the DealerBrands table
                String query = "SELECT brand_id, dealer_id FROM DealerBrands";

                try (Statement statement = connection.createStatement();
                     ResultSet rs = statement.executeQuery(query)) {
                    // Cycle through all the info in the database table
                    while (rs.next()) {
                        // Get the values for brand_id and dealer_id
                        int brandId = rs.getInt("brand_id");
                        int dealerId = rs.getInt("dealer_id");

                        // Use those values to create a new object and add it to the list.
                        dealerBrands.add(new Object[]{brandId, dealerId});
                    }
                    // If an error occurs catch it and print a message
                } catch (SQLException e) {
                    System.out.println("Error getting Dealer_Brands: " + e.getMessage());
                }
                return dealerBrands;
            }

            // getCarOptions creates an arraylist of objects
            public List<Object[]> getCarOptions() {
                List<Object[]> carOptions = new ArrayList<>();
                // Query selects the needed information from the Car_Options table
                String query = "SELECT option_set_id, model_id, color FROM Car_Options";

                try (Statement statement = connection.createStatement();
                     ResultSet rs = statement.executeQuery(query)) {
                    // Cycle through until we have looked through all the data in the Car_Options table
                    while (rs.next()) {
                        // Extract the data and assign it to its respective variable here
                        int optionSetId = rs.getInt("option_set_id");
                        int modelId = rs.getInt("model_id");
                        String color = rs.getString("color");

                        // Use those variables to create a new object and add it to the list
                        carOptions.add(new Object[]{optionSetId, modelId, color});
                    }
                    // If an error occurs print out a message.
                } catch (SQLException e) {
                    System.out.println("Error getting Car_Options: " + e.getMessage());
                }
                return carOptions;
            }
        }
    }
