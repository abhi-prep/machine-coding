package service;

import model.Cart;
import model.Product;

public interface CartService {
    Cart createCart(String cartId);
    boolean addToCart(String cartId, Product product, int quantity);
    void applyCoupon(String cartId, String couponCode);
    Cart getCart(String cartId);
}
