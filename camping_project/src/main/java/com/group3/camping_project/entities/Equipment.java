package com.group3.camping_project.entities;

import com.group3.camping_project.entities.enums.EquipmentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Equipment implements Serializable {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id ;

   private String name;

   private float price;

   private Date createdAt ;

   private int quantity ;

   private String description;

   private EquipmentType equipmentType ;

   @ManyToOne
   private User owner;

   @OneToOne
   private Image image ;

}