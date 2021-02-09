package examPrep.repository;

import examPrep.model.entity.Category;
import examPrep.model.entity.CategoryName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
    Optional<Category> findByName(CategoryName categoryName);
    //TO DO check findCategoryName must be findByCategoryName;
}
