package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NoteService {

    private final NoteMapper noteMapper;

    public int createNote(Note note) {
        return noteMapper.insert(note);
    }

    public List<Note> listAllNotes(Integer userId) {
        return noteMapper.listAllNotes(userId);
    }

    public Note findNote(Integer userId, String noteTitle, String noteDescription) { return noteMapper.findNote(userId, noteTitle, noteDescription); }

    public int updateNote(Note note) {
        return noteMapper.updateNote(note);
    }

    public int deleteNote(Integer userId, Integer noteId) {
        return noteMapper.deleteNote(userId, noteId);
    }
}
