package com.tienda.application.service.impl;

import com.tienda.domain.model.Customer;
import com.tienda.domain.port.CustomerRepository;
import com.tienda.infrastructure.entity.CustomerEntity;
import com.tienda.infrastructure.exceptions.ResourceNotFoundException;
import com.tienda.infrastructure.rest.mapper.CustomerMapper;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DomainCustomerServiceTest {

    @Mock
    CustomerRepository customerRepository;

    @Mock
    CustomerMapper customerMapper;

    @InjectMocks
    DomainCustomerService customerService;

    @Nested
    class GetCustomers {

        @Test
        void getCustomers_SuccessfulCase_ReturnsCustomerIterable() {

            Iterable<CustomerEntity> customerEntities = Arrays.asList(
                    new CustomerEntity(1L, "John", "Doe", "john.doe@example.com", "1234567890", "123 Main St"),
                    new CustomerEntity(2L, "Jane", "Doe", "jane.doe@example.com", "0987654321", "456 Elm St")
            );
            Iterable<Customer> customerDtos = Arrays.asList(
                    new Customer(1L, "John", "Doe", "john.doe@example.com", "1234567890", "123 Main St"),
                    new Customer(2L, "Jane", "Doe", "jane.doe@example.com", "0987654321", "456 Elm St")
            );


            when(customerRepository.findAll()).thenReturn(customerEntities);
            when(customerMapper.toDTO(customerEntities)).thenReturn(customerDtos);


            Iterable<Customer> customers = customerService.getCustomers();

            assertNotNull(customers);
            List<Customer> customerList = (List<Customer>) customers;
            assertEquals(2, customerList.size());
            assertEquals("John", customerList.get(0).getName());
            assertEquals("Jane", customerList.get(1).getName());
        }
    }

    @Nested
    class GetCustomerById {

        @Test
        void getCustomerById_SuccessfulCase_ReturnsCustomer() {

            Long validId = 1L;
            CustomerEntity customerEntity = new CustomerEntity();
            customerEntity.setId(validId);
            customerEntity.setName("John");
            customerEntity.setSurname("Doe");
            customerEntity.setEmail("john.doe@example.com");
            customerEntity.setPhoneNumber("1234567890");
            customerEntity.setAddress("123 Main St");


            Customer customerDto = new Customer();
            customerDto.setId(validId);
            customerDto.setName("John");
            customerDto.setSurname("Doe");
            customerDto.setEmail("john.doe@example.com");
            customerDto.setPhoneNumber("1234567890");
            customerDto.setAddress("123 Main St");



            when(customerRepository.findById(validId)).thenReturn(Optional.of(customerEntity));
            when(customerMapper.toDTO(customerEntity)).thenReturn(customerDto);


            Customer result = customerService.getCustomerById(validId);

            assertNotNull(result);
            assertEquals(validId, result.getId());
            assertEquals("John", result.getName());
            assertEquals("Doe", result.getSurname());
            assertEquals("john.doe@example.com", result.getEmail());
            assertEquals("1234567890", result.getPhoneNumber());
            assertEquals("123 Main St", result.getAddress());
        }

        @Test
        void getCustomerById_IdNotFound_ThenReturnResourceNotFoundException() {
            Long nonExistentId = 100L;

            when(customerRepository.findById(nonExistentId)).thenThrow(ResourceNotFoundException.class);

            assertThrows(ResourceNotFoundException.class, () -> customerService.getCustomerById(nonExistentId));
        }
    }

    @Nested
    class SaveCustomer {

        @Test
        void saveCustomer_SuccessfulInsertCase_ReturnsCustomer() {

            Customer customer = new Customer(null, "John", "Doe", "john.doe@example.com", "1234567890", "123 Main St");
            Customer savedCustomer = new Customer(1L, "John", "Doe", "john.doe@example.com", "1234567890", "123 Main St");
            CustomerEntity customerEntity = new CustomerEntity(null, "John", "Doe", "john.doe@example.com", "1234567890", "123 Main St");
            CustomerEntity savedCustomerEntity = new CustomerEntity(1L, "John", "Doe", "john.doe@example.com", "1234567890", "123 Main St");

            when(customerRepository.save(customerEntity)).thenReturn(savedCustomerEntity);
            when(customerMapper.toEntity(customer)).thenReturn(customerEntity);
            when(customerMapper.toDTO(savedCustomerEntity)).thenReturn(savedCustomer);

            Customer returnedCustomer = customerService.saveCustomer(customer);

            assertNotNull(returnedCustomer.getId());
            assertEquals("John", returnedCustomer.getName());
            verify(customerRepository, times(1)).save(customerEntity);
        }

        @Test
        void saveCustomer_SuccessfulUpdateCase_ReturnsCustomer() {

            Customer customer = new Customer(1L, "John", "Doe", "john.doe@example.com", "1234567890", "123 Main St");
            Customer modedCustomer = new Customer(1L, "Jane", "Doe", "john.doe@example.com", "1234567890", "123 Main St");
            CustomerEntity customerEntity = new CustomerEntity(1L, "John", "Doe", "john.doe@example.com", "1234567890", "123 Main St");
            CustomerEntity modedCustomerEntity = new CustomerEntity(1L, "Jane", "Doe", "john.doe@example.com", "1234567890", "123 Main St");

            when(customerRepository.findById(1L)).thenReturn(Optional.of(customerEntity));
            when(customerRepository.save(customerEntity)).thenReturn(modedCustomerEntity);
            when(customerMapper.toEntity(customer)).thenReturn(customerEntity);
            when(customerMapper.toDTO(modedCustomerEntity)).thenReturn(modedCustomer);
            when(customerMapper.toDTO(customerEntity)).thenReturn(customer);

            Customer returnedCustomer = customerService.saveCustomer(customer);

            assertNotNull(returnedCustomer.getId());
            assertEquals("Jane", returnedCustomer.getName());
            verify(customerRepository, times(1)).save(customerEntity);
        }

    }

    @Nested
    class DeleteCustomer {

        @Test
        void deleteCustomer_SuccessfulCase_ReturnsVoid() {

            Long id = 1L;
            CustomerEntity customerEntity = new CustomerEntity(1L, "John", "Doe", "john.doe@example.com", "1234567890", "123 Main St");

            when(customerRepository.findById(id)).thenReturn(Optional.of(customerEntity));
            doNothing().when(customerRepository).deleteById(id);

            customerService.deleteCustomer(id);

            verify(customerRepository, times(1)).deleteById(id);
        }

        @Test
        void deleteCustomer_IdNotFound_ReturnsResourceNotFoundException() {

            Long nonExistentId = 100L;

            when(customerRepository.findById(nonExistentId)).thenReturn(Optional.empty());

            assertThrows(ResourceNotFoundException.class, () -> customerService.getCustomerById(nonExistentId));
        }
    }
}