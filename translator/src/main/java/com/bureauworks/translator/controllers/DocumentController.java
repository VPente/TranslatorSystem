package com.bureauworks.translator.controllers;

import com.bureauworks.translator.dtos.DocumentDTO;
import com.bureauworks.translator.entities.Document;
import com.bureauworks.translator.exceptions.ResourceNotFoundException;
import com.bureauworks.translator.services.DocumentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    /**
     * Retrieves all documents.
     *
     * @return ResponseEntity with a list of all documents.
     */
    @GetMapping
    public ResponseEntity<List<DocumentDTO>> getAllDocuments() {
        List<Document> documents = documentService.findAll();
        List<DocumentDTO> documentDTOs = documents.stream()
                .map(DocumentDTO::new)
                .toList();
    
    return ResponseEntity.ok(documentDTOs);
    }
    

    /**
     * Retrieves a document by its ID.
     *
     * @param id The document ID.
     * @return ResponseEntity with the found document.
     * @throws ResourceNotFoundException if the document is not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Document> getDocumentById(@PathVariable Long id) {
        Document document = documentService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with ID: " + id));
        return ResponseEntity.ok(document);
    }

    /**
     * Creates a new document and associates it with a translator.
     *
     * @param document The document to create.
     * @return ResponseEntity with the created document.
     * @throws ResourceNotFoundException if the translator is not found.
     */
    @PostMapping
    public ResponseEntity<Document> createDocument(
                                                   @RequestBody Document document) {
        Document createdDocument = documentService.create(document);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDocument);
    }

    /**
     * Updates an existing document.
     *
     * @param id The ID of the document to update.
     * @param document The document with updated information.
     * @return ResponseEntity with the updated document.
     * @throws ResourceNotFoundException if the document is not found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DocumentDTO> updateDocument(@PathVariable Long id,
                                                        @RequestBody DocumentDTO documentDTO) {
        // Converte o DTO para a entidade
        Document documentToUpdate = documentDTO.toEntity();
        
        // Realiza a atualização
        Document updatedDocument = documentService.update(id, documentToUpdate);
        
        // Converte a entidade atualizada para DTO e retorna
        return ResponseEntity.ok(new DocumentDTO(updatedDocument));
    }

    /**
     * Deletes a document by its ID.
     *
     * @param id The ID of the document to delete.
     * @return ResponseEntity with no content.
     * @throws ResourceNotFoundException if the document is not found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
        documentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint para upload e processamento do CSV de documentos.
     * O arquivo deve conter os headers: subject;content;locale;author.
     */
    @PostMapping("/upload")
    public ResponseEntity<String> uploadDocumentsCSV(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Arquivo de documentos está vazio.");
        }
        try {
            documentService.processCSV(file);
            return ResponseEntity.ok("Arquivo de documentos processado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Erro ao processar arquivo de documentos: " + e.getMessage());
        }
    }
}