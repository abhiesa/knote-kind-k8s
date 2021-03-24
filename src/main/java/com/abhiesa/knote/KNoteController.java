package com.abhiesa.knote;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author abhiesa
 * */
@Controller
class KNoteController {

    private final KNoteService service;

    public KNoteController(final KNoteService service) {
        this.service = service;
    }

    private static final String CMD_PUBLISH = "Publish";
    private static final String CMD_UPLOAD = "Upload";

    @GetMapping("/knote")
    public String index(Model model) {

        final List<Note> notes = service.getAllNotes();
        model.addAttribute("notes", notes);
        return "knote";
    }

    @PostMapping("/note")
    public String saveNotes(@RequestParam("image") final MultipartFile file,
                            @RequestParam final String description,
                            @RequestParam(required = false) final String publish,
                            @RequestParam(required = false) final String upload,
                            Model model) throws Exception {

        if (CMD_PUBLISH.equals(publish)) {

            service.saveNote(description);

            final List<Note> notes = service.getAllNotes();
            model.addAttribute("notes", notes);
            model.addAttribute("description", "");

            return "redirect:/knote";
        }
        if (CMD_UPLOAD.equals(upload)) {

            if (file != null && file.getOriginalFilename() != null && !file.getOriginalFilename().isEmpty()) {

                final String desc = service.uploadImage(file, description);
                model.addAttribute("description", desc);
            }

            final List<Note> notes = service.getAllNotes();
            model.addAttribute("notes", notes);
            return "knote";
        }
        return "knote";
    }
}