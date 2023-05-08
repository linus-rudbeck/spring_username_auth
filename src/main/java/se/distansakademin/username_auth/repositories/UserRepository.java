package se.distansakademin.username_auth.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import se.distansakademin.username_auth.models.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    public User findByUsername(String username);
}
