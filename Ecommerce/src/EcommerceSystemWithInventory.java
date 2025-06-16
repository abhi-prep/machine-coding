import exceptions.CartNotFoundException;
import exceptions.CouponNotFoundException;
import exceptions.InsufficientStockException;
import exceptions.ProductNotFoundException;
import model.Cart;
import model.Coupon;
import model.Product;
import service.CartService;
import service.CheckoutService;
import service.CouponService;
import service.ProductService;
import service.impl.CartServiceImpl;
import service.impl.CheckoutServiceImpl;
import service.impl.CouponServiceImpl;
import service.impl.ProductServiceImpl;

public class EcommerceSystemWithInventory {
    public static void main(String[] args) {
        try {
            ProductService productService = new ProductServiceImpl();
            CouponService couponService = new CouponServiceImpl();
            CartService cartService = new CartServiceImpl(couponService);
            CheckoutService checkoutService = new CheckoutServiceImpl(cartService);
            // Add products with stock
            productService.addProduct(new Product("P1", "Laptop", 50000, 5));
            productService.addProduct(new Product("P2", "Mouse", 1000, 10));

            // Add a coupon
            couponService.addCoupon(new Coupon("NEWYEAR", 20, 3000));

            // Create cart
            Cart cart = cartService.createCart("CART123");

            try {
                // Try adding to cart with stock check
                Product laptop = productService.getProduct("P1");
                Product mouse = productService.getProduct("P2");

                try {
                    System.out.println("🎯 Adding items to cart:");
                    boolean addedLaptop = cartService.addToCart("CART123", laptop, 2); // Success
                    boolean addedMouse = cartService.addToCart("CART123", mouse, 1);   // Success
                    boolean failedAdd = cartService.addToCart("CART123", laptop, 10);  // Fail
                    System.out.println("Laptop added (2x): " + addedLaptop);
                    System.out.println("Mouse added (1x): " + addedMouse);
                    System.out.println("Laptop added again (10x): " + failedAdd + " ❌ - Not enough stock");

                } catch (InsufficientStockException | CartNotFoundException | IllegalArgumentException e){
                    System.err.println(e.getMessage());
                }

                try {
                    // Apply coupon
                    cartService.applyCoupon("CART123", "NEWYEAR");
                } catch (CartNotFoundException | CouponNotFoundException e){
                    System.err.println(e.getMessage());
                }


                try {
                    // Checkout
                    checkoutService.checkout("CART123");
                } catch (CartNotFoundException e){
                    System.err.println(e.getMessage());
                }


                // Remaining stock
                System.out.println("\n📦 Remaining Stock:");
                System.out.printf("Laptop: %d\n", laptop.getAvailableStock());
                System.out.printf("Mouse: %d\n", mouse.getAvailableStock());

            } catch (ProductNotFoundException e){
                System.err.println(e.getMessage());
            }

        } catch (Exception e){
            System.err.println("Unhandled Exception: " + e.getMessage());
        }

    }
}