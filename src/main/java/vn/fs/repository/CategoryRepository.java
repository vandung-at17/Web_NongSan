package vn.fs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.fs.entities.CategoryEntity;

/**
 * @author DongTHD
 *
 */
@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

}
