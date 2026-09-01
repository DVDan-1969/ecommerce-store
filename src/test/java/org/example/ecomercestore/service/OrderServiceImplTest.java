package org.example.ecomercestore.service;

import org.assertj.core.api.AssertionsForClassTypes;
import org.example.ecomercestore.dto.OrderRequestDTO;
import org.example.ecomercestore.dto.OrderResponseDTO;
import org.example.ecomercestore.exception.OrderNotFoundException;
import org.example.ecomercestore.exception.UserNotFoundException;
import org.example.ecomercestore.mapper.OrderMapper;
import org.example.ecomercestore.model.User;
import org.example.ecomercestore.repository.OrderRepository;
import org.example.ecomercestore.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.example.ecomercestore.model.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.StatusResultMatchersExtensionsKt.isEqualTo;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private User user;
    private Order order;
    private OrderRequestDTO orderRequestDTO;
    private OrderResponseDTO orderResponseDTO;
    private Date date;

    @BeforeEach
    public void setUp() {
      date =new Date();

      user = new User();
      user.setId(1L);

      order=new Order();
      order.setId(1L);
      order.setData(date);
      order.setTotal(new BigDecimal("2500.00"));
      order.setUser(user);

      orderRequestDTO = new OrderRequestDTO();
      orderRequestDTO.setDate(date);
      orderRequestDTO.setUserId(1L);

      orderResponseDTO =new OrderResponseDTO();
      orderResponseDTO.setId(1L);
      orderResponseDTO.setDate(date);
      orderResponseDTO.setTotal(new BigDecimal("2500.00"));
      orderResponseDTO.setUserId(1L);

    }
    @Test
    void shouldReturnOrderWhenOrderFound(){
        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));
        when(orderMapper.toResponseDTO(order))
                .thenReturn(orderResponseDTO);

        OrderResponseDTO result=orderService.getOrderById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getDate()).isEqualTo(date);
    }
    @Test
    void shouldThrowExceptionWhenOrderNotFound(){
        Long id=999L;
        when(orderRepository.findById(id))
                .thenReturn(Optional.empty());
        OrderNotFoundException exception = assertThrows(
                OrderNotFoundException.class,
                () -> orderService.getOrderById(id)
        );
        assertEquals("Order not found", exception.getMessage());
    }
    @Test
    void shouldReturnListOfOrders(){
        Order order2=new Order();
        order2.setId(2L);
        order2.setData(date);
        order2.setTotal(new BigDecimal("3500.00"));
        order2.setUser(user);

        OrderResponseDTO response2=new OrderResponseDTO();
        response2.setId(2L);
        response2.setDate(date);
        response2.setTotal(new BigDecimal("3500.00"));
        response2.setUserId(1L);

        when(orderRepository.findAll())
                .thenReturn(List.of(order,order2));
        when(orderMapper.toResponseDTO(order))
                .thenReturn(orderResponseDTO);
        when(orderMapper.toResponseDTO(order2))
                .thenReturn(response2);

        List<OrderResponseDTO> result=orderService.getAllOrders();
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getTotal()).isEqualTo(new BigDecimal("2500.00"));
        assertThat(result.get(1).getTotal()).isEqualTo(new BigDecimal("3500.00"));
    }
    @Test
    void shouldSaveAndReturnOrder(){
        when(userRepository.findById(1L))
            .thenReturn(Optional.of(user));
        when(orderMapper.toEntity(orderRequestDTO,user))
                .thenReturn(order);
        when(orderRepository.save(order))
                .thenReturn(order);
        when(orderMapper.toResponseDTO(order))
                .thenReturn(orderResponseDTO);

        OrderResponseDTO result=orderService.save(orderRequestDTO);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(orderResponseDTO.getId());
        assertThat(result.getDate()).isEqualTo(orderResponseDTO.getDate());
    }
    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        Long id = 999L;
        orderRequestDTO.setUserId(id);

        when(userRepository.findById(id))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> orderService.save(orderRequestDTO)
        );

        assertEquals("User not found", exception.getMessage());
    }
    @Test
    void shouldUpdateAndReturnOrder(){
        User user2=new User();
        user2.setId(2L);

        orderRequestDTO.setUserId(2L);

        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));

        when(userRepository.findById(2L))
                .thenReturn(Optional.of(user2));

        when(orderRepository.save(order))
                .thenReturn(order);

        when(orderMapper.toResponseDTO(order))
                .thenReturn(orderResponseDTO);

        OrderResponseDTO result =
                orderService.update(1L, orderRequestDTO);


        assertThat(result).isNotNull();
        assertThat(order.getData()).isEqualTo(orderRequestDTO.getDate());
        assertThat(order.getUser()).isEqualTo(user2);
    }
    @Test
    void shouldThrowExceptionWhenUpdatingOrderNotFound(){
        Long id=999L;
        when(orderRepository.findById(id))
                .thenReturn(Optional.empty());
        OrderNotFoundException exception = assertThrows(
                OrderNotFoundException.class,
                () -> orderService.update(id,orderRequestDTO)
        );
        assertEquals("Order not found", exception.getMessage());

    }
    @Test
    void shouldThrowExceptionWhenUpdatingUserNotFound(){
        Long userId=999L;
        Long orderId=1L;

        orderRequestDTO.setUserId(userId);

        when(orderRepository.findById(orderId))
                .thenReturn(Optional.of(order));
        when(userRepository.findById(userId))
                .thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> orderService.update(orderId,orderRequestDTO)
        );
        assertEquals("User not found", exception.getMessage());
    }


    @Test
    void shouldDeleteUser(){
        Long id=1L;

        when(orderRepository.findById(id))
                .thenReturn(Optional.of(order));
        orderService.deleteById(id);

        verify(orderRepository).delete(order);
    }
    @Test
    void shouldThrowExceptionWhenDeletingOrderNotFound(){
        Long id=999L;
        when(orderRepository.findById(id))
                .thenReturn(Optional.empty());

        OrderNotFoundException exception=assertThrows(
                OrderNotFoundException.class,
                () -> orderService.deleteById(id)

        );
        assertEquals("Order not found", exception.getMessage());
    }



}
