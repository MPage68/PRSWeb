package com.prs;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import com.prs.business.user.User;
import com.prs.business.user.UserRepository;
import com.prs.business.vendor.Vendor;
import com.prs.business.vendor.VendorRepository;

@RunWith(SpringRunner.class)
public class PRSVendorTests extends PrsWebApplicationTests{
	@Autowired
	private VendorRepository vendorRepository;

	@Test
	public void testVendorCrudFunctions() {
		Iterable<Vendor> vendors = vendorRepository.findAll();
		assertNotNull(vendors);

		Vendor v1 = new Vendor();	
	
		v1.setCode("cdoe");
		v1.setName("vendorName");
		v1.setAddress("add");
		v1.setCity("city");
		v1.setState("st");
		v1.setZip("23456");
		v1.setPhoneNumber("555-555-5555");
		v1.setEmail("mike@live.com");
		v1.setPreApproved(true);
		vendorRepository.save(v1);
		assertNotNull(vendorRepository.save(v1));
		int id = v1.getID();
		
		java.util.Optional<Vendor> v2 = vendorRepository.findById(id);
		assertEquals(v2.get().getName(),"vendorName");
		
		v2.get().setName("newVendorName");
		assertNotNull(vendorRepository.save(v2.get()));
		
		vendorRepository.delete(v2.get());
		assertThat((vendorRepository.findById(id)));		
	}	
}
