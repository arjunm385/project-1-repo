package com.spicybank.service;

import java.util.List;

import com.spicybank.exceptions.BusinessException;
import com.spicybank.model.Emp;
import com.spicybank.model.UserAccount;
import com.spicybank.model.UserTransaction;

public interface Emp_Details {
	

	public boolean checkUserAccount(long accno)throws BusinessException;
	public void deleteCustomerAccount(long account)throws BusinessException;
	public int getUserIdByAccount(long account)throws BusinessException;
	
	
	
	public List<UserAccount> getAllCustomerDetails()throws BusinessException;
	public UserAccount getCustomerByName(String name) throws BusinessException;
	public UserAccount getCustomerById(int userid)throws BusinessException;
	public UserAccount getCustomerByAccount(long account)throws BusinessException;
	public UserAccount getCustomerByPan(String pan)throws BusinessException;
	public List<UserAccount> getCustomerByState(String state)throws BusinessException;
	public List<UserTransaction> getAllTransactions()throws BusinessException;
	public List<UserTransaction> getTransactionsByAccount(long account)throws BusinessException;
	public boolean isValidEmp(Emp emp)throws BusinessException;

}
