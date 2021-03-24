package com.abhiesa.knote;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class KNoteServiceTests {

    @InjectMocks
    private KNoteService service;

    @Mock
    private NotesRepository repository;

    private static final List<Note> MOCK_NOTE_LIST = Arrays.asList(new Note("1", "Note 1"),
            new Note("2", "Note 2"), new Note("3", "Note 3"),  new Note("4", "Note 4"));

    private static final String TMP_DIR = "/Users/apande73/tmp";

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
        service = new KNoteService(repository, new KNoteProperties(TMP_DIR));
    }

    @Test
    void testGetAllNotes() {

        Mockito.when(repository.findAll()).thenReturn(MOCK_NOTE_LIST);
        final List<Note> notes = service.getAllNotes();
        Assertions.assertTrue(notes.containsAll(MOCK_NOTE_LIST));
        Mockito.verify(repository).findAll();
    }

    @Test
    void testSaveNote1() {

        Mockito.when(repository.save(Mockito.any(Note.class))).thenReturn(new Note(null, TMP_DIR));
        service.saveNote("hello");
    }

    @Test
    void testSaveNote2() {

        service.saveNote("");
    }

    @Test
    void testSaveNote3() {

        service.saveNote(null);
    }

    @Test
    void testUploadImage1() throws Exception {

        final File theDir = new File(TMP_DIR);
        if (!theDir.exists()){
            theDir.mkdirs();
        }
        final MockMultipartFile file = new MockMultipartFile("file", "hello.txt",
                MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());
        service.uploadImage(file, "test file");
    }

    @Test
    void testUploadImage2() throws Exception {

        final File theDir = new File(TMP_DIR);
        if (theDir.exists()){
            final String[]entries = theDir.list();
            for(final String s: entries){
                final File currentFile = new File(theDir.getPath(),s);
                currentFile.delete();
            }
            theDir.delete();
        }
        final MockMultipartFile file = new MockMultipartFile("file", "hello.txt",
                MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());
        service.uploadImage(file, "test file");
    }





}
