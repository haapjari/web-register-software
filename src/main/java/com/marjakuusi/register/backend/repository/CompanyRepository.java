package com.marjakuusi.register.backend.repository;

import com.marjakuusi.register.backend.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
