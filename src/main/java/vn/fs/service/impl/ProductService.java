package vn.fs.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vn.fs.converter.ProductConverter;
import vn.fs.entities.ProductEntity;
import vn.fs.model.dto.ProductDto;
import vn.fs.repository.ProductRepository;
import vn.fs.service.IProductService;

@Service
public class ProductService implements IProductService{
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProductConverter productConverter;
	
	@Override
	public List<ProductDto> findAllProductOfPage(Pageable pageable) {
		// TODO Auto-generated method stub
		List<ProductDto> productDtos = new ArrayList<>();
		List<ProductEntity> productEntities = productRepository.findAll(pageable).getContent();
		for (ProductEntity productEntity : productEntities) {
			ProductDto productDto = productConverter.toDto(productEntity);
			productDtos.add(productDto);
		}
		return productDtos;
	}

	@Override
	public int getTotalItem() {
		// TODO Auto-generated method stub
		int totalItem = productRepository.getTotalItem();
		return totalItem;
	}

	@Override
	public int getTotalItem(String name) {
		// TODO Auto-generated method stub
		int totalItem = productRepository.getTotalItem(name);
		return totalItem;
	}

	@Override
	public List<ProductDto> findProductOfName(String name, Pageable pageable) {
		// TODO Auto-generated method stub
		List<ProductDto> productDtos = new ArrayList<>();
		List<ProductEntity> productEntities = productRepository.findByName(name, pageable).getContent();
		for (ProductEntity productEntity : productEntities) {
			ProductDto productDto = productConverter.toDto(productEntity);
			productDtos.add(productDto);
		}
		return productDtos;
	}
	
}
