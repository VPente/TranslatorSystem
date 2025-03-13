package com.bureauworks.translator.dtos;

public class DocumentCsvDTO {

    private String subject;

    private String content;

    private String locale;

    private String author;

    public DocumentCsvDTO(
            String subject,
            String content,
            String locale,
            String author

    ) {
        this.subject = subject;
        this.content = content;
        this.locale = locale;
        this.author = author;
    }

    // Getters e Setters
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
}