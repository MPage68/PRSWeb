package com.prs.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.prs.util.PurchaseRequestLineItem;
import com.prs.util.PurchaseRequestLineItemRepository;





@Controller
@RequestMapping("/PurchaseRequestLineItems")
public class PurchaseRequestLineItemController {
	@Autowired
	private PurchaseRequestLineItemRepository purchaseRequestLineItemRepository;

	@GetMapping("/List")
	public @ResponseBody Iterable<PurchaseRequestLineItem> getAllPurchaseRequestLineItemLineItems() {
		Iterable<PurchaseRequestLineItem> purchaseRequestLineItems = purchaseRequestLineItemRepository.findAll();
		return purchaseRequestLineItems;
	}

	@GetMapping("/Get")
	public @ResponseBody Optional<PurchaseRequestLineItem> getPurchaseRequestLineItem(@RequestParam int id) {
		Optional<PurchaseRequestLineItem> purchaseRequestLineItem = purchaseRequestLineItemRepository.findById(id);
		return purchaseRequestLineItem;
	}

	@PostMapping("/Add")
	public @ResponseBody PurchaseRequestLineItem addPurchaseRequestLineItem(@RequestBody PurchaseRequestLineItem purchaseRequestLineItem) {
		return purchaseRequestLineItemRepository.save(purchaseRequestLineItem);
	}

	@PostMapping("/Change")
	public @ResponseBody PurchaseRequestLineItem updatePurchaseRequestLineItem(@RequestBody PurchaseRequestLineItem purchaseRequestLineItem) {
		return purchaseRequestLineItemRepository.save(purchaseRequestLineItem);
	}

	@PostMapping("/Remove")
	public @ResponseBody String removePurchaseRequestLineItem(@RequestBody PurchaseRequestLineItem purchaseRequestLineItem) {
		purchaseRequestLineItemRepository.delete(purchaseRequestLineItem);
		return "PurchaseRequestLineItem removed";
	}

}
