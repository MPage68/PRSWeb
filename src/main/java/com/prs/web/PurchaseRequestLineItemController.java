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
import com.prs.business.product.ProductRepository;
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
	@Autowired
	private ProductRepository productRepository;

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
				return JsonResponse.getErrorInstance("Error, purchase request line item not found : " + id);
			}
		} catch (Exception e) {
			return JsonResponse.getErrorInstance("Error, purchase request line item not valid:" + e.getMessage(), e);
		}
	}

	@GetMapping("/LinesForPurchaseRequest")
	public @ResponseBody JsonResponse getAllLinesForPurchaseRequest(@RequestParam int id) {
		try {
			return JsonResponse.getInstance(purchaseRequestLineItemRepository.findAllByPurchaseRequestId(id));
		}
		catch(Exception e){
			return JsonResponse.getErrorInstance("Error getting line items:"+id+"Error msg:" + e.getMessage(), e);
		}
	}
	
	/*@PostMapping("/Add")
	public @ResponseBody JsonResponse addPurchaseRequestLineItem(@RequestBody PurchaseRequestLineItem purchaseRequestLineItem) {
		return savePurchaseRequestLineItem(purchaseRequestLineItem);*/
	
	@PostMapping(path="/Add")
	public @ResponseBody JsonResponse addNewPurchaseRequestLineItem (@RequestBody PurchaseRequestLineItem purchaseRequestLineItem) {
		
		JsonResponse ret = null;
		try {
			if (purchaseRequestLineItem.getProduct().getName().equals("")) {				
				Product prod = productRepository.findById(purchaseRequestLineItem.getProduct().getId()).get();
				purchaseRequestLineItem.setProduct(prod);
			}
			ret = savePurchaseRequestLineItem(purchaseRequestLineItem);		
			if (!ret.getMessage().equals(JsonResponse.SUCCESS)) {
				ret = JsonResponse.getInstance("Failed to ADD prli.  Potential data corruption issue - purchaseRequestID = "+purchaseRequestLineItem.getPurchaseRequest().getID());
			}
			else {				
				PurchaseRequest pr = updateRequestTotal((PurchaseRequestLineItem)ret.getData());
				ret = JsonResponse.getInstance(pr);
			}
		}
		catch (Exception e) {
			String msg = "Add prli issue:  " + e.getMessage();
			e.printStackTrace();
			ret = JsonResponse.getErrorInstance(purchaseRequestLineItem, msg);
		}
		return ret;
	}	

	@PostMapping("/Change")
	public @ResponseBody JsonResponse updatePurchaseRequestLineItem(@RequestBody PurchaseRequestLineItem purchaseRequestLineItem) {
		JsonResponse ret = null;
		try {
			ret = savePurchaseRequestLineItem(purchaseRequestLineItem);		
			if (!ret.getMessage().equals(JsonResponse.SUCCESS)) {
				ret = JsonResponse.getErrorInstance("Failed to UPDATE prli.  Potential data corruption issue - purchaseRequestID = "+purchaseRequestLineItem.getPurchaseRequest().getID());
			}
		}
		catch (Exception e) {
			String msg = "UPDATE prli issue:  " + e.getMessage();
			e.printStackTrace();
			ret = JsonResponse.getErrorInstance(purchaseRequestLineItem, msg);
		}
		return ret;
	}

	@PostMapping("/Remove")
	public @ResponseBody JsonResponse removePurchaseRequestLineItem(@RequestBody PurchaseRequestLineItem purchaseRequestLineItem) {
		JsonResponse ret = null;
		try {
			purchaseRequestLineItemRepository.delete(purchaseRequestLineItem);
			ret = JsonResponse.getInstance(updateRequestTotal(purchaseRequestLineItem));
		}
		catch (Exception e) {
			String msg = "DELETE prli issue:  " + e.getMessage();
			e.printStackTrace();
			ret = JsonResponse.getErrorInstance(purchaseRequestLineItem, msg);
		}
		return ret;
	}

	private @ResponseBody JsonResponse savePurchaseRequestLineItem(@RequestBody PurchaseRequestLineItem purchaseRequestLineItem) {
		try {
			purchaseRequestLineItem = purchaseRequestLineItemRepository.saveAndFlush(purchaseRequestLineItem);
			return JsonResponse.getInstance(purchaseRequestLineItem);
		} catch (DataIntegrityViolationException ex) {
			return JsonResponse.getErrorInstance(ex.getRootCause().toString(), ex);
		} catch (Exception ex) {
			return JsonResponse.getErrorInstance(ex.getMessage(), ex);
		}
	}
	
	private PurchaseRequest updateRequestTotal(PurchaseRequestLineItem purchaseRequestLineItem) throws Exception {	
		
		Optional<PurchaseRequest> prOpt = purchaseRequestRepository.findById(purchaseRequestLineItem.getPurchaseRequest().getID());		
		PurchaseRequest purchaseRequest = prOpt.get();
		
		List<PurchaseRequestLineItem> lines = new ArrayList<>();
		lines = purchaseRequestLineItemRepository.findAllByPurchaseRequestId(purchaseRequest.getID());
		
		double sum = 0;
		for (PurchaseRequestLineItem line: lines) {
			Product p = line.getProduct();
			double lineTotal = line.getQuantity()*p.getPrice();
			sum += lineTotal;
		}
		purchaseRequest.setTotal(sum);
		return purchaseRequestRepository.save(purchaseRequest);	
	}

}