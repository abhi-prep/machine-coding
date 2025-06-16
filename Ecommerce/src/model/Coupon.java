package model;

public class Coupon {
    final String code;
    final double discountPercentage;
    final double maxDiscount;

    public Coupon(String code, double discountPercentage, double maxDiscount) {
        this.code = code;
        this.discountPercentage = discountPercentage;
        this.maxDiscount = maxDiscount;
    }

    public String getCode() {
        return code;
    }
}
