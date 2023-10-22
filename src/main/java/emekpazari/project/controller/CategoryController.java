package emekpazari.project.controller;

import emekpazari.project.dto.CategoryDto;
import emekpazari.project.entity.Category;
import emekpazari.project.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/category")
@CrossOrigin
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping()
    public ResponseEntity<List<Category>> getAllCategory(){
        List<Category> categories = categoryService.getAllCategory();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/category")
    public ResponseEntity<Category> getCategoryById(@RequestParam(name = "id")  Long id) {
        Optional<Category> category = categoryService.getCategoryById(id);

        if(category.isPresent()) {
            return ResponseEntity.ok(category.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/category")
    public ResponseEntity<Category> addCategory(@RequestBody Category category) {
        Category newCategory = categoryService.addCategory(category);
        return ResponseEntity.status(201).body(newCategory);
    }


    @PutMapping("/category")
    public ResponseEntity<Category> updateCategory(@RequestBody CategoryDto categoryDto) {
        Category updatedCategory = categoryService.updateCategory(categoryDto);

        if (updatedCategory == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("category/{id}")
    public void deleteCategory(@PathVariable Long id){
        categoryService.deleteById(id);
    }


}
