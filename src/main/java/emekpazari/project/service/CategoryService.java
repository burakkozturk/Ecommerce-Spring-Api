package emekpazari.project.service;

import emekpazari.project.dto.CategoryDto;
import emekpazari.project.entity.Category;
import emekpazari.project.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategory(){

        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(Long id){

        return categoryRepository.findById(id);
    }

    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }


    public Category updateCategory(CategoryDto categoryDto) {
        Optional<Category> existingCategory = categoryRepository.findById(categoryDto.getId());

        if (existingCategory.isPresent()) {
            Category category = existingCategory.get();
            category.setName(categoryDto.getCategory().getName());

            return categoryRepository.save(category);
        } else {
            return null;
        }
    }


    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
