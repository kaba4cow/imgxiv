package com.kaba4cow.imgxiv.domain.user.service;

import org.springframework.stereotype.Service;

import com.kaba4cow.imgxiv.domain.user.dto.UserDto;
import com.kaba4cow.imgxiv.domain.user.dto.UserMapper;
import com.kaba4cow.imgxiv.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DefaultUserService implements UserService {

	private final UserRepository userRepository;

	private final UserMapper userMapper;

	@Override
	public UserDto getUser(Long id) {
		return userMapper.mapToDto(userRepository.findByIdOrThrow(id));
	}

}
