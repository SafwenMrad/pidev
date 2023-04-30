package com.group3.camping_project.service;

import com.group3.camping_project.entities.CampingSpace;

import java.util.List;

public interface ImpCampingSpace {
    public CampingSpace addCampingSpace(CampingSpace cms    ) ;
    public CampingSpace updateCampingSpace(CampingSpace cms ,Integer idCampingSpace);
    void deleteCampingSpace (Integer id);
    List<CampingSpace> getAllCampingSpace();
}
