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

import com.prs.business.product.Product;
import com.prs.business.product.ProductRepository;
import com.prs.business.user.User;
import com.prs.business.user.UserRepository;
import com.prs.util.JsonResponse;

@Controller
@RequestMapping("/Products")
public class ProductController {
	@Autowired
	private ProductRepository productRepository;

	@GetMapping("/List")
	public @ResponseBody JsonResponse getAllProducts(Product product) {
		try {
			Iterable<Product> products = productRepository.findAll();
		} catch (Exception e) {
			return JsonResponse.getErrorInstance("Product list failure:", e);
		}
		return null;
	}

	@GetMapping("/Get/{id}")
	public @ResponseBody JsonResponse getProduct(@PathVariable int id) {
		try {
			Optional<Product> product = productRepository.findById(id);

			if (product.isPresent()) {
				return JsonResponse.getInstance(product);
			} else {
				return JsonResponse.getErrorInstance("Error, product ID not found: " + id, null);
			}
		} catch (Exception e) {
			return JsonResponse.getErrorInstance("Error, product ID not valid:" + e.getMessage(), e);
		}
	}

	@PostMapping("/Add")
	public @ResponseBody JsonResponse addProduct(@RequestBody Product product) {
		return saveProduct(product);
	}

	@PostMapping("/Change")
	public @ResponseBody JsonResponse updateProduct(@RequestBody Product product) {
		return saveProduct(product);
	}

	public @ResponseBody JsonResponse saveProduct(Product product) {
		try {
			productRepository.save(product);
			return JsonResponse.getInstance(product);
		} catch (DataIntegrityViolationException e) {
			return JsonResponse.getErrorInstance(e.getRootCause().toString(), e);
		} catch (Exception e) {
			return JsonResponse.getErrorInstance(e.getMessage(), e);
		}
	}

	@PostMapping("/Remove")
	public @ResponseBody JsonResponse removeProduct(@RequestBody Product product) {
		try {
		productRepository.delete(product);
		return JsonResponse.getInstance(product);
		}
		catch(Exception e){
			return JsonResponse.getErrorInstance(e.getMessage(), e);
		}
	}
}
