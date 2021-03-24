package com.abhiesa.knote;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class KNoteService {

    private final NotesRepository repository;
    private final KNoteProperties properties;
    private final Parser parser;
    private final HtmlRenderer renderer;

    public KNoteService(final NotesRepository repository, final KNoteProperties properties) {
        this.repository = repository;
        this.properties = properties;
        this.parser = Parser.builder().build();
        this.renderer = HtmlRenderer.builder().build();
    }

    public List<Note> getAllNotes() {

        final List<Note> notes = repository.findAll();
        Collections.reverse(notes);
        return notes;
    }



    public String uploadImage(MultipartFile file, String description) throws Exception {
        File uploadsDir = new File(properties.getUploadDir());
        if (!uploadsDir.exists()) {
            uploadsDir.mkdir();
        }
        String fileId = UUID.randomUUID().toString() + "." +
                file.getOriginalFilename().split("\\.")[1];
        file.transferTo(new File(properties.getUploadDir() + fileId));
        return description + " ![](/uploads/" + fileId + ")";
    }

    public void saveNote(final String description) {

        if (description != null && !description.trim().isEmpty()) {
            //We need to translate markup to HTML
            final Node document = parser.parse(description.trim());
            final String html = renderer.render(document);
            repository.save(new Note(html));
        }
    }
}
