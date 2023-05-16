package com.salesManagement.service;

import com.salesManagement.dao.face.IProductDao;
import com.salesManagement.dao.impl.ProductDao;
import com.salesManagement.model.*;

import java.util.List;

public class ProductService {
    private IProductDao productDao = new ProductDao();
    private Product product = new Product();

    public List<Product> getAll() {
        return productDao.getAll();
    }

    public Product getByProductId(char[] productId) {
        return productDao.getByProductId(productId);
    }

    public int save(Product product) {
        return productDao.save(product);
    }

    public int update(Product product){
        return productDao.update(product);
    }

    public int delete(char[] productId) {
        return productDao.delete(productId);
    }

    public List<Product> getByName(String name) {
        return productDao.getByName(name);
    }
    
    public List<Product> getByPrice(Double price) {
        return productDao.getByPrice(price);
    }
}
