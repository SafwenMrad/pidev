package com.group3.camping_project.service.FileService;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IVideoService {
    public String saveVideo(MultipartFile file) throws IOException;

    public byte[] getVideo(int videoId) throws IOException;
}
