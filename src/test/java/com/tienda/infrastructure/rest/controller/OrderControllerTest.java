package com.tienda.infrastructure.rest.controller;

import com.tienda.domain.model.Customer;
import com.tienda.domain.model.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = RANDOM_PORT)
class OrderControllerTest {

    @Autowired
    private WebTestClient client;

    @Test
    void getOrders_SuccessfulCase_ReturnsAllOrders() {

        client.get().uri("api/order")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Iterable.class);
    }

    @Nested
    class SaveOrder {
        @Test
        void saveOrder_SuccessfulCase_ReturnsOrder() {

            Customer customer = new Customer(1L, "gabriele", "nevini", "fake.mail@gmail.com", "23135465", "calle");
            Order orderIn = new Order(null, LocalDateTime.now(), "Pending", 12D, customer);

            client.post().uri("api/order")
                    .bodyValue(orderIn)
                    .exchange()
                    .expectStatus().isCreated()
                    .expectBody(Order.class)
                    .value(response -> {
                        assertNotNull(response);
                        assertNotNull(response.getId());
                    });
        }

        @Test
        void saveOrder_OrderHasNoCustomer_ReturnsBadRequest() {

            Order orderIn = new Order(null, LocalDateTime.now(), "Pending", 12D, null);

            client.post().uri("api/order")
                    .bodyValue(orderIn)
                    .exchange()
                    .expectStatus().isBadRequest()
                    .expectBody(Error.class)
                    .value(response -> {
                        assertNotNull(response);
                        assertNotNull(response.getMessage());
                    });
        }
    }

    @Nested
    class GetOrder {

        @Test
        void getOrder_SuccessfulCase_ReturnsOrder() {

            client.get().uri("api/order/1")
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody(Order.class)
                    .value(response -> {
                        assertNotNull(response);
                        assertEquals("pending", response.getStatus());
                    });
        }

        @Test
        void getOrder_IdNotFound_ReturnsNotFound() {

            client.get().uri("api/order/100")
                    .exchange()
                    .expectStatus().isNotFound()
                    .expectBody(Error.class)
                    .value(Assertions::assertNotNull);
        }
    }


    @Nested
    class DeleteOrder {

        @Test
        void deleteOrder_SuccessfulCase_ReturnsOk() {

            client.delete().uri("api/order/2")
                    .exchange()
                    .expectStatus().isOk();
        }

        @Test
        void deleteOrder_IdNotFound_ReturnsNotFound() {

            client.delete().uri("api/order/100")
                    .exchange()
                    .expectStatus().isNotFound()
                    .expectBody(Error.class)
                    .value(Assertions::assertNotNull);
        }
    }

}