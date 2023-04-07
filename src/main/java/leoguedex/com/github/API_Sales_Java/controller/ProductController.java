package leoguedex.com.github.API_Sales_Java.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import leoguedex.com.github.API_Sales_Java.model.Product;
import leoguedex.com.github.API_Sales_Java.repository.ProductRepository;
import leoguedex.com.github.API_Sales_Java.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@Api(value = "Product API")
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    private final ProductRepository productRepository;

    public ProductController(ProductService productService, ProductRepository productRepository) {
        this.productService = productService;
        this.productRepository = productRepository;
    }

    @PostMapping
    @ApiOperation(value = "Insert a new Product")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Product save with successfully"),
            @ApiResponse(code = 400, message = "Validation Error"),
            @ApiResponse(code = 401, message = "Unauthorized User "),
            @ApiResponse(code = 403, message = "User without access right"),
    })
    @ResponseStatus(HttpStatus.CREATED)
    public Product includeProduct(@RequestBody @Valid Product product) {
        return productService.includeProduct(product);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update product information")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Product update with successfully"),
            @ApiResponse(code = 400, message = "Validation Error"),
            @ApiResponse(code = 401, message = "Unauthorized User "),
            @ApiResponse(code = 403, message = "User without access right"),
            @ApiResponse(code = 404, message = "Product not found"),
    })
    @ResponseStatus(HttpStatus.OK)
    public void updateProduct(@PathVariable Integer id, @RequestBody @Valid Product product) {
        productService.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete Product by Id")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Product deleted with successfully"),
            @ApiResponse(code = 400, message = "Validation Error"),
            @ApiResponse(code = 401, message = "Unauthorized User "),
            @ApiResponse(code = 403, message = "User without access right"),
            @ApiResponse(code = 404, message = "Product not found"),
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Find client by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Product found with successfully"),
            @ApiResponse(code = 404, message = "Product not found"),
    })
    public Product findProductById(@PathVariable Integer id) {
        return productService.findProductById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Find all Products")
    @ApiResponses({
            @ApiResponse(code = 403, message = "Usuario sem direito de acesso")
    })
    public List<Product> findAllProducts() {
        return productService.findAllProduct();
    }

    @GetMapping("/filter")
    @ResponseStatus(HttpStatus.OK)
    public List<Product> filterProduct(Product product) {
        return productService.filterProduct(product);
    }

}