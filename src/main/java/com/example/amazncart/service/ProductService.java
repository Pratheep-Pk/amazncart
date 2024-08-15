package com.example.amazncart.service;

import com.example.amazncart.dto.DiscountDTO;
import com.example.amazncart.dto.ProductDTO;
import com.example.amazncart.entity.Product;
import com.example.amazncart.exception.NotFoundException;
import com.example.amazncart.repository.ProductRepository;
import com.example.amazncart.util.CurrencyConverter;
import com.example.amazncart.util.PromotionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CurrencyConverter currencyConverter;

	@Autowired
	private PromotionUtil promotionUtil;

	public ProductDTO createProduct(ProductDTO productDTO) {
		Product product = ProductDTO.toEntity(productDTO);
		Product savedProduct = productRepository.save(product);
		return ProductDTO.fromEntity(savedProduct);
	}

	public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new NotFoundException("Product not found"));
		product.setCategory(productDTO.getCategory());
		product.setPrice(productDTO.getPrice());
		product.setCurrency(productDTO.getCurrency());
		product.setOrigin(productDTO.getOrigin());
		product.setRating(productDTO.getRating());
		product.setInventory(productDTO.getInventory());
		return ProductDTO.fromEntity(productRepository.save(product));
	}

	public void deleteProduct(Long productId) {
		if (!productRepository.existsById(productId)) {
			throw new NotFoundException("Product not found");
		}
		productRepository.deleteById(productId);
	}

	public List<ProductDTO> getAllProducts() {
		List<Product> products = productRepository.findAll();
		return products.stream().map(ProductDTO::fromEntity).map(this::convertToINRIfNeeded).map(dto -> {
			dto.setDiscount(promotionUtil.applyDefaultDiscount(dto));
			return dto;
		}).collect(Collectors.toList());
	}

	public List<ProductDTO> getProductsByPromotion(String promotion) {
		if (!promotion.equals("SetA") && !promotion.equals("SetB")) {
			throw new NotFoundException("Promotion not found");
		}
		List<Product> products = productRepository.findAll();
		return products.stream().map(ProductDTO::fromEntity).map(this::convertToINRIfNeeded).map(dto -> {
			dto.setDiscount(promotionUtil.applyPromotion(dto, promotion));
			return dto;
		}).collect(Collectors.toList());
	}

	private ProductDTO convertToINRIfNeeded(ProductDTO dto) {
		if (!"INR".equals(dto.getCurrency())) {
			dto.setPrice(currencyConverter.convertToINR(dto.getPrice(), dto.getCurrency()));
			dto.setCurrency("INR");
		}
		return dto;
	}
}
