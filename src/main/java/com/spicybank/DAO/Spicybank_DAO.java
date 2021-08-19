package com.spicybank.DAO;

import java.util.List;

import com.spicybank.exceptions.BusinessException;
import com.spicybank.model.Emp;
import com.spicybank.model.User;
import com.spicybank.model.UserAccount;
import com.spicybank.model.UserTransaction;

public interface Spicybank_DAO {
	
	public boolean checkUserAccount(long accno)throws BusinessException;
	public void deleteCustomerAccount(long account)throws BusinessException;
	public int getUserIdByAccount(long account)throws BusinessException;
	
	
	public List<UserAccount> getAllCustomerDetails() throws BusinessException;
	public UserAccount getCustomerByName(String name) throws BusinessException;
	public UserAccount getCustomerById(int userid)throws BusinessException;
	public UserAccount getCustomerByAccount(long account)throws BusinessException;
	public UserAccount getCustomerByPan(String pan)throws BusinessException;
	public List<UserAccount> getCustomerByState(String state)throws BusinessException;
	public List<UserTransaction> getAllTransactions()throws BusinessException;
	public List<UserTransaction> getTransactionsByAccount(long account)throws BusinessException;
	public boolean isValidEmp(Emp emp)throws BusinessException;
	
	
	

	public long getAccountNumber(int id)throws BusinessException;
	
	

	
	public UserTransaction getTxnDetails(long acc, UserTransaction userTransaction)throws BusinessException;
	public String getUserNameByAccount(long account)throws BusinessException;
	public void moneyTransfer(float tr_amt, long acc, long tr_acc)throws BusinessException;
	void addTransfer(float amount, float prevamount, long account, long prevacc) throws BusinessException;
	void withdrawlTransfer(float amt, float pamt, long acc , long toacc) throws BusinessException;
	
	
	public boolean createUser(User user)throws BusinessException;
	public boolean createUserAccount(UserAccount userAccount)throws BusinessException;
	public boolean isValidUser(User user)throws BusinessException;
	public void txnAdd(String t_type,long account,float amount)throws BusinessException;
	public float checkBalance(long account)throws BusinessException;
	public void addBal(float amount, float prevamount, long account)throws BusinessException;
	public void withdrawBal(float amt, float pamt,long acc)throws BusinessException;
	
}
