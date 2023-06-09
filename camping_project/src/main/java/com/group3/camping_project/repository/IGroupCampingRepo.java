package com.group3.camping_project.repository;

import com.group3.camping_project.entities.GroupCamping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IGroupCampingRepo extends JpaRepository<GroupCamping,Integer> {

    List<GroupCamping> findByDestination(String destination);
}
