package model;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class Product {
    final String id;
    final String name;
    final double price;
    final AtomicInteger stock;
    final ReentrantLock lock = new ReentrantLock();

    public Product(String id, String name, double price, int initialStock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = new AtomicInteger(initialStock);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean reserveStock(int quantity) {
        lock.lock();
        try {
            if (stock.get() >= quantity) {
                stock.addAndGet(-quantity);
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    void releaseStock(int quantity) {
        lock.lock();
        try {
            stock.addAndGet(quantity);
        } finally {
            lock.unlock();
        }
    }

    public int getAvailableStock() {
        return stock.get();
    }
}
