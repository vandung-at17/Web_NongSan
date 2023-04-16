package vn.fs.converter;

import org.springframework.stereotype.Component;

import vn.fs.entities.ProductEntity;
import vn.fs.model.dto.ProductDto;

@Component
public class ProductConverter {
	public ProductDto toDto(ProductEntity productEntity) {
		ProductDto productDto = new ProductDto();
		productDto.setProductId(productEntity.getProductId());
		productDto.setProductName(productEntity.getProductName());
		productDto.setQuantity(productEntity.getQuantity());
		productDto.setPrice(productEntity.getPrice());
		productDto.setDiscount(productEntity.getDiscount());
		productDto.setProductImage(productEntity.getProductImage());
		productDto.setDescription(productEntity.getDescription());
		productDto.setEnteredDate(productEntity.getEnteredDate());
		productDto.setStatus(productEntity.getStatus());
		productDto.setFavorite(productEntity.getFavorite());
		productDto.setCategory(productEntity.getCategory());
		return productDto;
	}
}
