package com.practice.register.backend.repository;

import com.practice.register.backend.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author haapjari
 * @version 30.5.2020
 * Repository Class. Interface that describes entity type and primary key type. Spring Data will configure the rest.
 */
public interface TypeRepository extends JpaRepository<Type, Long> {
}
