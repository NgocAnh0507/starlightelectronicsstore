package com.greenvn.starlightelectronicsstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class EmployeeDetailsServiceImp implements UserDetailsService {

	@Autowired
	private UserService userService;
	public final static String ROLE_PREFIX = "ROLE_";// Spring Security 4
	public final static String PERMISSION_AUTHENTICATED = "AUTH";

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

}
