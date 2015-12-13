package tdd.solid.module.order.domain;

public class InventoryItem {
    private String itemCode;
    private String pricingCalculation;
    private double pricePerUnit;
    private double quantity;
    private double discount;

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getPricingCalculation() {
        return pricingCalculation;
    }

    public void setPricingCalculation(String pricingCalculation) {
        this.pricingCalculation = pricingCalculation;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

}
