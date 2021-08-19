package com.spicybank.service.Impl;

import com.spicybank.service.Emp_Details;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.spicybank.DAO.Spicybank_DAO;
import com.spicybank.DAO.Impl.Spicybank_DAOImpl;
import com.spicybank.exceptions.BusinessException;
import com.spicybank.model.Emp;
import com.spicybank.model.UserAccount;
import com.spicybank.model.UserTransaction;

public class Emp_DetailsImpl implements Emp_Details {
	private static Logger log = Logger.getLogger(Emp_DetailsImpl.class);
	Spicybank_DAO spicybank_DAO = new Spicybank_DAOImpl();
	


	@Override
	public boolean checkUserAccount(long accno) throws BusinessException {

		try {
		return spicybank_DAO.checkUserAccount(accno);
		}
		catch(BusinessException e) {
			log.warn(e.getMessage());
		}
		return false;
	}


	@Override
	public void deleteCustomerAccount(long account) throws BusinessException {
		try {
			spicybank_DAO.deleteCustomerAccount(account);
		}
		catch (BusinessException e) {
			log.warn(e.getMessage());
		}
	}

	@Override
	public int getUserIdByAccount(long account) throws BusinessException {
		try {
			return spicybank_DAO.getUserIdByAccount(account);
		}
		catch(BusinessException e) {
			log.warn(e.getMessage());
		}
		return 0;
}

	@Override
	public List<UserAccount> getAllCustomerDetails() throws BusinessException {
		
		try {
			return spicybank_DAO.getAllCustomerDetails();
		}
		catch(BusinessException e) {
			log.warn(e.getMessage());
		}
		return null;
	}

	@Override
	public UserAccount getCustomerByName(String name) throws BusinessException {
		try {
			return spicybank_DAO.getCustomerByName(name);
		}
		catch(BusinessException e) {
			log.warn(e.getMessage());
		}
		return null;
	}

	@Override
	public UserAccount getCustomerById(int userid) throws BusinessException {
		
		try {
			return spicybank_DAO.getCustomerById(userid);
		}
		catch(BusinessException e) {
			log.warn(e.getMessage());
		}
		return null;
	}

	@Override
	public UserAccount getCustomerByAccount(long account) throws BusinessException {
		try {
			return spicybank_DAO.getCustomerByAccount(account);
		}
		catch(BusinessException e) {
			log.warn(e.getMessage());
		}
		return null;
	}

	@Override
	public UserAccount getCustomerByPan(String pan) throws BusinessException {
		try {
			return spicybank_DAO.getCustomerByPan(pan);
		}
		catch(BusinessException e) {
			log.warn(e.getMessage());
		}
		return null;
		
	}

	@Override
	public List<UserAccount> getCustomerByState(String state) throws BusinessException {
		try {
			return spicybank_DAO.getCustomerByState(state);
		}
		catch(BusinessException e) {
			log.warn(e.getMessage());
		}
		return null;
	}

	@Override
	public List<UserTransaction> getAllTransactions() throws BusinessException {
		try {
			return spicybank_DAO.getAllTransactions();
		}
		catch(BusinessException e) {
			log.warn(e.getMessage());
		}
		return null;
	}

	@Override
	public List<UserTransaction> getTransactionsByAccount(long account) throws BusinessException {
		try {
			return spicybank_DAO.getTransactionsByAccount(account);
		}
		catch(BusinessException e) {
			log.warn(e.getMessage());
		}
		return null;
	}


	@Override
	public boolean isValidEmp(Emp emp) throws BusinessException {
		try {
			return spicybank_DAO.isValidEmp(emp);
		}
		catch(BusinessException e) {
			log.warn(e.getMessage());
		}
		return false;
	}
}
