package com.marjakuusi.register.backend.repository;

import com.marjakuusi.register.backend.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author jthaapas
 * @version 7.5.2020
 *
 * CustomerRepository Interface
 */

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /*
     * Query annotation to define a custom query.
     *
     * Checks if the string matches the first or the last name, and ignores case.
     *
     * Query uses JPQL (Java Persistence Query Language (JPQL) for querying JPA managed database.
     */
    @Query("select c from Customer c " +
            "where lower(c.firstName) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(c.lastName) like lower(concat('%', :searchTerm, '%'))") //
    List<Customer> search(@Param("searchTerm") String searchTerm); //

    // Spring Framework Import for @param

}
