package com.tienda.infrastructure.adaptador.impl;

import com.tienda.infrastructure.adaptador.CustomerCrudRepositoryMySQL;
import com.tienda.infrastructure.entity.CustomerEntity;
import com.tienda.infrastructure.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerRepositoryMySQLTest {

    @Mock
    CustomerCrudRepositoryMySQL customerCrudRepo;

    @InjectMocks
    CustomerRepositoryMySQL customerRepo;

    @Nested
    class FindAll {

        @Test
        void findAll_SuccessfulCase_ReturnsIterableCustomerEntity() {

            Iterable<CustomerEntity> customers = Arrays.asList(new CustomerEntity(1L, "John", "Doe", "john.doe@example.com", "1234567890", "123 Main St"), new CustomerEntity(2L, "Jane", "Doe", "jane.doe@example.com", "0987654321", "456 Elm St"));

            when(customerCrudRepo.findAll()).thenReturn(customers);

            Iterable<CustomerEntity> result = customerRepo.findAll();

            assertNotNull(result);
            assertEquals(customers, result);
        }
    }


    @Nested
    class FindById {

        @Test
        void findById_SuccessfulCase_ReturnsIterableCustomerEntity() {

            Long validId = 1L;
            CustomerEntity expectedCustomer = new CustomerEntity(validId, "John", "Doe", "john.doe@example.com", "1234567890", "123 Main St");

            when(customerCrudRepo.findById(validId)).thenReturn(Optional.of(expectedCustomer));

            Optional<CustomerEntity> optionalCustomer = customerRepo.findById(validId);

            assertFalse(optionalCustomer.isEmpty());
            CustomerEntity actualCustomer = optionalCustomer.get();
            assertEquals(expectedCustomer.getId(), actualCustomer.getId());
            assertEquals(expectedCustomer.getName(), actualCustomer.getName());
            assertEquals(expectedCustomer.getSurname(), actualCustomer.getSurname());
            assertEquals(expectedCustomer.getEmail(), actualCustomer.getEmail());
            assertEquals(expectedCustomer.getPhoneNumber(), actualCustomer.getPhoneNumber());
            assertEquals(expectedCustomer.getAddress(), actualCustomer.getAddress());
        }
    }


    @Nested
    class Save {

        @Test
        void save_SuccessfulCase_ReturnsIterableCustomerEntity() {

            CustomerEntity customer = new CustomerEntity(null, "John", "Doe", "john.doe@example.com", "1234567890", "123 Main St");
            CustomerEntity savedCustomer = new CustomerEntity(1L, "John", "Doe", "john.doe@example.com", "1234567890", "123 Main St");

            when(customerCrudRepo.save(customer)).thenReturn(savedCustomer);

            CustomerEntity result = customerRepo.save(customer);

            assertNotNull(result);
            assertEquals(savedCustomer.getId(), result.getId());
            assertEquals(savedCustomer.getName(), result.getName());
            assertEquals(savedCustomer.getSurname(), result.getSurname());
            assertEquals(savedCustomer.getEmail(), result.getEmail());
            assertEquals(savedCustomer.getPhoneNumber(), result.getPhoneNumber());
            assertEquals(savedCustomer.getAddress(), result.getAddress());

            verify(customerCrudRepo, times(1)).save(customer);
        }
    }


    @Nested
    class DeleteById {

        @Test
        void deleteById_SuccessfulCase_ReturnsVoid() {

            Long validId = 1L;

            customerRepo.deleteById(validId);

            verify(customerCrudRepo, times(1)).deleteById(validId);
        }
    }
}

