
//This requires jar dependencies and the db file, it works on my device, but I am not sure if that will transfer to you guys.

import javax.swing.*;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

    public class DatabaseConnection {
        private Connection connection;
        private String DbFile;

        public DatabaseConnection(String DbFile) {
            this.DbFile = DbFile;
        }

        public Connection createConnection() {
            try {
                if (connection == null || connection.isClosed()) {
                    connection = DriverManager.getConnection("jdbc:sqlite:" + DbFile);
                    System.out.println("Connection established to: " + DbFile);
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

        public String selectDatabase(){
            System.out.println("Please wait for the file choosing window to appear.");
            // Create a JFileChooser to let the user select the database file
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select Database File");
            int userSelection = fileChooser.showOpenDialog(null);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File dbFile = fileChooser.getSelectedFile();
                this.DbFile = dbFile.getAbsolutePath();
                // Establish the connection
                try{
                    this.createConnection();
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "No database file selected.");
                System.exit(0);
            }
            return this.DbFile;
        }

        public List<CarModel> getCarModels(){
            List<CarModel> carModels = new ArrayList<>();
            String query = "SELECT model_id, model_name, model_base_price, brand_id FROM Models";

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)){

                while (resultSet.next()) {
                    int modelId = resultSet.getInt("model_id");
                    String modelName = resultSet.getString("model_name");
                    int modelBasePrice = resultSet.getInt("model_base_price");
                    int brandId = resultSet.getInt("brand_id");

                    carModels.add(new CarModel(modelId, modelName, modelBasePrice, brandId));
                }
            } catch (SQLException e) {
                System.out.println("Error fetching models: " + e.getMessage());
            }

            return carModels;
        }

        public List<Dealer> getDealers() {
            List<Dealer> dealers = new ArrayList<>();
            String query = "SELECT dealer_id, dealer_name FROM Dealer";

            try(Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(query)){

                while (rs.next()) {
                    int dealerId = rs.getInt("dealer_id");
                    String dealerName = rs.getString("dealer_name");

                    dealers.add(new Dealer(dealerId, dealerName));
                }
            }catch (SQLException e){
                System.out.println("Error getting dealers: "  + e.getMessage());
            }

            return dealers;
        }

        public List<Object[]> getBrands(){
            List<Object[]> brands = new ArrayList<>();
            String query = "SELECT brand_id, brand_name FROM Brands";

            try(Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query)){
                while (rs.next()) {
                    int brandId = rs.getInt("brand_id");
                    String brandName = rs.getString("brand_name");

                    brands.add(new Object[]{brandId, brandName});
                }
            }catch (SQLException e){
                System.out.println("Error getting brands: "  + e.getMessage());
            }
            return brands;
        }

        public List<Object[]> getDealerBrands(){
            List<Object[]> dealerBrands = new ArrayList<>();
            String query = "SELECT brand_id, brand_id FROM DealerBrands";

            try(Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query)){
                while (rs.next()) {
                    int brandId = rs.getInt("brand_id");
                    int dealerId = rs.getInt("dealer_id");

                    dealerBrands.add(new Object[]{brandId, dealerId});
                }
            }catch (SQLException e){
                System.out.println("Error getting Dealer_Brands: "  + e.getMessage());
            }
            return dealerBrands;
        }

        public List<Object[]> getCarOptions(){
            List<Object[]> carOptions = new ArrayList<>();
            String query = "SELECT option_set_id, model_id, color FROM Car_Options";

            try(Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(query)){
                while (rs.next()) {
                    int optionSetId = rs.getInt("option_set_id");
                    int modelId = rs.getInt("model_id");
                    String color = rs.getString("color");

                    carOptions.add(new Object[]{optionSetId, modelId, color});
                }
            }catch (SQLException e){
                System.out.println("Error getting Car_Options: "  + e.getMessage());
            }
            return carOptions;
        }
    }
