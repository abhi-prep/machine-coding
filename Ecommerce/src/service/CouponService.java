package service;

import model.Coupon;

import java.util.Optional;

public interface CouponService {
    void addCoupon(Coupon coupon);
    Optional<Coupon> getCoupon(String code);
}