package com.prs.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.prs.purchaserequest.PurchaseRequest;
import com.prs.util.JsonResponse;
import com.prs.util.PurchaseRequestLineItem;
import com.prs.util.PurchaseRequestLineItemRepository;

@Controller
@RequestMapping("/PurchaseRequestLineItems")
public class PurchaseRequestLineItemController {
	@Autowired
	private PurchaseRequestLineItemRepository purchaseRequestLineItemRepository;

	@GetMapping("/List")
	public @ResponseBody JsonResponse getAllPurchaseRequestLineItemLineItems() {
		try {
			Iterable<PurchaseRequestLineItem> purchaseRequestLineItems = purchaseRequestLineItemRepository.findAll();
			return JsonResponse.getInstance(purchaseRequestLineItems);
		} catch (Exception e) {
			return JsonResponse.getErrorInstance("Purchase request line item not found: " + e.getMessage(), e);
		}
	}

	@GetMapping("/Get/{id}")
	public @ResponseBody JsonResponse getPurchaseRequestLineItem(@PathVariable int id) {
		try {
			Optional<PurchaseRequestLineItem> purchaseRequestLineItem = purchaseRequestLineItemRepository.findById(id);
			if (purchaseRequestLineItem.isPresent()) {
				return JsonResponse.getInstance(purchaseRequestLineItem);
			} else {
				return JsonResponse.getErrorInstance("Error, purchase request line item not found : " + id, null);
			}
		} catch (Exception e) {
			return JsonResponse.getErrorInstance("Error, purchase request line item not valid:" + e.getMessage(), e);
		}
	}

	@PostMapping("/Add")
	public @ResponseBody JsonResponse addPurchaseRequestLineItem(
			@RequestBody PurchaseRequestLineItem purchaseRequestLineItem) {
		return savePurchaseRequestLineItem(purchaseRequestLineItem);
	}

	@PostMapping("/Change")
	public @ResponseBody JsonResponse updatePurchaseRequestLineItem(
			@RequestBody PurchaseRequestLineItem purchaseRequestLineItem) {
		return savePurchaseRequestLineItem(purchaseRequestLineItem);
	}

	public @ResponseBody JsonResponse savePurchaseRequestLineItem(PurchaseRequestLineItem purchaseRequestLineItem) {
		try {
			purchaseRequestLineItemRepository.save(purchaseRequestLineItem);
			return JsonResponse.getInstance(purchaseRequestLineItem);
		} catch (DataIntegrityViolationException ex) {
			return JsonResponse.getErrorInstance(ex.getRootCause().toString(), ex);
		} catch (Exception ex) {
			return JsonResponse.getErrorInstance(ex.getMessage(), ex);
		}
	}

	@PostMapping("/Remove")
	public @ResponseBody JsonResponse removePurchaseRequestLineItem(
			@RequestBody PurchaseRequestLineItem purchaseRequestLineItem) {
		try {
			purchaseRequestLineItemRepository.delete(purchaseRequestLineItem);
			return JsonResponse.getInstance(purchaseRequestLineItem);
		} catch (Exception e) {
			return JsonResponse.getErrorInstance(e.getMessage(), e);
		}
	}
}
