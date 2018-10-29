package com.prs.purchaserequest;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface PurchaseRequestRepository extends CrudRepository<PurchaseRequest, Integer> {
//List<PurchaseRequest> findAllByUserIdNotAndStatus(int iD, String status);
}
