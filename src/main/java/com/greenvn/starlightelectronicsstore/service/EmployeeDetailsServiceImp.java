package com.greenvn.starlightelectronicsstore.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.greenvn.starlightelectronicsstore.entities.Employee;
//import com.greenvn.starlightelectronicsstore.entities.Permission;
import com.greenvn.starlightelectronicsstore.entities.Position;

public class EmployeeDetailsServiceImp implements UserDetailsService {

	@Autowired
	private EmployeeService empService;
	public final static String ROLE_PREFIX = "ROLE_";// Spring Security 4
	public final static String PERMISSION_AUTHENTICATED = "AUTH";

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Employee emp = null;
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		try {
			emp =empService.getEmployeeByUserName(username);
			if(emp==null) {
				throw new UsernameNotFoundException("employee not found");
			}
			//kiem tra quyen
			GrantedAuthority role = new SimpleGrantedAuthority(ROLE_PREFIX + PERMISSION_AUTHENTICATED);// required to
			// login
			authorities.add(role);
//			if(emp.getPermissions()!=null && emp.getPermissions().size()>0) {
//				List<Permission>permissions = emp.getPermissions();
//				for (Permission permission : permissions) {
//					GrantedAuthority auth = new SimpleGrantedAuthority(ROLE_PREFIX + permission.getName());
//					authorities.add(auth);
//				}
//			}
			if(emp.getPosition()!=null) {
				Position position = emp.getPosition();
				GrantedAuthority auth = new SimpleGrantedAuthority(ROLE_PREFIX + position.getName());
				authorities.add(auth);
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new UsernameNotFoundException("Employee not found.");
		}
		org.springframework.security.core.userdetails.User secEmployee = new org.springframework.security.core.userdetails.User(username, emp.getPassword(), emp.getIsActive(), true, true, true, authorities);
		return secEmployee;
	}

}
