package vn.fs.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import vn.fs.model.dto.ProductDto;

public interface IProductService {
	List<ProductDto> findProductPage (Pageable pageable);
}
