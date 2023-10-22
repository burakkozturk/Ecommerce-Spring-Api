package emekpazari.project.controller;

import emekpazari.project.dto.LatestProductResponse;
import emekpazari.project.dto.ProductDto;
import emekpazari.project.dto.ProductResponse;
import emekpazari.project.entity.Category;
import emekpazari.project.entity.Product;
import emekpazari.project.service.ProductService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/product")
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<ProductResponse> productResponses = productService.getAllProducts();
        return ResponseEntity.ok(productResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@RequestParam(name = "id") Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/category")
    public ResponseEntity<List<ProductResponse>> getProductsByCategory(@RequestParam(name = "category-id") Long categoryId) {
        List<ProductResponse> productResponses = productService.getProductsByCategory(categoryId);
        return ResponseEntity.ok(productResponses);
    }

    @GetMapping("/status")
    public ResponseEntity<List<ProductResponse>> getProductsByStatus(@RequestParam(name="status") int status) {
        List<ProductResponse> productResponses = productService.getProductsByStatus(status);
        return ResponseEntity.ok(productResponses);
    }

    @GetMapping("/user")
    public ResponseEntity<List<ProductResponse>> getProductsByUser(@RequestParam(name = "user-id") Long userId) {
        List<ProductResponse> productResponses = productService.getProductsByUser(userId);
        return ResponseEntity.ok(productResponses);
    }

    @GetMapping("/color")
    public ResponseEntity<List<ProductResponse>> getProductsByColorName(@RequestParam String color) {
        List<ProductResponse> productResponses = productService.getProductsByColorName(color);
        return ResponseEntity.ok(productResponses);
    }

    @GetMapping("/price-range")
    public List<ProductResponse> getProductsByPriceRange(@RequestParam(name = "min-price") double minPrice, @RequestParam(name = "max-price") double maxPrice) {
        return productService.getProductsByPriceRange(minPrice, maxPrice);
    }

    @GetMapping("/search")
    public List<ProductResponse> getProductsByTitle(@RequestParam(name="title") String title) {
        return productService.getProductsByTitle(title);
    }

    @GetMapping("/latest")
    public ResponseEntity<List<ProductResponse>> getLastSixProducts() {
        List<ProductResponse> productResponses = productService.getLastSixProducts();
        return ResponseEntity.ok(productResponses);
    }

    @GetMapping("/latest/{category}")
    public ResponseEntity<List<LatestProductResponse>> getLastSixProductsByCategory(@PathVariable Category category) {
        List<LatestProductResponse> productResponses = productService.getLastSixProductsByCategory(category);
        return ResponseEntity.ok(productResponses);
    }

    @PostMapping("/product")
    public ProductResponse saveProduct(@RequestBody ProductDto productDTO) {
        return productService.saveProduct(productDTO);
    }

    @PutMapping("product/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        try {
            ProductResponse updatedProduct = productService.updateProduct(id, productDto);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/approve")
    public ResponseEntity<Product> approveProduct(@RequestParam(name= "id") Long id) {
        try {
            Product approvedProduct = productService.approveProduct(id);
            return new ResponseEntity<>(approvedProduct, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("product/{id}")
    public void deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
    }
}
