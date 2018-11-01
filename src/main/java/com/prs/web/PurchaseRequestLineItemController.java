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
import com.prs.purchaseRequestLineItem.PurchaseRequestLineItem;
import com.prs.purchaseRequestLineItem.PurchaseRequestLineItemRepository;
import com.prs.purchaserequest.PurchaseRequest;
import com.prs.purchaserequest.PurchaseRequestRepository;
import com.prs.util.JsonResponse;

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

	@PostMapping(path="/Add")
	public @ResponseBody JsonResponse addNewPurchaseRequestLineItem (@RequestBody PurchaseRequestLineItem prli) {
		//Session session = 
		JsonResponse ret = null;
		try {
			if (prli.getProduct().getName().equals("")) {
				// Incoming Json is not fully qualified so we need to look up the product
				Product prod = prodRepository.findById(prli.getProduct().getId()).get();
				prli.setProduct(prod);
			}
			ret = savePurchaseRequestLineItem(prli);
			// ensure save was successful before continuing
			// if save was not successful then pr total will be corrupt
			if (!ret.getMessage().equals(JsonResponse.SUCCESS)) {
				ret = JsonResponse.getErrorInstance("Failed to ADD prli.  Potential data corruption issue - purchaseRequestID = "+prli.getPurchaseRequest().getId());
			}
			else {
				// update pr total
				PurchaseRequest pr = updateRequestTotal((PurchaseRequestLineItem)ret.getData());
				ret = JsonResponse.getInstance(pr);
			}
		}
		catch (Exception e) {
			String msg = "Add prli issue:  " + e.getMessage();
			e.printStackTrace();
			ret = JsonResponse.getErrorInstance(prli, msg);
		}
		return ret;
	}

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
