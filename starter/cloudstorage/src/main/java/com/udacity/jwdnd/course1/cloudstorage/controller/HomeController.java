package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/home")
public class HomeController {
    private final UserService userService;
    private final FileService fileService;
    private final NoteService noteService;
    private final CredentialService credentialService;
    private static final String SAVE_ERROR = "Your changes were not saved.";

    @GetMapping()
    public String homeView(Authentication authentication, Model model) {
        Integer userId = getUserId(authentication);
        List<FileResult> files = fileService.listAllFiles(userId);
        List<Note> notes = noteService.listAllNotes(userId);
        List<Credential> credentials = credentialService.listAllCredentials(userId);
        model.addAttribute("files", files);
        model.addAttribute("notes", notes);
        model.addAttribute("credentials", credentials);
        return "home";
    }

    @PostMapping(value =  "/files")
    public String uploadFile(Authentication authentication, @RequestParam("fileUpload") MultipartFile multipartFile, Model model) {
        Integer userId = getUserId(authentication);

        String error = null;

        try {
            if(multipartFile.isEmpty()) {
                throw new Exception("File is empty.");
            }
            File file = new File(null, multipartFile.getOriginalFilename(), multipartFile.getContentType(), String.valueOf(multipartFile.getSize()), userId, multipartFile.getBytes());
            int rowsAffected = fileService.createFile(file);
            if (rowsAffected < 1) {
                error = SAVE_ERROR;
            }
        } catch (Exception e) {
            error = e.getMessage();
        }

        updateResultModel(model, error, SAVE_ERROR);

        return "result";
    }

    @GetMapping(value = "/files/download/{fileId}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> downloadFile(Authentication authentication, @PathVariable("fileId") int fileId) {
        Integer userId = getUserId(authentication);
        File file = fileService.getFile(userId, fileId);
        return ResponseEntity
                .ok()
                .header("Content-Type", MediaType.APPLICATION_OCTET_STREAM_VALUE)
                .header("Content-Disposition", "inline; filename=\"" + file.getFilename() + "\"")
                .body(file.getFileData());
    }

    @GetMapping(value = "/files/delete/{fileId}")
    public String deleteFile(Authentication authentication, @PathVariable("fileId") int fileId, Model model){
        Integer userId = getUserId(authentication);

        String error = null;
        try {
            int rowsAffected = fileService.deleteFile(userId, fileId);
            if (rowsAffected < 1) {
                error = SAVE_ERROR;
            }
        } catch (Exception e) {
            error = e.getMessage();
        }

        updateResultModel(model, error, SAVE_ERROR);

        return "result";
    }

    @GetMapping(value = "/notes/delete/{noteId}")
    public String deleteNote(Authentication authentication, @PathVariable("noteId") int noteId, Model model) {
        Integer userId = getUserId(authentication);

        String error = null;
        try {
            int rowsAffected = noteService.deleteNote(userId, noteId);
            if (rowsAffected < 1) {
                error = SAVE_ERROR;
            }
        } catch (Exception e) {
            error = e.getMessage();
        }

        updateResultModel(model, error, SAVE_ERROR);

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
                error = SAVE_ERROR;
            }
        } catch (Exception e) {
            error = e.getMessage();
        }

        updateResultModel(model, error, SAVE_ERROR);

        return "result";
    }

    @PostMapping(value = "/credentials")
    public String createOrUpdateCredential(Authentication authentication, @ModelAttribute Credential request, Model model) {
        Integer userId = getUserId(authentication);
        Integer credentialId = request.getCredentialId();

        String error = null;

        try {
            Credential credential = new Credential(credentialId, request.getUrl(), request.getUsername(), "", request.getPassword(), userId);
            int rowsAffected = credentialId == null ? credentialService.createCredential(credential) : credentialService.updateCredential(credential);
            if (rowsAffected < 1) {
                error = SAVE_ERROR;
            }
        } catch (Exception e) {
            error = e.getMessage();
        }

        updateResultModel(model, error, SAVE_ERROR);

        return "result";
    }

    @GetMapping(value = "/credentials/delete/{credentialId}")
    public String deleteCredentials(Authentication authentication, @PathVariable("credentialId") int credentialId, Model model) {
        Integer userId = getUserId(authentication);

        String error = null;
        try {
            int rowsAffected = credentialService.deleteCredential(userId, credentialId);
            if (rowsAffected < 1) {
                error = SAVE_ERROR;
            }
        } catch (Exception e) {
            error = e.getMessage();
        }

        updateResultModel(model, error, SAVE_ERROR);

        return "result";
    }

    private Integer getUserId(Authentication authentication) {
        String username = authentication.getName();
        User user = userService.getUser(username);
        return user.getUserId();
    }

    private void updateResultModel(Model model, String error, String saveError) {
        if (error == null) {
            model.addAttribute("saveSuccess", true);
        } else if (error.equals(saveError)) {
            model.addAttribute("saveError", error);
        } else {
            model.addAttribute("error", error);
        }
    }
}
