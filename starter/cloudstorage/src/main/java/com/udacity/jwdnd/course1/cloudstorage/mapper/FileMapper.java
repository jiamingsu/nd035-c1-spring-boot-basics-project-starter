package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.FileResult;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES(#{filename}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(File file);

    @Select("SELECT fileId, filename FROM FILES WHERE userid = #{userId}")
    List<FileResult> listAllFiles(Integer userId);

    @Select("SELECT * FROM FILES WHERE userid = #{userId} AND fileid = #{fileId}")
    File getFile(Integer userId, Integer fileId);

    @Delete("DELETE FROM FILES WHERE userid = #{userId} AND fileid = #{fileId}")
    int deleteFile(Integer userId, Integer fileId);
}
