package com.example.dawine.service.product;

import com.example.dawine.dto.product.CategoryDto;
import com.example.dawine.dto.product.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService {
    Page<ProductDto> findAllProduct(Pageable pageable, String name);

    List<ProductDto> findAllBestSeller();

    List<CategoryDto> findAllCategories();

    List<ProductDto> findAllNewProduct();
}