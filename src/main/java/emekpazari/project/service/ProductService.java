package emekpazari.project.service;

import emekpazari.project.dto.LatestProductResponse;
import emekpazari.project.dto.ProductDto;
import emekpazari.project.dto.ProductResponse;
import emekpazari.project.entity.Category;
import emekpazari.project.entity.Product;
import emekpazari.project.entity.User;
import emekpazari.project.repository.CategoryRepository;
import emekpazari.project.repository.ProductRepository;
import emekpazari.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;


    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(this::convertToProductResponse)
                .collect(Collectors.toList());
    }

    public Optional<ProductResponse> getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(this::convertToProductResponse);
    }

    public List<ProductResponse> getProductsByStatus(int status) {
        List<Product> products = productRepository.findByStatus(status);
        return products.stream()
                .map(this::convertToProductResponse)
                .collect(Collectors.toList());
    }


    public List<ProductResponse> getProductsByCategory(Long categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId);
        return products.stream()
                .map(this::convertToProductResponse)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> getProductsByUser(Long userId) {
        List<Product> products = productRepository.findByUserId(userId);
        return products.stream()
                .map(this::convertToProductResponse)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> getProductsByColorName(String color) {
        List<Product> products = productRepository.findByColorIgnoreCase(color);
        return products.stream()
                .map(this::convertToProductResponse)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> getProductsByPriceRange(double minPrice, double maxPrice) {
        List<Product> products = productRepository.findByPriceBetween(minPrice, maxPrice);
        List<ProductResponse> productResponses = new ArrayList<>();
        for (Product product : products) {
            productResponses.add(convertToProductResponse(product));
        }
        return productResponses;
    }

    public List<ProductResponse> getProductsByTitle(String title) {
        List<Product> products = productRepository.findByTitleContainingIgnoreCase(title);
        List<ProductResponse> productResponses = new ArrayList<>();
        for (Product product : products) {
            productResponses.add(convertToProductResponse(product));
        }
        return productResponses;
    }


    public List<ProductResponse> getLastSixProducts() {
        List<Product> products = productRepository.findTop6ByOrderByIdDesc();
        return products.stream()
                .map(this::convertToProductResponse)
                .collect(Collectors.toList());
    }

    public List<LatestProductResponse> getLastSixProductsByCategory(Category category) {
        List<Product> products = productRepository.findTop6ByCategoryOrderByIdDesc(category);
        return products.stream()
                .map(this::convertToLatestProductResponse)
                .collect(Collectors.toList());
    }

    private LatestProductResponse convertToLatestProductResponse(Product product) {
        LatestProductResponse productResponse = new LatestProductResponse();

        productResponse.setId(product.getId());
        productResponse.setTitle(product.getTitle());
        productResponse.setDescription(product.getDescription());
        productResponse.setPrice(product.getPrice());
        productResponse.setColor(product.getColor());
        productResponse.setPhotoUrl(product.getPhotoUrl());


        return productResponse;
    }

    public ProductResponse saveProduct(ProductDto productDTO) {
        // Find the category and user based on the provided IDs
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        User user = userRepository.findById(productDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Create a new Product entity
        Product product = new Product();
        product.setTitle(productDTO.getTitle());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setPhotoUrl(productDTO.getPhotoUrl());
        product.setColor(productDTO.getColor());
        product.setCategory(category);
        product.setUser(user);
        product.setStatus(1);

        // Save the product to the database
        Product savedProduct = productRepository.save(product);

        // Convert the saved product to a ProductResponse and return it
        return convertToProductResponse(savedProduct);
    }

    public ProductResponse updateProduct(Long id, ProductDto productDto) {
        Product existingProduct = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));

        existingProduct.setTitle(productDto.getTitle());
        existingProduct.setDescription(productDto.getDescription());
        existingProduct.setPrice(productDto.getPrice());
        existingProduct.setColor(productDto.getColor());
        existingProduct.setPhotoUrl(productDto.getPhotoUrl());

        if (productDto.getCategoryId() != null) {
            Category category = categoryRepository.findById(productDto.getCategoryId()).orElseThrow(() -> new RuntimeException("Category not found"));
            existingProduct.setCategory(category);
        }

        if (productDto.getUserId() != null) {
            User user = userRepository.findById(productDto.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
            existingProduct.setUser(user);
        }

        Product updatedProduct = productRepository.save(existingProduct);

        // Güncellenen ürünü ProductResponse'a dönüştürün
        ProductResponse productResponse = new ProductResponse();
        // productResponse içindeki alanları doldurun

        return productResponse;
    }

    public Product approveProduct(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if(optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setStatus(2); // Statusu 2 olarak ayarla
            productRepository.save(product);
            return product;
        } else {
            throw new RuntimeException("Product not found with id : " + productId);
        }
    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }










    private ProductResponse convertToProductResponse(Product product) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setTitle(product.getTitle());
        productResponse.setDescription(product.getDescription());
        productResponse.setPrice(product.getPrice());
        productResponse.setColor(product.getColor());
        productResponse.setStatus(product.getStatus());
        productResponse.setPhotoUrl(product.getPhotoUrl());
        productResponse.setCategoryId(product.getCategory().getId()); // assuming getCategory() returns the category entity
        productResponse.setUserId(product.getUser().getId()); // assuming getUserId() returns the user ID
        return productResponse;
    }
}


