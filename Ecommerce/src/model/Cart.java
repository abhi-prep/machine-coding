package model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class Cart {
    final String cartId;
    final List<CartItem> items = new ArrayList<>();
    Coupon appliedCoupon;

    final ReentrantLock lock = new ReentrantLock();

    public Cart(String cartId) {
        this.cartId = cartId;
    }

    public void addItem(Product product, int quantity) {
        lock.lock();
        try {
            items.add(new CartItem(product, quantity));
        } finally {
            lock.unlock();
        }
    }

    public void applyCoupon(Coupon coupon) {
        this.appliedCoupon = coupon;
    }

    public double getTotalAmount() {
        return items.stream().mapToDouble(CartItem::getTotalPrice).sum();
    }

    public List<CartItem> getItems() {
        return items;
    }

    public double getDiscountedTotal() {
        double total = getTotalAmount();
        if (appliedCoupon == null) return total;
        double discount = total * appliedCoupon.discountPercentage / 100.0;
        return total - Math.min(discount, appliedCoupon.maxDiscount);
    }
}