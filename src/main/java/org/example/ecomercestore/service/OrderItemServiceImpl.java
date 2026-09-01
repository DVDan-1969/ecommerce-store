package org.example.ecomercestore.service;

import jakarta.transaction.Transactional;
import org.example.ecomercestore.dto.OrderItemRequestDTO;
import org.example.ecomercestore.dto.OrderItemResponseDTO;
import org.example.ecomercestore.mapper.OrderItemMapper;
import org.example.ecomercestore.model.Order;
import org.example.ecomercestore.model.OrderItem;
import org.example.ecomercestore.model.Product;
import org.example.ecomercestore.repository.OrderItemRepository;
import org.example.ecomercestore.repository.OrderRepository;
import org.example.ecomercestore.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;


@Service
@Transactional
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository repository;
    private final OrderItemMapper mapper;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderItemServiceImpl(OrderItemRepository repository,OrderItemMapper mapper,
                                OrderRepository orderRepository,
                                ProductRepository productRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.orderRepository=orderRepository;
        this.productRepository=productRepository;
    }



    @Override
    public List<OrderItemResponseDTO> getAllOrderItems() {return repository.findAll()
            .stream()
            .map(mapper::toResponseDTO)
            .toList();


    }

    @Override
    public OrderItemResponseDTO getOrderItemById(Long id) {
        OrderItem orderItem = repository.findById(id)
                .orElseThrow(()->new RuntimeException("Order item not found"));
        return mapper.toResponseDTO(orderItem);
    }

    @Override
    public OrderItemResponseDTO save(OrderItemRequestDTO dto) {
        Order order=orderRepository.findById(dto.getOrderId())
                .orElseThrow(()->new RuntimeException("Order not found"));
        Product product=productRepository.findById(dto.getProductId())
                .orElseThrow(()->new RuntimeException("Product not found"));
        OrderItem orderItem=mapper.toEntity(dto,order,product);
        OrderItem orderItemSaved = repository.save(orderItem);
        order.setTotal(calculeazaTotal(order));
        orderRepository.save(order);
        return mapper.toResponseDTO(orderItemSaved);

    }


    @Override
    public OrderItemResponseDTO update(Long id, OrderItemRequestDTO dto) {
        OrderItem orderItem= repository.findById(id)
                .orElseThrow(()->new RuntimeException("Order item not found"));
        Order order=orderRepository.findById(dto.getOrderId())
                .orElseThrow(()->new RuntimeException("Order not found"));
        Product product=productRepository.findById(dto.getProductId())
                .orElseThrow(()->new RuntimeException("Product not found"));
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setQuantity(dto.getQuantity());
        orderItem.setPrice(product.getPrice());
        OrderItem orderItemUpdated = repository.save(orderItem);
        order.setTotal(calculeazaTotal(order));
        orderRepository.save(order);
        return mapper.toResponseDTO(orderItemUpdated);
    }
    private BigDecimal calculeazaTotal(Order order) {
        return order.getOrderItems()
                .stream()
                .map(orderItem -> orderItem.getPrice()
                        .multiply(BigDecimal.valueOf(orderItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    @Override
    public void deleteById(Long id) {
        OrderItem orderItem=repository.findById(id)
                .orElseThrow(()->new RuntimeException("Order item not found"));
        Long orderId= orderItem.getOrder().getId();
        repository.delete(orderItem);
        repository.flush();
        Order order=orderRepository.findById(orderId)
                        .orElseThrow(()->new RuntimeException("Order not found"));
        order.setTotal(calculeazaTotal(order));
        orderRepository.save(order);
    }

}
