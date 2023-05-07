package com.group3.camping_project.controller;

import com.group3.camping_project.entities.Equipment;

import com.group3.camping_project.entities.enums.EquipmentType;
import com.group3.camping_project.repository.IEquipmentRepo;
import com.group3.camping_project.repository.IImageRepo;
import com.group3.camping_project.service.equipement_service.ImpEquipe;
import com.group3.camping_project.service.file_service.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
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
    @PostMapping("/addEquipment1/{id}")
    public Equipment ajouterEquipment1(@RequestBody Equipment equipment,@PathVariable("id") int id) {
        return impEquipe.addequipment1(equipment,id);
    }
    @PostMapping("/addEquipment2/{id}/{id1}")
    public Equipment ajouterEquipment2(@RequestBody Equipment equipment,@PathVariable("id") int id,@PathVariable("id1") int id1) {
        return impEquipe.addEquipment2(equipment,id,id1);
    }
    @GetMapping("/getbytype")
    public List<Equipment>equipment (@RequestParam("equipmentType") EquipmentType equipmentType){
        return impEquipe.findbytype(equipmentType);
    }
    @PutMapping("/updateEquipment1/{id}")
    public Equipment updateEquipment1(@RequestBody Equipment equipment,@PathVariable("id") int id){
        return impEquipe.updateEquipment1(equipment,id);
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
