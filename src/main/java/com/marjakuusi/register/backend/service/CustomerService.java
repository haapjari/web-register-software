package com.marjakuusi.register.backend.service;

import com.marjakuusi.register.backend.entity.Customer;
import com.marjakuusi.register.backend.entity.Type;
import com.marjakuusi.register.backend.repository.CustomerRepository;
import com.marjakuusi.register.backend.repository.TypeRepository;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Jari Haapasaari
 * @version 30.5.2020
 * Service Class. This Class will handle business logic and database access.
 */

/* Annotation lets Spring Framework now that this is a service class, and configures it available for dependency injection */

@Service
public class CustomerService {

    /* ----------------------------------------------------------------------------------- */

    /* attributes */

    private static final Logger LOGGER = Logger.getLogger(CustomerService.class.getName());
    private CustomerRepository customerRepository;
    private TypeRepository typeRepository;

    /* ----------------------------------------------------------------------------------- */

    /* constructors */

    public CustomerService(CustomerRepository customerRepository,
                           TypeRepository typeRepository) {
        this.customerRepository = customerRepository;
        this.typeRepository = typeRepository;
    }

    /* ----------------------------------------------------------------------------------- */

    /* logic */

    /**
     * List for Customers that matches a certain condition (for example filter)
     */
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    /**
     * Search method for customers.
     * @param stringFilter search parameter.
     * @return matched data
     */
    public List<Customer> findAll(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return customerRepository.findAll();
        } else {
            return customerRepository.search(stringFilter);
        }
    }

    /**
     * @return count objects in customerRepository object
     */
    public long count() {
        return customerRepository.count();
    }

    /**
     * Delete customer object from customerRepository.
     * @param customer object to be deleted
     */
    public void delete(Customer customer) {
        customerRepository.delete(customer);
    }

    /**
     * Save method for customer.
     * @param customer Object where data is saved.
     */
    public void save(Customer customer) {
        if (customer == null) {
            /* If there is a problem saving data, will log error message. */
            LOGGER.log(Level.SEVERE,
                    "Customer is null. Are you sure you have connected your form to the application?");
            return;
        }
        customerRepository.save(customer);
    }

    /**
     * Method to populate test data to the software.
     *
     * TODO
     * Change this to where data is read from file or database.
     */

    /* Annotation tells Spring to run this method after constuctors */

    @PostConstruct
    public void populateTestData() {

        if (typeRepository.count() == 0) {
            typeRepository.saveAll(
                    Stream.of("Consumer", "Business", "Enterprise")
                            .map(Type::new)
                            .collect(Collectors.toList()));
        }

        if (customerRepository.count() == 0) {
            Random r = new Random(0);
            List<Type> groupEntities = typeRepository.findAll();

            customerRepository.saveAll(
                    Stream.of("Mikki Hiiri", "Aku Ankka", "Hessu Hopo", "Roope Ankka", "Hessu Hopo","Hessu Hopo",
                              "Mikki Hiiri", "Aku Ankka", "Hessu Hopo", "Roope Ankka")
                            .map(name -> {
                                String[] split = name.split(" ");
                                Customer customer = new Customer();
                                customer.setFirstName(split[0]);
                                customer.setLastName(split[1]);
                                customer.setType(groupEntities.get(r.nextInt(groupEntities.size())));
                                customer.setStatus(Customer.Status.values()[r.nextInt(Customer.Status.values().length)]);
                                String email = (customer.getFirstName() + "." + customer.getLastName() + "@" + "jyu.fi").toLowerCase();
                                customer.setEmail(email);
                                return customer;
                            }).collect(Collectors.toList()));
        }

    }
}