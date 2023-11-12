package com.example.dawine.model.order;

import com.example.dawine.model.product.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double priceOrder;
    private Double quantity;
    @ManyToOne
    @JoinColumn(name = "idOrder", referencedColumnName = "id")
    private Order order;
    @ManyToOne
    @JoinColumn(name = "idProduct", referencedColumnName = "id")
    private Product product;
}
