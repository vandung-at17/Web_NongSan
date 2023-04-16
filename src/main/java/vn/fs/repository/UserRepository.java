package vn.fs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.fs.entities.UserEntity;

/**
 * @author DongTHD
 *
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

	UserEntity findByEmail(String email);

}
