package com.bt.crm_backend.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bt.crm_backend.Model.ClientFile;
import com.bt.crm_backend.Model.ClientModel;
import com.bt.crm_backend.Service.ClientModelService;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

	@Autowired
	private ClientModelService cModelService;

	// Get all clients
	@GetMapping("/")
	public ResponseEntity<List<ClientModel>> getAllClients() {
		List<ClientModel> clients = cModelService.getAllClients();
		return ResponseEntity.ok(clients);
	}

	// Check if a client with the same name and company already exists
	@GetMapping("/check-existence")
	public ResponseEntity<Boolean> checkClientExists(@RequestParam String clientName, 
	                                                 @RequestParam String clientCompany) {
	    boolean exists = cModelService.checkIfClientExists(clientName, clientCompany);
	    return ResponseEntity.ok(exists);
	}

	
	// Add a new client (without files)
	@PostMapping("/add-client")
	public ResponseEntity<?> addClient(@RequestBody ClientModel client) {
	    // Check if the client already exists
	    boolean clientExists = cModelService.checkIfClientExists(client.getClientName(), client.getClientCompany());
	    if (clientExists) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                             .body("Client with the same name and company already exists!");
	    }

	    // Add the new client
	    ClientModel savedClient = cModelService.addClient(client);
	    return ResponseEntity.ok(savedClient);
	}

	// Get client by name
	@GetMapping("/client/{clientName}")
	public ResponseEntity<List<ClientModel>> getClientByName(@PathVariable String clientName) {
		List<ClientModel> client = cModelService.getClientByName(clientName);
		if (client != null) {
			return ResponseEntity.ok(client);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	// Add files for an existing client (by clientId)
	@PostMapping("/{clientId}/add-files")
	public ResponseEntity<ClientFile> addFilesToClient(@PathVariable int clientId,
			@RequestParam("files") MultipartFile file) {
		try {
			ClientFile clientFile = cModelService.addFileForClient(clientId, file);
			return ResponseEntity.ok(clientFile);
		} catch (Exception e) {
			return ResponseEntity.status(500).body(null);
		}
	}

	// Get all files associated with a client
	@GetMapping("/{clientId}/files")
	public ResponseEntity<List<ClientFile>> getFilesByClientId(@PathVariable int clientId) {
		List<ClientFile> clientFiles = cModelService.getFilesByClientId(clientId);
		if (clientFiles.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // No files found for client
		}
		return ResponseEntity.ok(clientFiles);
	}

	// Get a particular file for a client by fileId
	@GetMapping("/{clientId}/files/{fileId}")
	public ResponseEntity<ClientFile> getFileByClientIdAndFileId(@PathVariable int clientId, @PathVariable int fileId) {
		try {
			ClientFile clientFile = cModelService.getFileByClientIdAndFileId(clientId, fileId);
			return ResponseEntity.ok(clientFile);
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // File not found
		}
	}

	// Delete a specific file (by fileId)
	@DeleteMapping("/{clientId}/delete-file/{fileId}")
	public ResponseEntity<Void> deleteClientFile(@PathVariable int fileId) {
		try {
			cModelService.deleteClientFile(fileId);
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}

	// Delete all files associated with a client
	@DeleteMapping("/{clientId}/delete-files")
	public ResponseEntity<Void> deleteAllClientFiles(@PathVariable int clientId) {
		try {
			cModelService.deleteClientFiles(clientId);
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}

	// Update client details (by clientId)
	@PutMapping("/{clientId}/update")
	public ResponseEntity<ClientModel> updateClientDetails(@PathVariable int clientId,
			@RequestBody ClientModel updatedClient) {
		try {
			ClientModel updatedClientData = cModelService.updateClient(clientId, updatedClient);
			return ResponseEntity.ok(updatedClientData);
		} catch (Exception e) {
			return ResponseEntity.status(500).body(null);
		}
	}

	// Delete a client (by clientId)
	@DeleteMapping("/{clientId}/delete")
	public ResponseEntity<Void> deleteClient(@PathVariable int clientId) {
		try {
			cModelService.deleteClient(clientId);
			return ResponseEntity.noContent().build(); // Return 204 No Content status after deletion
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}
}
