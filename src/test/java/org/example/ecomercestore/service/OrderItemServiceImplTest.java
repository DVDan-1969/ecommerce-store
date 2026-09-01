package org.example.ecomercestore.service;

import org.example.ecomercestore.dto.OrderItemRequestDTO;
import org.example.ecomercestore.dto.OrderItemResponseDTO;
import org.example.ecomercestore.mapper.OrderItemMapper;
import org.example.ecomercestore.model.Order;
import org.example.ecomercestore.model.OrderItem;
import org.example.ecomercestore.model.Product;
import org.example.ecomercestore.repository.OrderItemRepository;
import org.example.ecomercestore.repository.OrderRepository;
import org.example.ecomercestore.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class OrderItemServiceImplTest {
    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private OrderItemMapper orderItemMapper;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderItemServiceImpl orderItemService;

    private OrderItemResponseDTO orderItemResponseDTO;

    private OrderItem orderItem;

    @BeforeEach
    public void setUp() {
        orderItem=new OrderItem();
        orderItem.setId(1L);
        orderItem.setQuantity(5);
        orderItem.setPrice(new BigDecimal("150.00"));

        orderItemResponseDTO = new OrderItemResponseDTO();
        orderItemResponseDTO.setId(1L);
        orderItemResponseDTO.setQuantity(5);
        orderItemResponseDTO.setPrice(new BigDecimal("150.00"));
    }
    @Test
    void shouldReturnOrderItemWhenOrderItemFound() {
        when(orderItemRepository.findById(1L))
                .thenReturn(Optional.of(orderItem));
        when(orderItemMapper.toResponseDTO(orderItem))
                .thenReturn(orderItemResponseDTO);

        OrderItemResponseDTO result=orderItemService.getOrderItemById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getPrice()).isEqualTo(new BigDecimal("150.00"));
    }
    @Test
    void shouldThrowExceptionWhenOrderItemNotFound() {
        Long id=999L;
        when(orderItemRepository.findById(id))
                .thenReturn(Optional.empty());
        RuntimeException exception=assertThrows(
                RuntimeException.class,
                () -> orderItemService.getOrderItemById(id)
        );
        assertThat(exception.getMessage()).isEqualTo("Order item not found");
    }
    @Test
    void shouldReturnListOfOrderItems() {
       OrderItem orderItem2=new OrderItem();
       orderItem2.setId(2L);
       orderItem2.setQuantity(10);
       orderItem2.setPrice(new BigDecimal("250.00"));

       OrderItemResponseDTO response2=new OrderItemResponseDTO();
       response2.setId(2L);
       response2.setQuantity(10);
       response2.setPrice(new BigDecimal("250.00"));

       when(orderItemRepository.findAll())
               .thenReturn(List.of(orderItem,orderItem2));
       when(orderItemMapper.toResponseDTO(orderItem))
               .thenReturn(orderItemResponseDTO);
       when(orderItemMapper.toResponseDTO(orderItem2))
               .thenReturn(response2);

       List<OrderItemResponseDTO> result=orderItemService.getAllOrderItems();

       assertThat(result).isNotNull();
       assertThat(result.size()).isEqualTo(2);
       assertThat(result.get(0).getPrice()).isEqualTo(new BigDecimal("150.00"));
       assertThat(result.get(1).getPrice()).isEqualTo(new BigDecimal("250.00"));

    }
    @Test
    void shouldSaveAndReturnOrderItem(){

        Product product=new Product();
        product.setId(1L);
        Order order=new Order();
        order.setId(1L);

        OrderItemRequestDTO orderItemRequestDTO=new OrderItemRequestDTO();
        orderItemRequestDTO.setOrderId(1L);
        orderItemRequestDTO.setProductId(1L);
        orderItemRequestDTO.setQuantity(5);
        order.addOrderItem(orderItem);

        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));
        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));
        when(orderItemMapper.toEntity(orderItemRequestDTO,order,product))
                .thenReturn(orderItem);
        when(orderItemRepository.save(orderItem))
                .thenReturn(orderItem);
        when(orderItemMapper.toResponseDTO(orderItem))
                .thenReturn(orderItemResponseDTO);
        OrderItemResponseDTO result =
                orderItemService.save(orderItemRequestDTO);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getQuantity()).isEqualTo(5);
        assertThat(result.getPrice()).isEqualTo(new BigDecimal("150.00"));
        assertThat(order.getTotal())
                .isEqualTo(new BigDecimal("750.00"));
        verify(orderRepository).save(order);

    }
    @Test
    void shouldThrowExceptionWhenSavingOrderNotFound() {

        OrderItemRequestDTO dto = new OrderItemRequestDTO();
        dto.setOrderId(999L);
        dto.setProductId(1L);
        dto.setQuantity(5);


        when(orderRepository.findById(999L))
                .thenReturn(Optional.empty());
        RuntimeException exception=assertThrows(
                RuntimeException.class,
                () -> orderItemService.save(dto)
        );
        assertThat(exception.getMessage()).isEqualTo("Order not found");
    }
    @Test
    void shouldThrowExceptionWhenSavingProductNotFound(){
        Order order = new Order();
        order.setId(1L);

        OrderItemRequestDTO dto = new OrderItemRequestDTO();
        dto.setOrderId(1L);
        dto.setProductId(999L);
        dto.setQuantity(5);

        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));

        when(productRepository.findById(999L))
                .thenReturn(Optional.empty());

        RuntimeException exception=assertThrows(
                RuntimeException.class,
                ()->orderItemService.save(dto)
        );
        assertThat(exception.getMessage()).isEqualTo("Product not found");
    }
    @Test
    void shouldUpdateAndReturnOrderItem(){
        Product product2=new Product();
        product2.setId(2L);
        product2.setPrice(new BigDecimal("200.00"));

        Order order=new Order();
        order.setId(1L);

        order.addOrderItem(orderItem);

        OrderItemRequestDTO orderItemRequestDTO=new OrderItemRequestDTO();
        orderItemRequestDTO.setOrderId(1L);
        orderItemRequestDTO.setProductId(2L);
        orderItemRequestDTO.setQuantity(10);

        OrderItemResponseDTO updateResponse=new OrderItemResponseDTO();
        updateResponse.setId(1L);
        updateResponse.setQuantity(10);
        updateResponse.setPrice(new BigDecimal("200.00"));

        when(orderItemRepository.findById(1L))
                .thenReturn(Optional.of(orderItem));

        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));

        when(productRepository.findById(2L))
                .thenReturn(Optional.of(product2));

        when(orderItemRepository.save(orderItem))
                .thenReturn(orderItem);

        when(orderItemMapper.toResponseDTO(orderItem))
                .thenReturn(updateResponse);

        OrderItemResponseDTO result=
                orderItemService.update(1L,orderItemRequestDTO);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getQuantity()).isEqualTo(10);
        assertThat(result.getPrice()).isEqualTo(new BigDecimal("200.00"));

        assertThat(orderItem.getQuantity()).isEqualTo(10);
        assertThat(orderItem.getPrice()).isEqualTo(new BigDecimal("200.00"));
        assertThat(orderItem.getProduct()).isEqualTo(product2);

        assertThat(order.getTotal()).isEqualTo(new BigDecimal("2000.00"));

        verify(orderItemRepository).save(orderItem);
        verify(orderRepository).save(order);

    }
    @Test
    void shouldThrowExceptionWhenUpdatingOrderItemNotFound(){

        OrderItemRequestDTO dto = new OrderItemRequestDTO();
        Long id=999L;

        when(orderItemRepository.findById(id))
                .thenReturn(Optional.empty());

        RuntimeException exception=assertThrows(
                RuntimeException.class,
                () -> orderItemService.update(id,dto)
        );
        assertThat(exception.getMessage())
                .isEqualTo("Order item not found");
    }
    @Test
    void shouldThrowExceptionWhenUpdatingOrderNotFound(){
        OrderItemRequestDTO dto = new OrderItemRequestDTO();
        dto.setOrderId(999L);
        dto.setProductId(1L);
        dto.setQuantity(5);

        when(orderItemRepository.findById(1L))
                .thenReturn(Optional.of(orderItem));

        when(orderRepository.findById(999L))
                .thenReturn(Optional.empty());
        RuntimeException exception=assertThrows(
                RuntimeException.class,
                () -> orderItemService.update(1L,dto)
        );
        assertThat(exception.getMessage()).isEqualTo("Order not found");
    }
    @Test
    void shouldThrowExceptionWhenUpdatingProductNotFound(){

        Order order = new Order();
        order.setId(1L);

        OrderItemRequestDTO dto = new OrderItemRequestDTO();
        dto.setOrderId(1L);
        dto.setProductId(999L);
        dto.setQuantity(10);

        when(orderItemRepository.findById(1L))
            .thenReturn(Optional.of(orderItem));

        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));

        when(productRepository.findById(999L))
                .thenReturn(Optional.empty());

        RuntimeException exception=assertThrows(
                RuntimeException.class,
                ()->orderItemService.update(1L,dto)
        );
        assertThat(exception.getMessage()).isEqualTo("Product not found");

    }
    @Test
    void shouldDeleteOrderItemAndRecalculateOrderTotal(){

        Order order = new Order();
        order.setId(1L);

        OrderItem orderItem2=new OrderItem();
        orderItem2.setId(2L);
        orderItem2.setQuantity(2);
        orderItem2.setPrice(new BigDecimal("200.00"));

        OrderItem orderItem1=orderItem;
        orderItem1.setQuantity(5);
        orderItem1.setPrice(new BigDecimal("150.00"));

        order.addOrderItem(orderItem1);
        order.addOrderItem(orderItem2);

        when(orderItemRepository.findById(1L))
                .thenReturn(Optional.of(orderItem));

        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));

        doAnswer(invocation -> {
            order.getOrderItems().remove(orderItem1);
            return null;
        }).when(orderItemRepository).delete(orderItem1);

        orderItemService.deleteById(1L);

        verify(orderItemRepository).delete(orderItem1);
        verify(orderItemRepository).flush();
        verify(orderRepository).save(order);

        assertThat(order.getTotal()).isEqualTo(new BigDecimal("400.00"));
    }
    @Test
    void shouldThrowExceptionWhenDeletingOrderItemNotFound(){
        Long id=999L;

        when(orderItemRepository.findById(id))
                   .thenReturn(Optional.empty());
        RuntimeException exception=assertThrows(
                RuntimeException.class,
                () -> orderItemService.deleteById(id)
        );
        assertThat(exception.getMessage()).isEqualTo("Order item not found");
    }
    @Test
    void shouldThrowExceptionWhenDeletingOrderNotFound(){

        Long orderItemId=1L;
        Order order=new Order();
        order.setId(999L);
        orderItem.setOrder(order);

        when(orderItemRepository.findById(orderItemId))
                .thenReturn(Optional.of(orderItem));

        when(orderRepository.findById(999L))
                .thenReturn(Optional.empty());

        RuntimeException exception=assertThrows(
                RuntimeException.class,
                () -> orderItemService.deleteById(orderItemId)
        );
        assertThat(exception.getMessage())
                .isEqualTo("Order not found");
        verify(orderItemRepository).delete(orderItem);
        verify(orderItemRepository).flush();
    }
}
