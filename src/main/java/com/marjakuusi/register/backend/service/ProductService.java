package com.marjakuusi.register.backend.service;

import com.marjakuusi.register.backend.entity.Product;
import com.marjakuusi.register.backend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    /**
     * Loops through each company and returns a Map containing the company name and number of employees.
     * @return stats
     */
    public Map<String, Integer> getStats() {
        HashMap<String, Integer> stats = new HashMap<>();
        findAll().forEach(company -> stats.put(company.getName(), company.getProductOwners().size()));
        return stats;
    }

}
