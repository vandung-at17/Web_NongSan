package vn.fs.service;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import vn.fs.entities.RoleEntity;
import vn.fs.entities.UserEntity;
import vn.fs.repository.UserRepository;

/**
 * @author DongTHD
 *
 */
@Service
public class UserDetailService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity user = userRepository.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				mapRolesToAuthorities(user.getRoles()));

	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<RoleEntity> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

}
