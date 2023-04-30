package com.group3.camping_project.Controller;

import com.group3.camping_project.entities.CampingSpace;
import com.group3.camping_project.service.ImpCampingSpace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/campingspace")
public class CampingSpaceController {
@Autowired
    ImpCampingSpace impCampingSpace;


    @PostMapping("/addCampingSpace")
    public CampingSpace addRating(@RequestBody CampingSpace cms) {

        return  impCampingSpace.addCampingSpace(cms);
    }

    @GetMapping("/get-All")
    public List<CampingSpace> getAll(){
        return impCampingSpace.getAllCampingSpace();
    }

    @DeleteMapping("/delete/{IdCamping}")
    public void deleteCamping( @PathVariable Integer IdCamping)
    {
        impCampingSpace.deleteCampingSpace(IdCamping);
    }


    @PutMapping("/UpdateCamping/{idCamping}")
    public CampingSpace updateCamping(@RequestBody CampingSpace cms , @PathVariable Integer idCamping)
    {return impCampingSpace.updateCampingSpace(cms , idCamping);}
}
