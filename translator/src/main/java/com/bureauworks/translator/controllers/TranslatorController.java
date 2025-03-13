package com.bureauworks.translator.controllers;

import com.bureauworks.translator.entities.Translator;
import com.bureauworks.translator.exceptions.ResourceNotFoundException;
import com.bureauworks.translator.services.TranslatorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/translators")
public class TranslatorController {

    private final TranslatorService translatorService;

    public TranslatorController(TranslatorService translatorService) {
        this.translatorService = translatorService;
    }

    /**
     * Retrieves all translators.
     *
     * @return ResponseEntity with a list of all translators.
     */
    @GetMapping
    public ResponseEntity<List<Translator>> getAllTranslators() {
        List<Translator> translators = translatorService.findAll();
        return ResponseEntity.ok(translators);
    }

    /**
     * Retrieves a translator by its ID.
     *
     * @param id The translator ID.
     * @return ResponseEntity with the found translator.
     * @throws ResourceNotFoundException if the translator is not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Translator> getTranslatorById(@PathVariable Long id) {
        Translator translator = translatorService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Translator not found with ID: " + id));
        return ResponseEntity.ok(translator);
    }

    /**
     * Creates a new translator.
     *
     * @param translator The translator to create.
     * @return ResponseEntity with the created translator.
     */
    @PostMapping
    public ResponseEntity<Translator> createTranslator(@RequestBody Translator translator) {
        Translator createdTranslator = translatorService.create(translator);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTranslator);
    }

    /**
     * Updates an existing translator.
     *
     * @param id The ID of the translator to update.
     * @param translator The translator with updated information.
     * @return ResponseEntity with the updated translator.
     * @throws ResourceNotFoundException if the translator is not found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Translator> updateTranslator(@PathVariable Long id,
                                                       @RequestBody Translator translator) {
        Translator updatedTranslator = translatorService.update(id, translator)
                .orElseThrow(() -> new ResourceNotFoundException("Translator not found with ID: " + id));
        return ResponseEntity.ok(updatedTranslator);
    }

    /**
     * Deletes a translator by its ID.
     *
     * @param id The ID of the translator to delete.
     * @return ResponseEntity with no content.
     * @throws ResourceNotFoundException if the translator is not found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTranslator(@PathVariable Long id) {
        boolean deleted = translatorService.delete(id);
        if (!deleted) {
            throw new ResourceNotFoundException("Translator not found with ID: " + id);
        }
        return ResponseEntity.noContent().build();
    }
}