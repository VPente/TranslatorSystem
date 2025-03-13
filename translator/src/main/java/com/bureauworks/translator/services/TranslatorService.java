package com.bureauworks.translator.services;

import com.bureauworks.translator.entities.Translator;
import com.bureauworks.translator.repositories.TranslatorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TranslatorService {

    private final TranslatorRepository translatorRepository;

    public TranslatorService(TranslatorRepository translatorRepository) {
        this.translatorRepository = translatorRepository;
    }

    /**
     * Returns all registered translators.
     */
    public List<Translator> findAll() {
        return translatorRepository.findAll();
    }

    /**
     * Finds a translator by ID.
     *
     * @param id Translator ID.
     * @return Optional containing the translator if found.
     */
    public Optional<Translator> findById(Long id) {
        return translatorRepository.findById(id);
    }

    /**
     * Creates a new translator.
     */
    @Transactional
    public Translator create(Translator translator) {
        return translatorRepository.save(translator);
    }

    /**
     * Updates an existing translator.
     *
     * @param id Translator ID.
     * @param updatedTranslator Translator entity with new data.
     * @return Updated translator entity wrapped in Optional.
     */
    @Transactional
    public Optional<Translator> update(Long id, Translator updatedTranslator) {
        return translatorRepository.findById(id).map(existingTranslator -> {
            existingTranslator.setName(updatedTranslator.getName());
            existingTranslator.setEmail(updatedTranslator.getEmail());
            existingTranslator.setSourceLanguage(updatedTranslator.getSourceLanguage());
            existingTranslator.setTargetLanguage(updatedTranslator.getTargetLanguage());
            return translatorRepository.save(existingTranslator);
        });
    }

    /**
     * Deletes a translator by ID.
     *
     * @param id Translator ID.
     * @return true if the translator was found and deleted, false otherwise.
     */
    @Transactional
    public boolean delete(Long id) {
        Optional<Translator> translatorOptional = translatorRepository.findById(id);
        translatorOptional.ifPresent(translatorRepository::delete);
        return translatorOptional.isPresent();
    }
}