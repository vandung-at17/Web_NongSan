package vn.fs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vn.fs.entities.ProductEntity;
import vn.fs.model.dto.ProductDto;
import vn.fs.repository.ProductRepository;
import vn.fs.service.IProductService;

@Service
public class ProductService implements IProductService{
	
	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public List<ProductDto> findProductPage(Pageable pageable) {
		// TODO Auto-generated method stub
		List<ProductEntity> productEntities = productRepository.findAll(pageable).getContent();
		return null;
	}
	
}
