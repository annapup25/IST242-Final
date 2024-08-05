public class CarModel {
    private int modelId;
    private String modelName;
    private int modelBasePrice;
    private int brandId;

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

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public int getModelBasePrice() {
        return modelBasePrice;
    }

    public void setModelBasePrice(int modelBasePrice) {
        this.modelBasePrice = modelBasePrice;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

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
