package com.marjakuusi.register.backend.service;

import com.marjakuusi.register.backend.entity.Customer;
import com.marjakuusi.register.backend.entity.Product;
import com.marjakuusi.register.backend.repository.CustomerRepository;
import com.marjakuusi.register.backend.repository.ProductRepository;

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
 * Backend Component that handles most of the Customer and Data Object Logic.
 */

@Service
public class CustomerService {

    /* ----------------------------------------------------------------------------------- */

    /* attributes */

    private static final Logger LOGGER = Logger.getLogger(CustomerService.class.getName());
    private CustomerRepository customerRepository;
    private ProductRepository productRepository;

    /* ----------------------------------------------------------------------------------- */

    /* constructors */

    public CustomerService(CustomerRepository customerRepository,
                           ProductRepository productRepository) {
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
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

    public long count() {
        return customerRepository.count();
    }

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
    @PostConstruct
    public void populateTestData() {

        if (productRepository.count() == 0) {
            productRepository.saveAll(
                    Stream.of("Apple", "Lemon", "Pear")
                            .map(Product::new)
                            .collect(Collectors.toList()));
        }

        if (customerRepository.count() == 0) {
            Random r = new Random(0);
            List<Product> products = productRepository.findAll();

            customerRepository.saveAll(
                    Stream.of("Mikki Hiiri", "Aku Ankka", "Hessu Hopo", "Roope Ankka")
                            .map(name -> {
                                String[] split = name.split(" ");
                                Customer customer = new Customer();
                                customer.setFirstName(split[0]);
                                customer.setLastName(split[1]);
                                customer.setProduct(products.get(r.nextInt(products.size())));
                                customer.setStatus(Customer.Status.values()[r.nextInt(Customer.Status.values().length)]);
                                String email = (customer.getFirstName() + "." + customer.getLastName() + "@" + "jyu.fi").toLowerCase();
                                customer.setEmail(email);
                                return customer;
                            }).collect(Collectors.toList()));
        }

    }
}