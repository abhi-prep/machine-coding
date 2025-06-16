package service;

import model.Product;

public interface ProductService {
    void addProduct(Product product);
    Product getProduct(String productId);
}