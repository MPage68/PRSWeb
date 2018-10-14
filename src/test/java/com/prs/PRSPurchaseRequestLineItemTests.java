package com.prs;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import com.prs.business.product.Product;
import com.prs.business.user.User;
import com.prs.business.user.UserRepository;
import com.prs.purchaserequest.PurchaseRequest;
import com.prs.util.PurchaseRequestLineItem;
import com.prs.util.PurchaseRequestLineItemRepository;

@RunWith(SpringRunner.class)
public class PRSPurchaseRequestLineItemTests extends PrsWebApplicationTests{
	@Autowired
	private PurchaseRequestLineItemRepository purchaseRequestLineItemRepository;

	@Test
	public void testPurchaseRequestLineItemCrudFunctions() {
		Iterable<PurchaseRequestLineItem> purchaseRequestLineItems = purchaseRequestLineItemRepository.findAll();
		assertNotNull(purchaseRequestLineItems);

		PurchaseRequestLineItem prli1 = new PurchaseRequestLineItem();
		prli1.setPurchaseRequest(new PurchaseRequest() {{ 
			setID(3);
		}});
		prli1.setProduct(new Product() {{
		setId(9);
		}});
		prli1.setQuantity(1);
		purchaseRequestLineItemRepository.save(prli1);		
		
		assertNotNull(purchaseRequestLineItemRepository.save(prli1));
		int id = prli1.getId();
		
		java.util.Optional<PurchaseRequestLineItem> prli2 = purchaseRequestLineItemRepository.findById(id);
		assertEquals(prli2.get().getQuantity(), 1);
		
	prli2.get().setQuantity(2);
		assertNotNull(purchaseRequestLineItemRepository.save(prli2.get()));
		
		purchaseRequestLineItemRepository.delete(prli2.get());
		assertThat((purchaseRequestLineItemRepository.findById(id)));		
	}	
}
