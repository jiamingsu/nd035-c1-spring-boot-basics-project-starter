package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FileService {

    private final FileMapper fileMapper;

    public int createFile(File file) {
        return fileMapper.insert(file);
    }

    public List<File> listAllFiles(Integer userId) {
        return fileMapper.listAllFiles(userId);
    }

    public File getFile(Integer userId, Integer fileId) {
        return fileMapper.getFile(userId, fileId);
    }

    public int deleteFile(Integer userId, Integer fileId) {
        return fileMapper.deleteFile(userId, fileId);
    }
}
