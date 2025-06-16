package service.impl;

import exceptions.ProductNotFoundException;
import model.Product;
import service.ProductService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProductServiceImpl implements ProductService {
    private final Map<String, Product> products = new ConcurrentHashMap<>();

    public void addProduct(Product product) {
        products.put(product.getId(), product);
    }

    public Product getProduct(String productId) {
        Product product = products.get(productId);
        if (product == null) throw new ProductNotFoundException(productId);

        return products.get(productId);
    }
}
