package com.group3.camping_project.repository;

import com.group3.camping_project.entities.ChargeRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IReposPayment extends JpaRepository<ChargeRequest,Integer> {
}
