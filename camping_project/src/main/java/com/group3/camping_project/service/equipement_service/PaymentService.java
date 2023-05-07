package com.group3.camping_project.service.equipement_service;

import com.group3.camping_project.entities.ChargeRequest;
import com.group3.camping_project.repository.IReposPayment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService implements ImpPayment{
    @Autowired
    IReposPayment iReposPayment;
    @Override
    public ChargeRequest ajouter(ChargeRequest chargerequest) {
        return iReposPayment.save(chargerequest);
    }
    @Override
    public List<ChargeRequest> requestList(){
        return iReposPayment.findAll();
    }
}
