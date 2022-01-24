package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/home")
public class HomeController {
    private final UserService userService;
    private final NoteService noteService;
    private static final String NOTE_SAVE_ERROR = "There was an error saving your note. Please try again.";

    @GetMapping()
    public String homeView(Authentication authentication, Model model) {
        Integer userId = getUserId(authentication);
        List<Note> notes = noteService.listAllNotes(userId);
        model.addAttribute("notes", notes);
        return "home";
    }

    @GetMapping(value = "/notes/delete/{noteId}")
    public String deleteNote(Authentication authentication, @PathVariable("noteId") int noteId, Model model) {
        Integer userId = getUserId(authentication);

        String error = null;
        try {
            int rowsAffected = noteService.deleteNote(userId, noteId);
            if (rowsAffected < 1) {
                error = NOTE_SAVE_ERROR;
            }
        } catch (Exception e) {
            error = e.getMessage();
        }

        if (error == null) {
            model.addAttribute("saveSuccess", true);
        } else if (error.equals(NOTE_SAVE_ERROR)) {
            model.addAttribute("saveError", error);
        } else {
            model.addAttribute("error", error);
        }

        return "result";
    }

    @PostMapping(value = "/notes")
    public String createOrUpdateNote(Authentication authentication, @ModelAttribute Note request, Model model) {
        Integer userId = getUserId(authentication);
        Integer noteId = request.getNoteId();

        String error = null;

        try {
            Note note = new Note(noteId, request.getNoteTitle(), request.getNoteDescription(), userId);
            int rowsAffected = noteId == null ? noteService.createNote(note) : noteService.updateNote(note);
            if (rowsAffected < 1) {
                error = NOTE_SAVE_ERROR;
            }
        } catch (Exception e) {
            error = e.getMessage();
        }

        if (error == null) {
            model.addAttribute("saveSuccess", true);
        } else if (error.equals(NOTE_SAVE_ERROR)) {
            model.addAttribute("saveError", error);
        } else {
            model.addAttribute("error", error);
        }

        return "result";
    }

    private Integer getUserId(Authentication authentication) {
        String username = authentication.getName();
        User user = userService.getUser(username);
        return user.getUserId();
    }
}
