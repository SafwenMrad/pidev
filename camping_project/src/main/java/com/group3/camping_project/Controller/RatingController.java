package com.group3.camping_project.Controller;

import com.group3.camping_project.entities.Rating;
import com.group3.camping_project.service.user_management.ImpRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ratings")
public class RatingController {
    @Autowired
    private ImpRating impRating;
    @PostMapping("/addRating/{idUser}/{idcampingspace}")
    public Rating addRating(@RequestBody Rating rating  , @PathVariable Integer idUser, @PathVariable Integer idcampingspace) {

        return  impRating.addRating(rating , idUser, idcampingspace );
    }
}
