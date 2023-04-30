package com.group3.camping_project.controller;

import com.group3.camping_project.entities.Equipment;
import com.group3.camping_project.entities.EquipmentResponse;
import com.group3.camping_project.repository.IEquipmentRepo;
import com.group3.camping_project.repository.IImageRepo;
import com.group3.camping_project.service.FileService.IImageService;
import com.group3.camping_project.service.ImpEquipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/Equipment")
public class ControllerEquipment {
    @Autowired
    public ImpEquipe impEquipe;
    @Autowired
    IEquipmentRepo iEquipmentRepo;
    @Autowired
    IImageRepo iImageRepo;
    IImageService iImageService;
    @GetMapping("/getall")
    public List<Equipment> getaal(){
        return impEquipe.getallequip();
    }
    @GetMapping("one/{id}")
    public Equipment getoneEquipment(@PathVariable("id") Long id){
           return impEquipe.getoneEquipment(id);
    }
    @PostMapping("/addEquipment")
    public Equipment ajouterEquipment(@RequestBody Equipment equipment) {
        return impEquipe.addequipment(equipment);
    }
     @PostMapping("/image")
     public ResponseEntity<?> save(@RequestParam MultipartFile file,@RequestBody Equipment equipment) throws IOException {
         String message =iImageService.saveImage(file);
         Equipment equipment1 = impEquipe.addequipment(equipment);
         EquipmentResponse response = new EquipmentResponse(equipment1, message);
         return ResponseEntity.status(HttpStatus.OK).body(response);

     }


     @PutMapping("/updateEquipment")
    public Equipment updateEquipment(@RequestBody Equipment equipment){
        return impEquipe.updateEquipment(equipment);
     }
     @DeleteMapping("/delete/{id}")
    public void deleteEquipment(@PathVariable("id")Long id){
        impEquipe.deleteEquipement(id);
     }
}
