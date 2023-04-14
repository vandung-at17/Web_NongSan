package vn.fs.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vn.fs.converter.CategoryConverter;
import vn.fs.entities.CategoryEntity;
import vn.fs.model.dto.CategoryDto;
import vn.fs.repository.CategoryRepository;
import vn.fs.service.ICategoryService;
@Service
public class CategoryService implements ICategoryService{

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private CategoryConverter categoryConverter;
	
	@Override
	public List<CategoryDto> findAllCategoryOfPage(Pageable pageable) {
		// TODO Auto-generated method stub
		List<CategoryDto> categoryDtos = new ArrayList<>();
		List<CategoryEntity> categoryEntities = categoryRepository.findAll(pageable).getContent();
		for (CategoryEntity categoryEntity : categoryEntities) {
			CategoryDto categoryDto = categoryConverter.toDto(categoryEntity);
			categoryDtos.add(categoryDto);
		}
		return categoryDtos;
	}

	@Override
	public int getTotalItem() {
		// TODO Auto-generated method stub
		int totalItem = categoryRepository.getTotalItem();
		return totalItem;
	}

}
