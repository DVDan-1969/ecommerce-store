package org.example.ecomercestore.service;

import jakarta.transaction.Transactional;
import org.example.ecomercestore.dto.OrderItemRequestDTO;
import org.example.ecomercestore.dto.OrderItemResponseDTO;
import org.example.ecomercestore.exception.OrderItemNotFoundException;
import org.example.ecomercestore.exception.OrderNotFoundException;
import org.example.ecomercestore.exception.ProductNotFoundException;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
@Transactional
public class OrderItemServiceImpl implements OrderItemService {

    private static final Logger logger =
            LoggerFactory.getLogger(OrderItemServiceImpl.class);
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
    public List<OrderItemResponseDTO> getAllOrderItems() {
        logger.info("Retrieving all Order Items");

        return repository.findAll()
            .stream()
            .map(mapper::toResponseDTO)
            .toList();


    }

    @Override
    public OrderItemResponseDTO getOrderItemById(Long id) {

        logger.info("Searching for Order item with id {}",id);
        OrderItem orderItem = repository.findById(id)
                .orElseThrow(()->{
                    logger.warn("Order item with id {} not found",id);
                    return new OrderItemNotFoundException("Order item not found");
                });
        return mapper.toResponseDTO(orderItem);
    }

    @Override
    public OrderItemResponseDTO save(OrderItemRequestDTO dto) {
        logger.info("Creating order item for order id {} and product id {}",
                dto.getOrderId(),dto.getProductId());
        Order order=orderRepository.findById(dto.getOrderId())
                .orElseThrow(()->{
                    logger.warn("Order with id {} not found",dto.getOrderId());
                    return new OrderNotFoundException("Order not found");
                });
        Product product=productRepository.findById(dto.getProductId())
                .orElseThrow(()->{
                    logger.warn("Product with id {} not found",dto.getProductId());
                     return new ProductNotFoundException("Product not found");
                });
        OrderItem orderItem=mapper.toEntity(dto,order,product);
        OrderItem orderItemSaved = repository.save(orderItem);
        order.setTotal(calculeazaTotal(order));
        orderRepository.save(order);
        logger.info("Order item created successfully with id {}",orderItemSaved.getId());
        return mapper.toResponseDTO(orderItemSaved);

    }


    @Override
    public OrderItemResponseDTO update(Long id, OrderItemRequestDTO dto) {

        logger.info("Updating order item with id {}",id);
        OrderItem orderItem= repository.findById(id)
                .orElseThrow(()->{
                    logger.warn("Order item with id {} not found",id);
                    return new OrderItemNotFoundException("Order item not found");
                });
        Order order=orderRepository.findById(dto.getOrderId())
                .orElseThrow(()->{
                    logger.warn("Order with id {} not found",dto.getOrderId());
                    return new OrderNotFoundException("Order not found");
                });
        Product product=productRepository.findById(dto.getProductId())
                .orElseThrow(()->{
                    logger.warn("Product with id {} not found",dto.getProductId());
                     return new ProductNotFoundException("Product not found");
                });
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setQuantity(dto.getQuantity());
        orderItem.setPrice(product.getPrice());
        OrderItem orderItemUpdated = repository.save(orderItem);
        logger.info("Order item with id {} updated successfully",
                orderItemUpdated.getId());
        order.setTotal(calculeazaTotal(order));
        orderRepository.save(order);
        return mapper.toResponseDTO(orderItemUpdated);
    }
    private BigDecimal calculeazaTotal(Order order) {

        BigDecimal total = order.getOrderItems()
                .stream()
                .map(orderItem -> orderItem.getPrice()
                        .multiply(BigDecimal.valueOf(orderItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        logger.debug("Calculated total {} for order id {}",
                total,
                order.getId());

        return total;
    }


    @Override
    public void deleteById(Long id) {

        logger.info("Deleting order item with id {}", id);
        OrderItem orderItem=repository.findById(id)
                .orElseThrow(()->{
                    logger.warn("Order item with id {} not found",id);
                    return new OrderItemNotFoundException("Order item not found");
                });
        Long orderId= orderItem.getOrder().getId();
        repository.delete(orderItem);
        repository.flush();
        Order order=orderRepository.findById(orderId)
                        .orElseThrow(()->{
                            logger.warn("Order with id {} not found",orderId);
                            return new OrderNotFoundException("Order not found");
                        });
        order.setTotal(calculeazaTotal(order));
        orderRepository.save(order);
        logger.info("Order item with id {} deleted successfully", id);
    }

}
