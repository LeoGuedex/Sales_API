package leoguedex.com.github.API_Sales_Java.service;

import leoguedex.com.github.API_Sales_Java.model.Product;
import leoguedex.com.github.API_Sales_Java.repository.ProductRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private static final String PRODUCT_NOT_FOUND = "Product not found";

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product includeProduct(Product product) {
        return productRepository.save(product);
    }

    public void updateProduct(Integer id, Product product) {
        productRepository.findById(id)
                .map(productFound -> {
                    product.setId(productFound.getId());
                    productRepository.save(product);
                    return Void.TYPE;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, PRODUCT_NOT_FOUND));
    }

    public void deleteProduct(Integer id) {
        productRepository.findById(id)
                .map(productFound -> {
                    productRepository.delete(productFound);
                    return Void.TYPE;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, PRODUCT_NOT_FOUND));
    }

    public Product findProductById(Integer id){
        return productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, PRODUCT_NOT_FOUND));
    }

    public List<Product> findAllProduct(){
        return productRepository.findAll();
    }

    public List<Product> filterProduct(Product product){
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Product> productFiltrado = Example.of(product, exampleMatcher);

        return productRepository.findAll(productFiltrado);
    }

}
