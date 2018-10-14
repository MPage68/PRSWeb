package com.prs;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import com.prs.business.user.User;
import com.prs.business.user.UserRepository;
import com.prs.business.vendor.Vendor;
import com.prs.purchaserequest.PurchaseRequest;
import com.prs.purchaserequest.PurchaseRequestRepository;

@RunWith(SpringRunner.class)
public class PRSPurchaseRequestTest extends PrsWebApplicationTests {
	@Autowired
	private PurchaseRequestRepository purchaseRequestRepository;

	@Test
	public void testPurchaseRequestCrudFunctions() {
		Iterable<PurchaseRequest> purchaseRequest = purchaseRequestRepository.findAll();
		assertNotNull(purchaseRequest);

		PurchaseRequest pr1 = new PurchaseRequest();
		pr1.setUser(new User() {
			{
				setID(3);
			}
		});
		pr1.setDescription("It's a thing");
		pr1.setJustification("It's needed");
		pr1.setDateNeeded(LocalDate.now());
		pr1.setDeliveryMode("ground");
		pr1.setStatus("STATUS_REVIEW");
		pr1.setTotal(1);
		pr1.setSubmittedDate(LocalDateTime.now());
		purchaseRequestRepository.save(pr1);

		assertNotNull(purchaseRequestRepository.save(pr1));
		int id = pr1.getID();

		java.util.Optional<PurchaseRequest> pr2 = purchaseRequestRepository.findById(id);
		assertEquals(pr2.get().getDescription(), "It's a thing");

		pr2.get().setDescription("another thing");
		assertNotNull(purchaseRequestRepository.save(pr2.get()));

		purchaseRequestRepository.delete(pr2.get());
		assertThat((purchaseRequestRepository.findById(id)));
	}
}
