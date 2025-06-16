package service.impl;

import exceptions.CartNotFoundException;
import exceptions.CouponNotFoundException;
import exceptions.InsufficientStockException;
import model.Cart;
import model.Coupon;
import model.Product;
import service.CartService;
import service.CouponService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CartServiceImpl implements CartService {
    private final Map<String, Cart> carts = new ConcurrentHashMap<>();
    private final CouponService couponService;

    public CartServiceImpl(CouponService couponService) {
        this.couponService = couponService;
    }

    public Cart createCart(String cartId) {
        Cart cart = new Cart(cartId);
        carts.put(cartId, cart);
        System.out.println("Cart created with ID: " + cartId);
        return cart;
    }

    public boolean addToCart(String cartId, Product product, int quantity) {
        Cart cart = carts.get(cartId);
        if (cart == null)
            throw new CartNotFoundException("Cart not found");

        if (quantity <= 0)
            throw new IllegalArgumentException("Quantity must be greater than 0");

        boolean reserved = product.reserveStock(quantity);
        if (!reserved) {
            throw new InsufficientStockException(product.getName(), product.getAvailableStock(), quantity);
        }
        cart.addItem(product, quantity);
        return true;

    }

    public void applyCoupon(String cartId, String couponCode) {
        Cart cart = carts.get(cartId);
        if (cart == null)
            throw new CartNotFoundException(cartId);

        Coupon coupon = couponService.getCoupon(couponCode)
                .orElseThrow(() -> new CouponNotFoundException(couponCode));

        cart.applyCoupon(coupon);
    }

    public Cart getCart(String cartId) {
        return carts.get(cartId);
    }
}