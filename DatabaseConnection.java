
//This requires jar dependencies and the db file, it works on my device, but I am not sure if that will transfer to you guys.

import javax.swing.*;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

    public class DatabaseConnection {
        private Connection connection;
        private final String DbFile;

        public DatabaseConnection(String DbFile) {
            this.DbFile = DbFile;
        }

        public Connection createConnection() {
            try {
                if (connection == null || connection.isClosed()) {
                    connection = DriverManager.getConnection("jdbc:sqlite:" + DbFile);
                    System.out.println("Connection established");
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return connection;
        }

        public void close() {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                    System.out.println("Connection closed");
                } else{
                    System.out.println("Connection already closed");
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        public static void main(String[] args) {
            System.out.println("Please wait for the file choosing window to appear.");
            // Create a JFileChooser to let the user select the database file
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select Database File");
            int userSelection = fileChooser.showOpenDialog(null);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File dbFile = fileChooser.getSelectedFile();
                // Create a new instance of DatabaseConnection with the selected file path
                DatabaseConnection databaseConnection = new DatabaseConnection(dbFile.getAbsolutePath());
                // Establish the connection
                try{
                    databaseConnection.createConnection();
                }catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                //might want to make some method calls and do operations here

                // Close the connection when done
                databaseConnection.close();
            } else {
                JOptionPane.showMessageDialog(null, "No database file selected.");
                System.exit(0);
            }
        }

        //this method could maybe benefit from carModel objects instead of Strings?
        public List<String> getCarModels(){
            List<String> carModels = new ArrayList<>();
            String query = "SELECT * FROM Models";

            try (Connection connection = createConnection();
                 Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)){

                while (resultSet.next()) {
                    int modelId = resultSet.getInt("model_id");
                    String modelName = resultSet.getString("model_name");
                    int basePrice = resultSet.getInt("model_base_price");
                    int brandId = resultSet.getInt("brand_id");

                    // Format the data as a string
                    String modelString = String.format("ID: %d, Name: %s, Base Price: %d, Brand ID: %d",
                            modelId, modelName, basePrice, brandId);
                    carModels.add(modelString);
                }
            } catch (SQLException e) {
                System.out.println("Error fetching models: " + e.getMessage());
            }

            return carModels;
        }

        //will write more soon.
    }
