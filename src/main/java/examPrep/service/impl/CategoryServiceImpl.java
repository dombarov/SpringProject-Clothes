package examPrep.service.impl;

import examPrep.model.entity.Category;
import examPrep.model.entity.CategoryName;
import examPrep.model.service.CategoryServiceModel;
import examPrep.repository.CategoryRepository;
import examPrep.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void initCategories() {
        if (this.categoryRepository.count() == 0){
            Arrays.stream(CategoryName.values())
                    .forEach(categoryName -> {
                    this.categoryRepository.save(new Category(categoryName, String.format("Description for %s", categoryName.name())));
                    });

        }
    }

    @Override
    public Category findByCategoryName(CategoryName categoryName) {
        return this.categoryRepository.findByName(categoryName).orElse(null);
    }

//    @Override
//    public CategoryServiceModel findByName(CategoryName categoryName) {
//        return this.categoryRepository.findByName(categoryName)
//                .map(category -> this.modelMapper.map(category, CategoryServiceModel.class)).orElse(null);
//    }
}
