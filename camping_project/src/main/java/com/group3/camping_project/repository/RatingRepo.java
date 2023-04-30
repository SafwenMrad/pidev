package com.group3.camping_project.repository;

import com.group3.camping_project.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepo extends JpaRepository<Rating,Integer> {
}
