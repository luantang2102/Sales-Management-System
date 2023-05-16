package com.salesManagement.dao.face;
import com.salesManagement.model.Product;
import java.util.List;

public interface IProductDao {
    List<Product> getAll();
    Product getByProductId(char[] id);
    int save(Product t);
    int update(Product t);
    int delete(char[] productId);

    List<Product> getByName(String name);
    List<Product> getByPrice(double price);
}

