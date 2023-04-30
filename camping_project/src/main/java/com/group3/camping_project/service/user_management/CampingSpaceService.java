package com.group3.camping_project.service.user_management;

import com.group3.camping_project.entities.CampingSpace;
import com.group3.camping_project.repository.ICampingSpaceRepo;
import com.group3.camping_project.service.ImpCampingSpace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CampingSpaceService implements ImpCampingSpace {

@Autowired
 ICampingSpaceRepo camprepo;

    public CampingSpace addCampingSpace(CampingSpace cms) {
        return camprepo.save(cms);
    }

    @Override
    public CampingSpace updateCampingSpace(CampingSpace cms, Integer idCampingSpace) {
        return camprepo.save(cms);
    }

    @Override
    public void deleteCampingSpace(Integer id) {
     camprepo.deleteById(id);
    }

    @Override
    public List<CampingSpace> getAllCampingSpace() {
        return camprepo.findAll();
    }
}
