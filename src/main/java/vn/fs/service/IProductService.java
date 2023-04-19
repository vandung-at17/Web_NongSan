package vn.fs.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import vn.fs.model.dto.ProductDto;

public interface IProductService {
	public List<ProductDto> findAllProductOfPage (Pageable pageable);
	public int getTotalItem();
	public int getTotalItem(String name);
	public List<ProductDto> findProductOfName(String name, Pageable pageable);
	public ProductDto findById(Long id);
	public ProductDto insert(ProductDto productDto, MultipartFile file);
}
