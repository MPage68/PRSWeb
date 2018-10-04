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
import org.springframework.web.bind.annotation.ResponseBody;

import com.prs.purchaserequest.PurchaseRequest;
import com.prs.purchaserequest.PurchaseRequestRepository;
import com.prs.util.JsonResponse;

@Controller
@RequestMapping("/PurchaseRequests")
public class PurchaseRequestController {
	@Autowired
	private PurchaseRequestRepository purchaseRequestRepository;

	@GetMapping("/List")
	public @ResponseBody JsonResponse getAllPurchaseRequests(PurchaseRequest purchaseRequest) {
		try {
			Iterable<PurchaseRequest> purchaseRequests = purchaseRequestRepository.findAll();
			return JsonResponse.getInstance(purchaseRequest);
		} catch (Exception e) {
			return JsonResponse.getErrorInstance("Purchase Request list empty: " + e.getMessage(), e);
		}
	}

	@GetMapping("/Get/{id}")
	public @ResponseBody JsonResponse getPurchaseRequest(@PathVariable int id) {
		try {
			Optional<PurchaseRequest> purchaseRequest = purchaseRequestRepository.findById(id);

			if (purchaseRequest.isPresent()) {
				return JsonResponse.getInstance(purchaseRequest);
			} else {
				return JsonResponse.getErrorInstance("Error, purchase request id not valid: " + id, null);
			}
		} catch (Exception e) {
			return JsonResponse.getErrorInstance("Error, " + e.getMessage(), e);
		}
	}

	@PostMapping("/Add")
	public @ResponseBody JsonResponse addPurchaseRequest(@RequestBody PurchaseRequest purchaseRequest) {
		return savePurchaseRequest(purchaseRequest);
	}

	@PostMapping("/Change")
	public @ResponseBody JsonResponse updatePurchaseRequest(@RequestBody PurchaseRequest purchaseRequest) {
		return savePurchaseRequest(purchaseRequest);
	}

	public @ResponseBody JsonResponse savePurchaseRequest(PurchaseRequest purchaseRequest) {
		try {
			purchaseRequestRepository.save(purchaseRequest);
			return JsonResponse.getInstance(purchaseRequest);
		} catch (DataIntegrityViolationException ex) {
			return JsonResponse.getErrorInstance(ex.getRootCause().toString(), ex);
		} catch (Exception ex) {
			return JsonResponse.getErrorInstance(ex.getMessage(), ex);
		}
	}

	@PostMapping("/Remove")
	public @ResponseBody JsonResponse removePurchaseRequest(@RequestBody PurchaseRequest purchaseRequest) {
		try {
			purchaseRequestRepository.delete(purchaseRequest);
			return JsonResponse.getInstance(purchaseRequest);
		} catch (Exception e) {
			return JsonResponse.getErrorInstance("Error, :" + e.getMessage(), e);
		}
	}

}
