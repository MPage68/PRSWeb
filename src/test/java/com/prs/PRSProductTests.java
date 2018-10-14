package com.prs;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import com.prs.business.product.Product;
import com.prs.business.product.ProductRepository;
import com.prs.business.user.User;
import com.prs.business.user.UserRepository;
import com.prs.business.vendor.Vendor;
@RunWith(SpringRunner.class)
public class PRSProductTests extends PrsWebApplicationTests {
	
	@Autowired
	private ProductRepository productRepository;

	@Test
	public void testProductCrudFunctions() {
		Iterable<Product> products = productRepository.findAll();
		assertNotNull(products);

		Product p1 = new Product();
		p1.setVendor(new Vendor() {{
			setID(1);
		}});
		p1.setPartNumber("KD-234");
		p1.setName("productName");
		p1.setPrice(2.00);
		p1.setUnit(null);
		p1.setPhotoPath(null);
		productRepository.save(p1);
		
		assertNotNull(productRepository.save(p1));
		int id = p1.getId();
		
		java.util.Optional<Product> p2 = productRepository.findById(id);
		assertEquals(p2.get().getName(),"productName");
		
		p2.get().setName("newProductName");
		assertNotNull(productRepository.save(p2.get()));
		
		productRepository.delete(p2.get());
		assertThat((productRepository.findById(id)));		
	}	
}

