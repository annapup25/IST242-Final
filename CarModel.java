public class CarModel {
    private int modelId; // ID of the car model
    private String modelName; // name of the car model
    private int modelBasePrice; // base price of the car model
    private int brandId; // ID of the brand

    // initialize a CarModel object
    public CarModel(int modelId, String modelName, int modelBasePrice, int brandId) {
        this.modelId = modelId;
        this.modelName = modelName;
        this.modelBasePrice = modelBasePrice;
        this.brandId = brandId;
    }

    // getters and setters
    public int getModelId() {
        return modelId;
    }

    // Setter for model ID
    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    // Getter for model name
    public String getModelName() {
        return modelName;
    }

    // Setter for model name
    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    // Getter for model base price
    public int getModelBasePrice() {
        return modelBasePrice;
    }

    // Setter for model base price
    public void setModelBasePrice(int modelBasePrice) {
        this.modelBasePrice = modelBasePrice;
    }

    // Setter for brand ID
    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    //  toString method of the CarModel object
    @Override
    public String toString() {
        return "CarModel{" +
                "modelId=" + modelId +
                ", modelName='" + modelName + '\'' +
                ", modelBasePrice=" + modelBasePrice +
                ", brandId=" + brandId +
                '}';
    }
}
