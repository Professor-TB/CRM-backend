package com.bt.crm_backend.Repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bt.crm_backend.Model.BtUser;

//@EnableJpaRepositories
@Repository
public interface ClientRepo extends JpaRepository<BtUser, Integer> {

	BtUser findByUsername(String username);
}


//	Optional<BtUser> findOneByEmailAndPassword(String email, String password);