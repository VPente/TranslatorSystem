package com.bureauworks.translator.repositories;

import com.bureauworks.translator.entities.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    /**
     * Finds all documents belonging to a specific translator.
     *
     * @param translatorId ID of the translator.
     * @return List of documents associated with the given translator.
     */
    List<Document> findByTranslatorId(Long translatorId);
}