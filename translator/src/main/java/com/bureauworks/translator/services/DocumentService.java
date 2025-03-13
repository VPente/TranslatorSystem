package com.bureauworks.translator.services;

import com.bureauworks.translator.dtos.DocumentCsvDTO;
import com.bureauworks.translator.entities.Document;
import com.bureauworks.translator.entities.Translator;
import com.bureauworks.translator.exceptions.ResourceNotFoundException;
import com.bureauworks.translator.repositories.DocumentRepository;
import com.bureauworks.translator.repositories.TranslatorRepository;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.exceptions.CsvException;
import com.opencsv.CSVReader;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class DocumentService {

    private static final Logger logger = Logger.getLogger(DocumentService.class.getName());

    private final DocumentRepository documentRepository;
    private final TranslatorRepository translatorRepository;

    public DocumentService(DocumentRepository documentRepository, TranslatorRepository translatorRepository) {
        this.documentRepository = documentRepository;
        this.translatorRepository = translatorRepository;
    }

    /**
     * Retrieves all registered documents.
     */
    public List<Document> findAll() {
        return documentRepository.findAll();
    }

    /**
     * Finds a document by ID.
     *
     * @param id Document ID.
     * @return Optional containing the document if found.
     */
    public Optional<Document> findById(Long id) {
        return documentRepository.findById(id);
    }

    /**
     * Creates a new document and associates it with a translator.
     *
     * @param document     Document entity to be saved.
     * @return Saved document entity.
     * @throws ResourceNotFoundException if the translator does not exist.
     */
    @Transactional
    public Document create(Document document) {
        Translator translator = translatorRepository.findByEmail(document.getAuthor())
                .orElseThrow(() -> new ResourceNotFoundException("Translator not found with email"));

        document.setTranslator(translator);
        return documentRepository.save(document);
    }

    /**
     * Updates an existing document.
     *
     * @param id              Document ID.
     * @param updatedDocument Document entity with updated data.
     * @return Updated document entity.
     * @throws ResourceNotFoundException if the document does not exist.
     */
    @Transactional
    public Document update(Long id, Document updatedDocument) {
        Document existingDocument = findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with ID: " + id));
    
        existingDocument.setSubject(updatedDocument.getSubject());
        existingDocument.setContent(updatedDocument.getContent());
        existingDocument.setLocale(updatedDocument.getLocale());
        existingDocument.setAuthor(updatedDocument.getAuthor());
        
        return documentRepository.save(existingDocument);
    }

    /**
     * Deletes a document by ID.
     *
     * @param id Document ID.
     * @throws ResourceNotFoundException if the document does not exist.
     */
    @Transactional
    public void delete(Long id) {
        Document document = findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with ID: " + id));
        documentRepository.delete(document);
    }

    private List<DocumentCsvDTO> processCsv(MultipartFile file) throws Exception {
        try (Reader reader = new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8)) {
          
            HeaderColumnNameMappingStrategy<DocumentCsvDTO> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(DocumentCsvDTO.class);
            System.out.println(strategy + "teste");
         
            CsvToBean<DocumentCsvDTO> csvToBean = new CsvToBeanBuilder<DocumentCsvDTO>(reader)
                    .withMappingStrategy(strategy)
                    .withSeparator(';')
                    .withIgnoreLeadingWhiteSpace(true)
                    .withIgnoreQuotations(true) 
                    .withThrowExceptions(false) 
                    .build();

            System.out.println(csvToBean.getClass() + "teste2");

            List<DocumentCsvDTO> dtos = csvToBean.parse();

         

            return dtos;
        } catch (Exception e) {
            throw new Exception("Erro ao processar o CSV: " + e.getMessage(), e);
        }
    }

    /**
     * Uploads documents from a CSV file.
     * The CSV file must have a header with the columns:
     * subject;content;locale;author.
     * The 'locale' field is optional.
     *
     * This method processes the CSV file, maps the DTOs to entities, ensures that
     * each document is
     * associated with a translator (found by email or created if not existing), and
     * then saves all documents.
     *
     * @param file the CSV file containing document data.
     * @return List of saved Document entities.
     * @throws Exception if an error occurs during processing.
     */
    @Transactional
    public List<Document> uploadDocuments(MultipartFile file) throws Exception {
        // Process CSV and obtain DTOs
        List<DocumentCsvDTO> dtos = processCsv(file);
        List<Document> documents = new ArrayList<>();

        logger.info("CSV DTOs: " + dtos);

        // Map each DTO to a Document entity
        for (DocumentCsvDTO dto : dtos) {
            // Validate required fields
            if (dto.getSubject() == null || dto.getContent() == null || dto.getAuthor() == null) {
                logger.warning("Invalid row skipped: subject=" + dto.getSubject() + ", content=" + dto.getContent()
                        + ", author=" + dto.getAuthor());
                continue;
            }

            // Normalize fields (trimming extra spaces)
            String subject = normalizeSpaces(dto.getSubject());
            String content = normalizeSpaces(dto.getContent());
            String locale = (dto.getLocale() == null || dto.getLocale().isEmpty()) ? "unknown"
                    : normalizeSpaces(dto.getLocale());
            String author = normalizeSpaces(dto.getAuthor());

            // Find existing translator by email
            Translator translator = translatorRepository.findByEmail(author)
                    .orElseThrow(() -> new ResourceNotFoundException("Translator not found for email: " + author));

            System.out.println(translator + "translator");

            // Create the document using the constructor that accepts (subject, content,
            // locale, translator)
            Document document = new Document(subject, content, locale, translator);
            documents.add(document);

            System.out.println(document + "document");
        }

        // Save all documents in a batch operation and return them
        return documentRepository.saveAll(documents);
    }

    /**
     * Helper method to normalize spaces in a string.
     *
     * @param input the input string.
     * @return the normalized string.
     */
    private String normalizeSpaces(String input) {
        return (input != null) ? input.trim().replaceAll("\\s+", " ") : null;
    }

    @Transactional
    public void processCSV(MultipartFile file) throws Exception {
        try (
            Reader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))
        ) {
            // Pré-processa o conteúdo para remover linhas extras, exemplo: cabeçalho não esperado ("Python")
            Reader filteredReader = filterCSVReader(reader);

            // Configura o CSVFormat utilizando builder
            CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                    .setDelimiter(';')
                    .setHeader()                 // A primeira linha válida será utilizada como header
                    .setSkipHeaderRecord(true)   // Pula o registro de cabeçalho na iteração
                    .setIgnoreHeaderCase(true)
                    .setTrim(true)
                    .build();

            Iterable<CSVRecord> records = csvFormat.parse(filteredReader);
            List<Document> documents = new ArrayList<>();

            for (CSVRecord record : records) {
                // Verifica se a linha possui pelo menos 4 colunas
                if (record.size() < 4) {
                    continue;
                }

                try {
                    String subject = record.get("subject");
                    String content = record.get("content");
                    String locale = record.get("locale");
                    String author = record.get("author");

                    // Validação simples: o subject não pode ser vazio
                    if (subject != null && !subject.trim().isEmpty()) {
                        // Busca o tradutor a partir do e-mail (campo author)
                        Optional<Translator> translator = translatorRepository.findByEmail(author);
                        if (translator == null) {
                            // Caso o tradutor não seja encontrado, informa no log e ignora essa linha
                            System.err.println("Tradutor não encontrado para o email: " + author + ". Linha ignorada: " + record);
                            continue;
                        }

                        Document document = new Document();
                        document.setSubject(subject);
                        document.setContent(content);
                        document.setLocale(locale);
                        document.setAuthor(author);
                        document.setTranslator(translator.get());
                        
                        // Se desejar manter a relação bidirecional consistente:
                        Translator newTranslator = new Translator();
                        newTranslator.addDocument(document);
                        
                        documents.add(document);
                    }
                } catch (Exception e) {
                    System.err.println("Linha inválida, ignorando: " + record);
                }
            }
            documentRepository.saveAll(documents);
        } catch (IOException e) {
            throw new Exception(e.getMessage(), e);
        }
    }
    /**
     * Esse método faz a pré-filtragem do conteúdo do CSV.
     * Ele lê linha a linha e descarta as linhas que não parecem fazer parte da tabela,
     * baseando-se na presença do delimitador ";" e de palavras chave esperadas (por exemplo, "subject").
     */
    private Reader filterCSVReader(Reader reader) throws IOException {
        BufferedReader br = new BufferedReader(reader);
        String line;
        StringBuilder csvContent = new StringBuilder();
        boolean headerFound = false;

        // Procura pelo cabeçalho válido (que deve conter, por exemplo, "subject")
        while ((line = br.readLine()) != null) {
            if (!headerFound) {
                // Se a linha contém o delimitador ";" e possui a palavra "subject", assumimos que é o cabeçalho
                if (line.contains(";") && line.toLowerCase().contains("subject")) {
                    headerFound = true;
                    csvContent.append(line).append("\n");
                }
            } else {
                // Depois do cabeçalho, adiciona todas as linhas
                csvContent.append(line).append("\n");
            }
        }

        if (!headerFound) {
            throw new IOException("Cabeçalho CSV não encontrado.");
        }

        return new StringReader(csvContent.toString());
    }



}