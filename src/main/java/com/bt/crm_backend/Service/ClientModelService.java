package com.bt.crm_backend.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bt.crm_backend.Model.ClientFile;
import com.bt.crm_backend.Model.ClientModel;
import com.bt.crm_backend.Repo.ClientFileRepo;
import com.bt.crm_backend.Repo.ClientModelRepo;

@Service
public class ClientModelService {

	@Autowired
	private ClientModelRepo cModelRepo;

	@Autowired
	private ClientFileRepo cFileRepo;

	@Value("${file.upload-dir}")
	private String uploadDir;

	// Add Client (Without Files)
	public ClientModel addClient(ClientModel client) {
		return cModelRepo.save(client);
	}

	// Get Client by Name method
	public List<ClientModel> getClientByName(String clientName) {
		return cModelRepo.findByClientName(clientName);
	}

	// Get All Clients method
	public List<ClientModel> getAllClients() {
		return cModelRepo.findAll();
	}

	// Get Clients by Company Name method
	public List<ClientModel> getClientsByCompany(String clientCompany) {
		return cModelRepo.findByClientCompany(clientCompany);
	}

	// Add Files for an Existing Client (Files are saved in a folder named after
	// client)
	public ClientFile addFileForClient(int clientId, MultipartFile file) throws IOException {
		// Find the client by ID
		ClientModel client = cModelRepo.findById(clientId).orElseThrow(() -> new RuntimeException("Client not found"));

		// Get the client's name to create a specific folder
		String clientFolderName = client.getClientName().replaceAll("[^a-zA-Z0-9]", "_"); // Sanitize client name to
																							// make it folder-friendly

		// Create the client-specific folder inside the upload directory
		Path clientFolderPath = Paths.get(uploadDir, clientFolderName);
		if (!Files.exists(clientFolderPath)) {
			Files.createDirectories(clientFolderPath); // Create the folder if it doesn't exist
		}

		// Generate a unique file name and target location within the client-specific
		// folder
		String fileName = file.getOriginalFilename();
		Path targetLocation = clientFolderPath.resolve(fileName);

		// Save the file to the disk
		Files.copy(file.getInputStream(), targetLocation);

		// Create and save the ClientFile entity with the correct file path and upload
		// date
		ClientFile clientFile = new ClientFile();
		clientFile.setClientId(clientId); // Associate file with client
		clientFile.setFileName(fileName);
		clientFile.setFileType(file.getContentType());
		clientFile.setFilePath(targetLocation.toString()); // Save the file path within the client-specific folder
		clientFile.setUploadDate(new Date()); // Set the upload date

		return cFileRepo.save(clientFile);
	}

	// Get all files for a client
	public List<ClientFile> getFilesByClientId(int clientId) {
		return cFileRepo.findByClientId(clientId);
	}
	
	// Get a particular file by clientId and fileId
    public ClientFile getFileByClientIdAndFileId(int clientId, int fileId) {
        return cFileRepo.findByClientIdAndFileId(clientId, fileId)
                        .orElseThrow(() -> new RuntimeException("File not found"));
    }

	// Delete Specific Client File (By fileId)
	public void deleteClientFile(int fileId) {
		// Find the file to delete
		ClientFile file = cFileRepo.findById(fileId).orElseThrow(() -> new RuntimeException("File not found"));

		// Delete the file from the disk
		Path filePath = Paths.get(file.getFilePath());
		try {
			Files.deleteIfExists(filePath); // Delete the file from disk
			cFileRepo.delete(file); // Delete file record from database
		} catch (IOException e) {
			throw new RuntimeException("Failed to delete file from disk", e);
		}
	}

	// Delete All Files Associated with a Client
	public void deleteClientFiles(int clientId) {
		// Retrieve all files associated with the client
		Iterable<ClientFile> clientFiles = cFileRepo.findByClientId(clientId);

		// Delete each file from disk and from the database
		for (ClientFile clientFile : clientFiles) {
			deleteClientFile(clientFile.getFileId());
		}
	}

	// Update Client Details (Based on Client ID)
	public ClientModel updateClient(int clientId, ClientModel updatedClient) {
		// Find the existing client
		ClientModel existingClient = cModelRepo.findById(clientId)
				.orElseThrow(() -> new RuntimeException("Client not found"));

		// Update fields of the client as required
		existingClient.setClientName(updatedClient.getClientName());
		existingClient.setClientCompany(updatedClient.getClientCompany());
		existingClient.setPhno(updatedClient.getPhno());

		// Save and return the updated client
		return cModelRepo.save(existingClient);
	}

	// Delete Client (Based on Client ID)
	public void deleteClient(int clientId) {
		// Find the existing client
		ClientModel client = cModelRepo.findById(clientId).orElseThrow(() -> new RuntimeException("Client not found"));

		// Optionally delete associated files (if applicable)
		deleteClientFiles(clientId);

		// Delete the client
		cModelRepo.delete(client);
	}
}
