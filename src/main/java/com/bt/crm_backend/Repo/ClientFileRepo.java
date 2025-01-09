package com.bt.crm_backend.Repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bt.crm_backend.Model.ClientFile;

@Repository
public interface ClientFileRepo extends JpaRepository<ClientFile, Integer> {
    List<ClientFile> findByClientId(int clientId);
    
    Optional<ClientFile> findByClientIdAndFileId(int clientId, int fileId);
}
