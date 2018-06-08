package com.cg.mypaymentapp.test;
import java.math.BigDecimal;

import junit.framework.Assert;

import org.junit.Test;

import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.exception.InsufficientBalanceException;
import com.cg.mypaymentapp.exception.InvalidInputException;
import com.cg.mypaymentapp.service.WalletService;
import com.cg.mypaymentapp.service.WalletServiceImpl;


public class TestClass {

	WalletService service;
	@Test(expected=InvalidInputException.class)
	public void testCreateAccountForNullName(){
		 service =new WalletServiceImpl();
		BigDecimal bg= new BigDecimal(34099);
		Customer customer =service.createAccount(null, "9824383129",bg);
	}
	
	@Test(expected=InvalidInputException.class)
	public void testCreateAccountForNullMobileNo(){
		 service =new WalletServiceImpl();
		BigDecimal bg= new BigDecimal(34099);
		Customer customer =service.createAccount("Yogini",null,bg);
	}
	
	@Test(expected=InvalidInputException.class)
	public void testCreateAccountForNegativeAmount(){
		 service =new WalletServiceImpl();
		BigDecimal bg= new BigDecimal(-34099);
		Customer customer =service.createAccount("Yogini", "9823831294",bg);
	}
	@Test(expected=InvalidInputException.class)
	public void testCreateAccountForInvalidMobileNo(){
		 service =new WalletServiceImpl();
		BigDecimal bg= new BigDecimal(34099);
		Customer customer =service.createAccount("Yogini", "982383129",bg);
	}
	@Test
	public void testCreateAccountForCorrectData(){
		 service =new WalletServiceImpl();
		BigDecimal bg= new BigDecimal(34099);
		Customer customer =service.createAccount("Yogini", "9823823129",bg);
		Assert.assertEquals("Yogini", customer.getName());
		Assert.assertEquals("9823823129", customer.getName());
		Assert.assertEquals(bg, customer.getWallet().getBalance());
		
	}
	
	public void testShowBalanceForNullMobileNo(){
		 service =new WalletServiceImpl();
		Customer customer =service.showBalance(null);
	}

	@Test(expected=InvalidInputException.class)
	public void testShowBalanceForInvalidMobileNo(){
		 service =new WalletServiceImpl();
		Customer customer =service.showBalance("9354332");
	}
	
	@Test
	public void testShowBalanceForValidMobileNo(){
		 service =new WalletServiceImpl();
		Customer customer =service.showBalance("9802331234");
		Assert.assertEquals("9802331234", customer.getMobileno());
		Assert.assertEquals(90000, customer.getWallet().getBalance());
		
	}
	@Test(expected=InvalidInputException.class)
	public void testFundTransferForNullData(){
		 service =new WalletServiceImpl();
		Customer customer=service.fundTransfer(null, null, null);

	}
	@Test(expected=InvalidInputException.class)
	public void testFundTransferForNullSourceMobileNo(){
		 service =new WalletServiceImpl();
		BigDecimal bg= new BigDecimal(34620);
			Customer customer=service.fundTransfer(null, "9829329112", bg);
	}
	@Test(expected=InvalidInputException.class)
	public void testFundTransferForInvalidMobileNoData(){
		service =new WalletServiceImpl();
		BigDecimal bg= new BigDecimal(34620);
		Customer customer=service.fundTransfer("901230", "921993211", bg);
		
	}
	@Test(expected=InvalidInputException.class)
	public void testFundTransferForInvalidAmount(){
		service =new WalletServiceImpl();
		BigDecimal bg= new BigDecimal(-34620.99);
		Customer customer=service.fundTransfer("9030201234", "9002012345", bg);

	}
	//given mobile number does not exist in DB.
	@Test(expected=InvalidInputException.class)
	public void testDepositAmountForInvalidMobileNumber(){
		service= new WalletServiceImpl();
		BigDecimal bg= new BigDecimal(9000);
		Customer customer=service.depositAmount("9020320132", bg);
	}
	@Test(expected=InvalidInputException.class)
	public void testDepositAmountForNegativeAmount(){
		service= new WalletServiceImpl();
		BigDecimal bg= new BigDecimal(-9000);
		Customer customer=service.depositAmount("9020320132", bg);
	}
	@Test(expected=InvalidInputException.class)
	public void testDepositAmountForZero(){
		service= new WalletServiceImpl();
		BigDecimal bg= new BigDecimal(0);
		Customer customer =service.depositAmount("9012013953", bg);
	}

	@Test 
	public void testDepositAmountForValidAmount(){
		service= new WalletServiceImpl();
		Customer cust= service.showBalance("9012013953");
		int oldBal=cust.getWallet().getBalance().intValue();
		BigDecimal bg= new BigDecimal(4500);
		BigDecimal oldBalance= new BigDecimal(oldBal);
		Customer customer =service.depositAmount("9012013953", bg);
		Assert.assertEquals(customer.getWallet().getBalance(),oldBalance.add(bg));
	}
	
	/// for Withdraw method
	//given mobile number does not exist in DB.
		@Test(expected=InvalidInputException.class)
		public void testWithdrawAmountForInvalidMobileNumber(){
			service= new WalletServiceImpl();
			BigDecimal bg= new BigDecimal(9000);
			Customer customer=service.withdrawAmount("9020320132", bg);
		}
		@Test(expected=InvalidInputException.class)
		public void testWithdrawAmountForNegativeAmount(){
			service= new WalletServiceImpl();
			BigDecimal bg= new BigDecimal(-9000);
			Customer customer=service.withdrawAmount("9020320132", bg);
		}
		@Test(expected=InvalidInputException.class)
		public void testWithdrawAmountForZero(){
			service= new WalletServiceImpl();
			BigDecimal bg= new BigDecimal(0);
			Customer customer =service.withdrawAmount("9012013953", bg);
		}

		@Test 
		public void testWithdrawAmountForValidAmount(){
			service= new WalletServiceImpl();
			Customer cust= service.showBalance("9012013953");
			int oldBal=cust.getWallet().getBalance().intValue();
			BigDecimal bg= new BigDecimal(4500);
			BigDecimal oldBalance= new BigDecimal(oldBal);
			Customer customer =service.withdrawAmount("9012013953", bg);
			Assert.assertEquals(customer.getWallet().getBalance(),oldBalance.subtract(bg));
		}

		@Test(expected=InsufficientBalanceException.class)
		public void testWithdrawAmountForLowBalance(){
			service= new WalletServiceImpl();
			Customer cust= service.showBalance("9012013953");
			BigDecimal bg= new BigDecimal(cust.getWallet().getBalance().add(new BigDecimal(15000)).intValue());
			Customer customer =service.withdrawAmount("9012013953", bg);
		}

		

}
