package leoguedex.com.github.API_Sales_Java.repository;

import leoguedex.com.github.API_Sales_Java.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Integer> {

    Optional<Users> findByLogin(String login);

}
