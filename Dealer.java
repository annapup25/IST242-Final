public class Dealer {
    private int dealerId;
    private String dealerName;

    public Dealer(int dealerId, String dealerName) {
        this.dealerId = dealerId;
        this.dealerName = dealerName;
    }

    // Getters and Setters
    public int getDealerId() {
        return dealerId;
    }

    public void setDealerId(int dealerId) {
        this.dealerId = dealerId;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    @Override
    public String toString() {
        return "Dealer{" +
                "dealerId=" + dealerId +
                ", dealerName='" + dealerName + '\'' +
                '}';
    }
}
