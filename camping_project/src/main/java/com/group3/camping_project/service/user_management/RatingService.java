package com.group3.camping_project.service.user_management;

import com.group3.camping_project.entities.CampingSpace;
import com.group3.camping_project.entities.Rating;
import com.group3.camping_project.entities.User;
import com.group3.camping_project.repository.ICampingSpaceRepo;
import com.group3.camping_project.repository.IUserRepo;
import com.group3.camping_project.repository.RatingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RatingService implements ImpRating{


    @Autowired
    RatingRepo ratingRepo;

    @Autowired
    IUserRepo userRepo;

    @Autowired
    ICampingSpaceRepo campingSpaceRepo;

    @Override
    public Rating addRating(Rating rating, Integer idUsr, Integer idcampingspace) {
        User u = userRepo.findById(idUsr).orElse(null);
        CampingSpace cms = campingSpaceRepo.findById(idcampingspace).orElse(null);
        rating.setUser(u);
        rating.setCampingSpace(cms);
        ratingRepo.save(rating);
        return rating;
    }

    @Override
    public Rating updateRating(Rating rating, Integer idRating) {
        return null;
    }

    @Override
    public void deleteRating(Integer id) {

    }

    @Override
    public List<Rating> getAllRatings() {
        return null;
    }
}
