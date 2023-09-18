package com.github.onechesz.scooter_shop.repositories;

import com.github.onechesz.scooter_shop.entities.RentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RentRepository extends JpaRepository<RentEntity, Integer> {
    @Query(value = "SELECT * FROM \"rent\" WHERE scooter_id = :scooter_id", nativeQuery = true)
    Optional<RentEntity> findByScooterId(@Param(value = "scooter_id") int scooterId);

    @Query(value = "SELECT * FROM \"rent\" WHERE user_id = :user_id", nativeQuery = true)
    List<RentEntity> findByUserId(@Param(value = "user_id") int userId);

    @Query(value = "SELECT * FROM \"rent\" WHERE user_id = :user_id AND scooter_id IN (:rented_scooter_ids)", nativeQuery = true)
    List<RentEntity> findByUserIdAndScooterIds(@Param(value = "user_id") int userId, @Param("rented_scooter_ids") List<Integer> names);

    @Modifying
    @Query(value = "DELETE FROM \"rent\" WHERE user_id = :user_id AND scooter_id = :scooter_id", nativeQuery = true)
    void deleteByUserIdAndScooterId(@Param(value = "user_id") int userId, @Param(value = "scooter_id") int scooterId);

    @Modifying
    @Query(value = "DELETE FROM \"rent\" WHERE user_id = :user_id", nativeQuery = true)
    void deleteAllByUserId(@Param(value = "user_id") int userId);
}
