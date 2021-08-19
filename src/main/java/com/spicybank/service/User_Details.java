package com.spicybank.service;

import com.spicybank.exceptions.BusinessException;
import com.spicybank.model.User;
import com.spicybank.model.UserAccount;
import com.spicybank.model.UserTransaction;

public interface User_Details {

	public float checkBalance(long account)throws BusinessException;
	public void addBal(float amount, float prevamount, long account)throws BusinessException;
	public void txnAdd(String t_type,long account,float amount)throws BusinessException;
	public void withdrawBal(float amt, float pamt,long acc)throws BusinessException;

	public UserTransaction getTxnDetails(long acc, UserTransaction userTransaction)throws BusinessException;
	public String getUserNameByAccount(long account)throws BusinessException;
	public void moneyTransfer(float tr_amt, long acc, long tr_acc)throws BusinessException;
	public void addTransfer(float amount, float prevamount, long account, long prevacc) throws BusinessException;
	public void withdrawlTransfer(float amt, float pamt, long acc , long toacc) throws BusinessException;
	
	
	public boolean createUser(User user)throws BusinessException;
	public boolean createUserAccount(UserAccount userAccount)throws BusinessException;
	public boolean isValidUser(User user)throws BusinessException;
	public long getAccountNumber(int id)throws BusinessException;

}
