package com.bt.crm_backend.Repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bt.crm_backend.Model.ClientFile;
import com.bt.crm_backend.Model.ClientModel;

@Repository
public interface ClientModelRepo extends JpaRepository<ClientModel, Integer> {

	List<ClientModel> findByClientName(String clientName);

	List<ClientModel> findByClientCompany(String clientCompany);
	
	List<ClientModel> findAll();
	
	List<ClientFile> findByClientId(int clientId);
	
	Optional<ClientModel> findByClientNameAndClientCompany(String clientName, String clientCompany);
}
