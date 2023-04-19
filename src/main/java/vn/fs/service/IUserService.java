package vn.fs.service;

import vn.fs.model.dto.UserDto;

public interface IUserService {
	public UserDto findByEmail (String email);
}
