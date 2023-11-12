package com.example.dawine.controller;

import com.example.dawine.dto.product.CategoryDto;
import com.example.dawine.dto.product.ProductDto;
import com.example.dawine.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@CrossOrigin("*")
public class ProductController {
    @Autowired
    private IProductService productService;

    @GetMapping("/listBestSeller")
    public ResponseEntity<List<ProductDto>> getListBestSeller() {
        List<ProductDto> productDtos = productService.findAllBestSeller();
        if (productDtos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDto>> getListCategories() {
        List<CategoryDto> categories = productService.findAllCategories();
        if (categories.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/listNewProduct")
    public ResponseEntity<List<ProductDto>> getListNewProduct() {
        List<ProductDto> productDtos = productService.findAllNewProduct();
        if (productDtos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }
}