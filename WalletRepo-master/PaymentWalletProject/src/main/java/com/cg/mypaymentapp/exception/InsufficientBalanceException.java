package com.cg.mypaymentapp.exception;

public class InsufficientBalanceException extends Exception{

	public InsufficientBalanceException(String msg) {
		super(msg);
	}
}
