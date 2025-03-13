package com.bureauworks.translator.repositories;

import com.bureauworks.translator.entities.Translator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TranslatorRepository extends JpaRepository<Translator, Long> {

    /**
     * Finds a translator by email.
     *
     * @param email Translator's email.
     * @return Optional containing the translator if found.
     */
    Optional<Translator> findByEmail(String email);
}