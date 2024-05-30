package com.tienda.application.service.impl;

import com.tienda.domain.model.Customer;
import com.tienda.domain.model.Order;
import com.tienda.domain.port.OrderRepository;
import com.tienda.infrastructure.entity.CustomerEntity;
import com.tienda.infrastructure.entity.OrderEntity;
import com.tienda.infrastructure.exceptions.ResourceNotFoundException;
import com.tienda.infrastructure.rest.mapper.OrderMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
 
@ExtendWith(MockitoExtension.class)
class DomainOrderServiceTest {

    @Mock
    OrderRepository orderRepository;

    @Mock
    OrderMapper orderMapper;

    @InjectMocks
    DomainOrderService orderService;

    @Nested
    class GetOrders {

        @Test
        void getOrders_SuccessfulCase_ReturnsOrderIterable() {

            Iterable<OrderEntity> orderEntities = Arrays.asList(
                    new OrderEntity(1L, LocalDateTime.now(), "Pending", 100.0, new CustomerEntity()),
                    new OrderEntity(2L, LocalDateTime.now(), "Shipped", 200.0, new CustomerEntity())
            );
            Iterable<Order> orderDtos = Arrays.asList(
                    new Order(1L, LocalDateTime.now(), "Pending", 100.0, new Customer()),
                    new Order(2L, LocalDateTime.now(), "Shipped", 200.0, new Customer())
            );


            when(orderRepository.findAll()).thenReturn(orderEntities);
            when(orderMapper.toDTO(orderEntities)).thenReturn(orderDtos);


            Iterable<Order> orders = orderService.getOrders();

            assertNotNull(orders);
            List<Order> orderList = (List<Order>) orders;
            assertEquals(2, orderList.size());
            assertEquals("Pending", orderList.get(0).getStatus());
            assertEquals("Shipped", orderList.get(1).getStatus());
        }
    }

    @Nested
    class GetOrderById {

        @Test
        void getOrderById_SuccessfulCase_ReturnsOrder() {

            Long validId = 1L;
            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setId(validId);
            orderEntity.setDate(LocalDateTime.now());
            orderEntity.setStatus("Pending");
            orderEntity.setTotal(100.0);
            orderEntity.setCustomer(new CustomerEntity());

            Order orderDto = new Order();
            orderDto.setId(validId);
            orderDto.setDate(LocalDateTime.now());
            orderDto.setStatus("Pending");
            orderDto.setTotal(100.0);
            orderDto.setCustomer(new Customer());


            when(orderRepository.findById(validId)).thenReturn(Optional.of(orderEntity));
            when(orderMapper.toDTO(orderEntity)).thenReturn(orderDto);


            Order result = orderService.getOrderById(validId);

            assertNotNull(result);
            assertEquals(orderDto.getId(), result.getId());
            assertEquals(orderDto.getDate(), result.getDate());
            assertEquals(orderDto.getStatus(), result.getStatus());
            assertEquals(orderDto.getTotal(), result.getTotal());
            assertEquals(orderDto.getCustomer(), result.getCustomer());

        }

        @Test
        void getOrderById_IdNotFound_ThenReturnResourceNotFoundException() {
            Long nonExistentId = 100L;

            when(orderRepository.findById(nonExistentId)).thenThrow(ResourceNotFoundException.class);

            assertThrows(ResourceNotFoundException.class, () -> orderService.getOrderById(nonExistentId));
        }
    }

    @Nested
    class SaveOrder {

        @Test
        void saveOrder_SuccessfulInsertCase_ReturnsOrder() {

            Order order = new Order(null, LocalDateTime.now(), "Pending", 100.0, new Customer());
            Order savedOrder = new Order(1L, LocalDateTime.now(), "Pending", 100.0, new Customer());
            OrderEntity orderEntity = new OrderEntity(null, LocalDateTime.now(), "Pending", 100.0, new CustomerEntity());
            OrderEntity savedOrderEntity = new OrderEntity(1L, LocalDateTime.now(), "Pending", 100.0, new CustomerEntity());

            when(orderRepository.save(orderEntity)).thenReturn(savedOrderEntity);
            when(orderMapper.toEntity(order)).thenReturn(orderEntity);
            when(orderMapper.toDTO(savedOrderEntity)).thenReturn(savedOrder);

            Order returnedOrder = orderService.saveOrder(order);

            assertNotNull(returnedOrder.getId());
            assertEquals("Pending", returnedOrder.getStatus());
            verify(orderRepository, times(1)).save(orderEntity);
        }

        @Test
        void saveOrder_SuccessfulUpdateCase_ReturnsOrder() {

            Order order = new Order(1L, LocalDateTime.now(), "Pending", 100.0, new Customer());
            Order modedOrder = new Order(1L, LocalDateTime.now(), "Shipped", 100.0, new Customer());
            OrderEntity orderEntity = new OrderEntity(1L, LocalDateTime.now(), "Pending", 100.0, new CustomerEntity());
            OrderEntity modedOrderEntity = new OrderEntity(1L, LocalDateTime.now(), "Shipped", 100.0, new CustomerEntity());

            when(orderRepository.findById(1L)).thenReturn(Optional.of(orderEntity));
            when(orderRepository.save(orderEntity)).thenReturn(modedOrderEntity);
            when(orderMapper.toEntity(order)).thenReturn(orderEntity);
            when(orderMapper.toDTO(modedOrderEntity)).thenReturn(modedOrder);
            when(orderMapper.toDTO(orderEntity)).thenReturn(order);

            Order returnedOrder = orderService.saveOrder(order);

            assertNotNull(returnedOrder.getId());
            assertEquals(1L, returnedOrder.getId());
            assertEquals("Shipped", returnedOrder.getStatus());
            verify(orderRepository, times(1)).save(orderEntity);
        }

    }

    @Nested
    class DeleteOrder {

        @Test
        void deleteOrder_SuccessfulCase_ReturnsVoid() {

            Long id = 1L;
            OrderEntity orderEntity = new OrderEntity(1L, LocalDateTime.now(), "Pending", 100.0, new CustomerEntity());

            when(orderRepository.findById(id)).thenReturn(Optional.of(orderEntity));
            doNothing().when(orderRepository).deleteById(id);

            orderService.deleteOrder(id);

            verify(orderRepository, times(1)).deleteById(id);
        }

        @Test
        void deleteOrder_IdNotFound_ReturnsResourceNotFoundException() {

            Long nonExistentId = 100L;

            when(orderRepository.findById(nonExistentId)).thenReturn(Optional.empty());


            assertThrows(ResourceNotFoundException.class, () -> orderService.deleteOrder(nonExistentId));
        }
    }
}