package com.example.dawine.service.product;

import com.example.dawine.dto.product.CategoryDto;
import com.example.dawine.dto.product.ProductDto;
import com.example.dawine.repository.product.IProductRepository;
import com.example.dawine.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService {
    @Autowired
    private IProductRepository productRepository;

    @Override
    public Page<ProductDto> findAllProduct(Pageable pageable, String name) {
        return productRepository.findAllProduct(pageable, "%" + name + "%");
    }

    @Override
    public List<ProductDto> findAllBestSeller() {
        return productRepository.findAllBestSeller();
    }

    @Override
    public List<CategoryDto> findAllCategories() {
        return productRepository.findAllCategories();
    }

    @Override
    public List<ProductDto> findAllNewProduct() {
        return productRepository.findAllNewProduct();
    }
}