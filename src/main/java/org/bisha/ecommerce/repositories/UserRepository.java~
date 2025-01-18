package org.bisha.ecommerce.repositories;

import org.bisha.ecommerce.enums.Role;
import org.bisha.ecommerce.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(long id);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    List<User> findByRole(Role role);

    List<User> findByBirthDateBefore(LocalDate date);

    List<User> findByAddressContaining(String address);

    List<User> findByCreatedAtAfter(LocalDateTime dateTime);

    Optional<User> findByTelephoneNumber(long telephoneNumber);

    List<User> findByNameContaining(String name);

    List<User> findByUsernameStartingWith(String prefix);

    List<User> findByEmailEndingWith(String domain);

    List<User> findByRoleAndCreatedAtAfter(Role role, LocalDateTime dateTime);

    List<User> findByActive(boolean active);
}