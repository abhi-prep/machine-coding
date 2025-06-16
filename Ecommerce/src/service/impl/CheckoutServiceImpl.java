package service.impl;


import exceptions.CartNotFoundException;
import model.Cart;
import service.CartService;
import service.CheckoutService;

public class CheckoutServiceImpl implements CheckoutService {
    private final CartService cartService;

    public CheckoutServiceImpl(CartService cartService) {
        this.cartService = cartService;
    }

    public void checkout(String cartId) {
        Cart cart = cartService.getCart(cartId);
        if (cart == null) throw new CartNotFoundException(cartId);

        double original = cart.getTotalAmount();
        double finalAmount = cart.getDiscountedTotal();

        System.out.printf("✅ Checkout for Cart [%s]:\n", cartId);
        System.out.printf("   Items: %d\n", cart.getItems().size());
        System.out.printf("   Original: ₹%.2f\n", original);
        System.out.printf("   Final (after discount): ₹%.2f\n", finalAmount);
    }
}