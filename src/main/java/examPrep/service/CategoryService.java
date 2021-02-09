package examPrep.service;


import examPrep.model.entity.Category;
import examPrep.model.entity.CategoryName;

public interface CategoryService {
    void initCategories();

    Category findByCategoryName(CategoryName categoryName);


}
