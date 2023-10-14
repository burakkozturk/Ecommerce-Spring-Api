package emekpazari.project.service;

import emekpazari.project.entity.Product;
import emekpazari.project.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> getProductsByUser(Long userId) {

        return productRepository.findByUserId(userId);
    }

    public List<Product> getProductsByColor(String color) {

        return productRepository.findByColor(color);
    }


    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product productDetails) {
        Optional<Product> optionalProduct = productRepository.findById(id);

        if(optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setTitle(productDetails.getTitle());
            product.setDescription(productDetails.getDescription());
            product.setPrice(productDetails.getPrice());
            product.setPhotoUrl(productDetails.getPhotoUrl());
            product.setColor(productDetails.getColor());
            product.setCategory(productDetails.getCategory());
            product.setUser(productDetails.getUser());
            return productRepository.save(product);
        }
        return null;
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
