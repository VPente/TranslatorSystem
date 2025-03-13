package com.bureauworks.translator.dtos;

import com.bureauworks.translator.entities.Document;

public class DocumentDTO {
    private Long id;
    private String subject;
    private String content;
    private String locale;
    private String author;
    private String translatorEmail;

    public DocumentDTO(Document document) {
        this.id = document.getId();
        this.subject = document.getSubject();
        this.content = document.getContent();
        this.locale = document.getLocale();
        this.author = document.getAuthor();
        this.translatorEmail = document.getTranslator() != null ? document.getTranslator().getEmail() : null;
    }

    public DocumentDTO() {
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTranslatorEmail() {
        return translatorEmail;
    }

    public void setTranslatorEmail(String translatorEmail) {
        this.translatorEmail = translatorEmail;
    }

    public Document toEntity() {
        Document document = new Document();
        document.setSubject(this.subject);
        document.setContent(this.content);
        document.setLocale(this.locale);
        document.setAuthor(this.author);
        return document;
    }
}