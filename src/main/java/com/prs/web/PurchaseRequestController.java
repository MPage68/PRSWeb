package com.prs.web;

import java.time.LocalDateTime;
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
	public final String STATUS_NEW = "New";
	public final String STATUS_REVIEW = "Review";
	public final String STATUS_EDIT = "Edit";
	public final String STATUS_APPROVED = "Approved";
	public final String STATUS_REJECTED = "Rejected";

	@GetMapping("/List")
	public @ResponseBody JsonResponse getAllPurchaseRequests(PurchaseRequest purchaseRequest) {
		try {
			Iterable<PurchaseRequest> purchaseRequests = purchaseRequestRepository.findAll();
			return JsonResponse.getInstance(purchaseRequest);
		} catch (Exception e) {
			return JsonResponse.getErrorInstance("Purchase request list failure: " + e.getMessage(), e);
		}
	}

	@GetMapping("/Get/{id}")
	public @ResponseBody JsonResponse getPurchaseRequest(@PathVariable int id) {
		try {
			Optional<PurchaseRequest> purchaseRequest = purchaseRequestRepository.findById(id);

			if (purchaseRequest.isPresent()) {
				return JsonResponse.getInstance(purchaseRequest);
			} else {
				return JsonResponse.getErrorInstance("Error, purchase request ID not found: " + id, null);
			}
		} catch (Exception e) {
			return JsonResponse.getErrorInstance("Error, purchase request ID not valid" + e.getMessage(), e);
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
			purchaseRequestRepository.save(submitPurchaseRequest(purchaseRequest));
			return JsonResponse.getInstance(purchaseRequest);
		} catch (DataIntegrityViolationException e) {
			return JsonResponse.getErrorInstance(e.getRootCause().toString(), e);
		} catch (Exception e) {
			return JsonResponse.getErrorInstance(e.getMessage(), e);
		}
	}

	@PostMapping("/Remove")
	public @ResponseBody JsonResponse removePurchaseRequest(@RequestBody PurchaseRequest purchaseRequest) {
		try {
			purchaseRequestRepository.delete(purchaseRequest);
			return JsonResponse.getInstance(purchaseRequest);
		} catch (Exception e) {
			return JsonResponse.getErrorInstance(e.getMessage(), e);
		}
	}

	public PurchaseRequest submitPurchaseRequest(PurchaseRequest purchaseRequest) {
		purchaseRequest.setSubmittedDate(LocalDateTime.now());
		if (purchaseRequest.getTotal() < 50) {
			purchaseRequest.setStatus(STATUS_APPROVED);
		} else {
			purchaseRequest.setStatus(STATUS_NEW);
		}
		return purchaseRequest;
	}

	public @ResponseBody JsonResponse submitPurchaseRequestForReview(@RequestBody PurchaseRequest purchaseRequest) {
		purchaseRequest.setStatus(STATUS_REVIEW);
		return savePurchaseRequest(purchaseRequest);
	}

	public String getPurchaseRequestStatus(int id) {
		Optional<PurchaseRequest> purchaseRequest = purchaseRequestRepository.findById(id);
		return purchaseRequest.get().getStatus();

	}

	public @ResponseBody JsonResponse approvePurchaseRequest(@RequestBody PurchaseRequest purchaseRequest) {
		purchaseRequest.setStatus(STATUS_APPROVED);
		return savePurchaseRequest(purchaseRequest);
	}

	public @ResponseBody JsonResponse rejectPurchaseRequest(@RequestBody PurchaseRequest purchaseRequest) {
		purchaseRequest.setStatus(STATUS_REJECTED);
		return savePurchaseRequest(purchaseRequest);
	}
}
