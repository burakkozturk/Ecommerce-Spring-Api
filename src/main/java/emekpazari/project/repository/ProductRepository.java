package emekpazari.project.repository;

import emekpazari.project.dto.ProductResponse;
import emekpazari.project.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


    List<Product> findByCategoryId(Long categoryId);

    List<Product> findByUserId(Long userId);

    List<Product> findByColorIgnoreCase(String color);

    List<Product> findByPriceBetween(double minPrice, double maxPrice);

    List<Product> findByTitleContainingIgnoreCase(String title);

    List<Product> findByStatus(int status);
}
