package com.marjakuusi.register.backend.service;

import com.marjakuusi.register.backend.entity.Company;
import com.marjakuusi.register.backend.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CompanyService {

    private CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    /**
     * Loops through each company and returns a Map containing the company name and number of employees.
     * @return
     */
    public Map<String, Integer> getStats() {
        HashMap<String, Integer> stats = new HashMap<>();
        findAll().forEach(company -> stats.put(company.getName(), company.getEmployees().size()));
        return stats;
    }

}
