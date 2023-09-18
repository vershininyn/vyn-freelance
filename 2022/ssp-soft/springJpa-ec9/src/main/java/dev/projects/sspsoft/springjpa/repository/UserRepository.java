package dev.projects.sspsoft.springjpa.repository;

import dev.projects.sspsoft.springjpa.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    @Query(value = "SELECT * FROM public.user", nativeQuery = true)
    Collection<UserEntity> findAllNativeQuery();
}