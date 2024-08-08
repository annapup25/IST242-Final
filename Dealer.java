public class Dealer {
    private int dealerId; // ID of the dealer
    private String dealerName; // Name of the dealer

    // constructor to initialize a Dealer object
    public Dealer(int dealerId, String dealerName) {
        this.dealerId = dealerId;
        this.dealerName = dealerName;
    }

    // Getter for dealer ID
    public int getDealerId() {
        return dealerId;
    }

    // Setter for dealer ID
    public void setDealerId(int dealerId) {
        this.dealerId = dealerId;
    }

    // Getter for dealer name
    public String getDealerName() {
        return dealerName;
    }

    // Setter for dealer name
    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    // toString method of the Dealer object
    @Override
    public String toString() {
        return "Dealer{" +
                "dealerId=" + dealerId +
                ", dealerName='" + dealerName + '\'' +
                '}';
    }
}
