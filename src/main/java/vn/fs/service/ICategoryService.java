package vn.fs.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import vn.fs.model.dto.CategoryDto;

public interface ICategoryService {
	List<CategoryDto> findAllCategoryOfPage(Pageable pageable);
	int getTotalItem();
}
