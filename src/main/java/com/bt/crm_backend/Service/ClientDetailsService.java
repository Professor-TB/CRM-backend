//package com.bt.crm_backend.Service;
//
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.bt.crm_backend.Model.BtUser;
//import com.bt.crm_backend.Model.ClientPrincipal;
//import com.bt.crm_backend.Repo.ClientRepo;
//
//@Service
//public class ClientDetailsService implements UserDetailsService{
//
//private ClientRepo repo;
//	
//	public ClientDetailsService(ClientRepo repo) {
//		this.repo = repo;
//	}
//
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		
//		BtUser user = repo.findByUsername(username);
//		if(user == null) {
//			System.out.println("User not found");
//			throw new UsernameNotFoundException("User not found");
//		}
//		return new ClientPrincipal(user);
//	}
//}