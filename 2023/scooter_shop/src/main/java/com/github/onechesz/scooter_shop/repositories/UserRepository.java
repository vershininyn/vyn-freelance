package com.github.onechesz.scooter_shop.repositories;

import com.github.onechesz.scooter_shop.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByName(String name);

    @Query(value = "UPDATE \"user\" SET role = :role WHERE id = :id", nativeQuery = true)
    @Modifying
    void editRole(@Param(value = "id") int id, @Param(value = "role") String role);

    @Query(value = "UPDATE \"user\" SET balance = 0 WHERE id = :id", nativeQuery = true)
    @Modifying
    void setZeroBalance(@Param(value = "id") int id);
}
