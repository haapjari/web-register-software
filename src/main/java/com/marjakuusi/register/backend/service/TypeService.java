package com.marjakuusi.register.backend.service;

import com.marjakuusi.register.backend.entity.Type;
import com.marjakuusi.register.backend.repository.TypeRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jari Haapasaari
 * @version 30.5.2020
 * Service Class. This Class will handle business logic and database access.
 */
@Service
public class TypeService {

    /* ----------------------------------------------------------------------------------- */

    /* attributes */

    private TypeRepository typeRepository;

    /* ----------------------------------------------------------------------------------- */

    /* constructors */

    public TypeService(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    /* ----------------------------------------------------------------------------------- */

    /* logic */

    /**
     * Finds all "Type" objects in typeRepository.
     * @return Returns list of "Type" objects in typeRepository.
     */
    public List<Type> findAll() {
        return typeRepository.findAll();
    }

    /**
     * Loops through each "Type" and returns a Map containing "Type" name and number of "Customer" objects in tested "Type".
     * @return Number of objects in certain "Type"
     */
    public Map<String, Integer> getStats() {
        HashMap<String, Integer> stats = new HashMap<>();
        findAll().forEach(company -> stats.put(company.getName(), company.getTypes().size()));
        return stats;
    }

}
