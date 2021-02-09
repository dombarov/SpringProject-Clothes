package examPrep.repository;

import examPrep.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@RequestMapping
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
}
