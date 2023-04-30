package com.group3.camping_project.service.user_management;

import com.group3.camping_project.entities.Rating;

import java.util.List;

public interface ImpRating {
    public Rating addRating(Rating rating , Integer idUsr , Integer idcampingspace ) ;
    public Rating updateRating(Rating rating ,Integer idRating);
    void deleteRating(Integer id);
    List<Rating> getAllRatings();
}
