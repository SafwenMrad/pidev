package com.group3.camping_project.service.equipement_service;

import com.group3.camping_project.entities.ChargeRequest;

import java.util.List;

public interface ImpPayment {

    ChargeRequest ajouter(ChargeRequest chargeRequest);
    List<ChargeRequest> requestList();
}
