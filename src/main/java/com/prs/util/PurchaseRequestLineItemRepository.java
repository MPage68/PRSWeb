package com.prs.util;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface PurchaseRequestLineItemRepository extends CrudRepository<PurchaseRequestLineItem, Integer> {

	List<PurchaseRequestLineItem> findAllByPurchaseRequestId(int id);

}
