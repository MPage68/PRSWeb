package com.prs.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.prs.business.product.Product;
import com.prs.purchaserequest.PurchaseRequest;
import com.prs.purchaserequest.PurchaseRequestRepository;
import com.prs.util.JsonResponse;
import com.prs.util.PurchaseRequestLineItem;
import com.prs.util.PurchaseRequestLineItemRepository;

@CrossOrigin
@Controller
@RequestMapping("/PurchaseRequestLineItems")
public class PurchaseRequestLineItemController {
	@Autowired
	private PurchaseRequestLineItemRepository purchaseRequestLineItemRepository;
	@Autowired
	private PurchaseRequestRepository purchaseRequestRepository;

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

	@GetMapping("/LinesForPurchaseRequest")
	public @ResponseBody Iterable<PurchaseRequestLineItem> getAllLinesForPurchaseRequest(@RequestParam int id) {
		return purchaseRequestLineItemRepository.findAllByPurchaseRequestId(id);

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
			updateRequestTotal(purchaseRequestLineItem);
			return JsonResponse.getInstance(purchaseRequestLineItem);
		} catch (DataIntegrityViolationException ex) {
			return JsonResponse.getErrorInstance(ex.getRootCause().toString(), ex);
		} catch (Exception ex) {
			return JsonResponse.getErrorInstance(ex.getMessage(), ex);
		}
	}

	private void updateRequestTotal(PurchaseRequestLineItem prli) {
		Optional<PurchaseRequest> prOpt = purchaseRequestRepository.findById(prli.getPurchaseRequest().getID());

		PurchaseRequest pr = prOpt.get();
		List<PurchaseRequestLineItem> lines = new ArrayList<>();
		lines = purchaseRequestLineItemRepository.findAllByPurchaseRequestId(pr.getID());
		double total = 0;
		for (PurchaseRequestLineItem line : lines) {
			Product p = line.getProduct();
			double lineTotal = line.getQuantity() * p.getPrice();
			total += lineTotal;
		}
		pr.setTotal(total);
		purchaseRequestRepository.save(pr);

	}

	@PostMapping("/Remove")
	public @ResponseBody JsonResponse removePurchaseRequestLineItem(
			@RequestBody PurchaseRequestLineItem purchaseRequestLineItem) {
		try {
			purchaseRequestLineItemRepository.delete(purchaseRequestLineItem);
			updateRequestTotal(purchaseRequestLineItem);
			return JsonResponse.getInstance(purchaseRequestLineItem);
		} catch (Exception e) {
			return JsonResponse.getErrorInstance(e.getMessage(), e);
		}
	}
}
