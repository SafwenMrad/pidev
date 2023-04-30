package com.group3.camping_project.entities;

import com.group3.camping_project.entities.enums.CampingType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
    
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CampingSpace implements Serializable {
@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id ;
    private  String name ;
    private  float price ;
    private  String location ;
    private  String email ;
    private  String phoneNumber ;
    private int LikesNumber;

    @Enumerated(EnumType.STRING)
    private CampingType campingType;
    private String description ;
    private Date CreatedAt ;
    private Date UpdatedAt ;

    @OneToOne
    private Image image ;
    @OneToOne
    private  Video video ;

    @OneToMany(mappedBy = "campingSpace")
    private List<Review> review;

    @OneToMany(mappedBy = "campingSpace", cascade = CascadeType.ALL)
    private List<Rating> ratings;

}
