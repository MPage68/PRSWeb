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
			return JsonResponse.getErrorInstance("No products in list:", e);
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
				return JsonResponse.getErrorInstance("Incorrect product: " + id, null);
			}
		} catch (Exception e) {
			return JsonResponse.getErrorInstance("Error, id not valid:" + e.getMessage(), e);
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
		} catch (DataIntegrityViolationException ex) {
			return JsonResponse.getErrorInstance(ex.getRootCause().toString(), ex);
		} catch (Exception ex) {
			return JsonResponse.getErrorInstance(ex.getMessage(), ex);
		}

	}

	@PostMapping("/Remove")
	public @ResponseBody JsonResponse removeProduct(@RequestBody Product product) {
		try {
		productRepository.delete(product);
		return JsonResponse.getInstance(product);
		}
		catch(Exception e){
			return JsonResponse.getErrorInstance("Product not deleted: " + e.getMessage(), e);
		}
	}

}
