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

import com.prs.business.vendor.Vendor;
import com.prs.business.vendor.VendorRepository;
import com.prs.util.JsonResponse;

@Controller
@RequestMapping("/Vendor")
public class VendorController {

	@Autowired
	private VendorRepository vendorRepository;

	@GetMapping("/List")
	public @ResponseBody JsonResponse getAllVenders() {
		try {
			return JsonResponse.getInstance(vendorRepository.findAll());
		} catch (Exception e) {
			return JsonResponse.getErrorInstance("Error, Vendor list failure:" + e.getMessage(), e);
		}
	}

	@GetMapping("/Get/{id}")
	public @ResponseBody JsonResponse getVenders(@PathVariable int id) {
		try {
			Optional<Vendor> vendor = vendorRepository.findById(id);
			if (vendor.isPresent()) {
				return JsonResponse.getInstance(vendor);
			} else {
				return JsonResponse.getErrorInstance("Error, Vendor ID not found:" + id, null);
			}
		} catch (Exception e) {
			return JsonResponse.getErrorInstance("Error, Vendor ID not valid:" + e.getMessage(), e);
		}
	}

	@PostMapping("/Add")
	public @ResponseBody JsonResponse addVender(@RequestBody Vendor vendor) {
		return saveVendor(vendor);
	}

	@PostMapping("/Change")
	public @ResponseBody JsonResponse updateVendor(@RequestBody Vendor vendor) {
		return saveVendor(vendor);
	}
	
	public @ResponseBody JsonResponse saveVendor(@RequestBody Vendor vendor) {
		try {
			vendorRepository.save(vendor);
			return JsonResponse.getInstance(vendor);
		} catch (DataIntegrityViolationException e) {
			return JsonResponse.getErrorInstance(e.getRootCause().toString(), e);
		} catch (Exception e) {
			return JsonResponse.getErrorInstance(e.getMessage(), e);
		}
	}

	@PostMapping("/Remove")
	public @ResponseBody JsonResponse removeVendor(@RequestBody Vendor vendor) {
		try {
			vendorRepository.delete(vendor);
			return JsonResponse.getInstance(vendor);
		} catch (Exception e) {
			return JsonResponse.getErrorInstance(e.getMessage(), e);
		}
	}
}
