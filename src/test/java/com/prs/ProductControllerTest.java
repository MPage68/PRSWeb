package com.prs;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import com.prs.business.product.Product;
import com.prs.business.product.ProductRepository;
import com.prs.business.user.User;
import com.prs.business.vendor.Vendor;
import com.prs.util.JsonResponse;
import com.prs.web.ProductController;

@RunWith(SpringRunner.class)
public class ProductControllerTest extends PrsWebApplicationTests  {
 
	@Autowired
	private ProductController productController;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Before
	public void setUpTests() {
		Product p1 = new Product();
		p1.setVendor(new Vendor() {{
			setID(1);
		}});
		p1.setPrice(1.00);
		p1.setName("Thing");
		p1.setPartNumber("12123");
		productRepository.save(p1);
	}
	@Test
	public void getAllProducts_returnsValues_whenCalled() {
		JsonResponse productsResponse = productController.getAllProducts();
		assertNotNull(productsResponse.getData());
		assertEquals(productsResponse.getMessage(), JsonResponse.SUCCESS);
		assertEquals(productsResponse.getCode(), 0);
		assertNull(productsResponse.getError());
		
	}
	
@Test 
public void getProductByID_returnsID_whenIDIsPresent() {
	JsonResponse productsResponse = productController.getProduct(1);
	assertNotNull(productsResponse.getData());
	@SuppressWarnings("unchecked")
	Optional<Product> product = (Optional<Product>) productsResponse.getData();
	assertEquals(Product.class, product.get());
	
	
}

}
