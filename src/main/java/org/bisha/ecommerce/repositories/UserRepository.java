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

    Optional<List<User>> findByRole(Role role);

    Optional<List<User>> findByBirthDateBefore(LocalDate date);

    Optional<List<User>> findByAddressContaining(String address);

    Optional<List<User>> findByCreatedAtAfter(LocalDateTime dateTime);

    Optional<List<User>> findByTelephoneNumber(long telephoneNumber);

    Optional<List<User>> findByNameContaining(String name);

    Optional<List<User>> findByUsernameStartingWith(String prefix);

    Optional<List<User>> findByEmailEndingWith(String domain);

    Optional<List<User>> findByRoleAndCreatedAtAfter(Role role, LocalDateTime dateTime);

    Optional<List<User>> findByActive(boolean active);
}