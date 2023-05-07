package com.group3.camping_project.controller;



import com.group3.camping_project.service.FileService.IImageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin(origins = "*")



@RestController
@RequestMapping("/image")
public class ImageController {



    @Autowired
    IImageService iImageService;

    @PostMapping("/saveImage")
    public ResponseEntity<?> saveImage(@RequestParam MultipartFile file) throws IOException {
        iImageService.saveImage(file);
        return ResponseEntity.status(HttpStatus.OK).body("image zaved");
    }

    @GetMapping("/getImage/{id}")
    public ResponseEntity<?> getImage(@PathVariable int id) throws IOException {
        byte[] image =iImageService.getImage(id);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).
                body(image);

    }

}
