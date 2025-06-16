package service.impl;

import model.Coupon;
import service.CouponService;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class CouponServiceImpl implements CouponService {
    private final Map<String, Coupon> coupons = new ConcurrentHashMap<>();

    public void addCoupon(Coupon coupon) {
        coupons.put(coupon.getCode(), coupon);
        System.out.println("Coupon: " + coupon.getCode() + " added.");
    }

    public Optional<Coupon> getCoupon(String code) {
        return Optional.ofNullable(coupons.get(code));
    }
}