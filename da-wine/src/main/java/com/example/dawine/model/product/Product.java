package com.example.dawine.model.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "price")
    private double price;
    @Column(name = "image_url", columnDefinition = "LONGTEXT")
    private String imageUrl;
    @Column(name = "concentration")
    private String concentration;
    @Column(name = "capacity")
    private String capacity;
    @Column(name = "product_description", columnDefinition = "LONGTEXT")
    private String productDescription;
    @Column(name = "flag_deleted")
    private boolean flagDeleted;

    @ManyToOne
    @JoinColumn(name = "brand_id", referencedColumnName = "brand_id")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "type_id")
    private Category category;
}
