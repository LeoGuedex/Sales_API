package leoguedex.com.github.API_Sales_Java.repository;

import leoguedex.com.github.API_Sales_Java.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}