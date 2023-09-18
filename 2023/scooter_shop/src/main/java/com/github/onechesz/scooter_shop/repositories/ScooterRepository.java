package com.github.onechesz.scooter_shop.repositories;

import com.github.onechesz.scooter_shop.entities.ScooterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScooterRepository extends JpaRepository<ScooterEntity, Integer> {
    @Query(value = "SELECT * FROM \"scooter\" WHERE is_already_under_rent = false", nativeQuery = true)
    List<ScooterEntity> findAllExceptAlreadyUnderRent();

//    @Query(value = "SELECT CAST(CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END) FROM \"scooter\" WHERE title = :title", nativeQuery = true)
//    boolean isThisTitleAlreadyExists(@Param(value = "title") String title);

    @Query(value = "SELECT COUNT(*) > 0 FROM \"scooter\" WHERE title = :title", nativeQuery = true)
    boolean isThisTitleAlreadyExists(@Param(value = "title") String title);
}
