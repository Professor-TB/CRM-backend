package com.bt.crm_backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bt.crm_backend.Model.BtUser;
import com.bt.crm_backend.Service.ClientLoginService;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/auth")
public class CrmLoginController {

	@Autowired
	private ClientLoginService clientService;

	@PostMapping("/login")
	public String login(@RequestBody BtUser user) {
		return clientService.verify(user);
	}
}