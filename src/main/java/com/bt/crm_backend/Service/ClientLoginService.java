package com.bt.crm_backend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.bt.crm_backend.Model.BtUser;


@Service
public class ClientLoginService {

	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JwtService jwtService;
	
	public ClientLoginService(AuthenticationManager authManager, JwtService jwtService) {
		this.authManager = authManager;
		this.jwtService = jwtService;
	}



	public String verify(BtUser user) {
		Authentication authentication = 
				authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
		if(authentication.isAuthenticated())
			return jwtService.generateToken(user.getUsername());
		return "fail";
	}
}
