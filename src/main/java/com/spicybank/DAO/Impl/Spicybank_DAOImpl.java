package com.spicybank.DAO.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.spicybank.DAO.Spicybank_DAO;
import com.spicybank.dbutil.PostgresConnection;
import com.spicybank.exceptions.BusinessException;
import com.spicybank.model.Emp;
import com.spicybank.model.User;
import com.spicybank.model.UserAccount;
import com.spicybank.model.UserTransaction;
import com.spicybank.service.Impl.Emp_DetailsImpl;

public class Spicybank_DAOImpl implements Spicybank_DAO {
	private static Logger log = Logger.getLogger(Spicybank_DAOImpl.class);

	@Override
	public boolean checkUserAccount(long accno) throws BusinessException {

		boolean a = false;
		long accno1 = 0;
		try (Connection connection = PostgresConnection.getConnection()) {
			String sql = "select account from user_account where account=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, accno);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				accno1 = resultSet.getInt("account");
			}

			if (accno == accno1)
				a = true;
			else
				a = false;

		} catch (SQLException e) {
			log.warn(e.getMessage());
		} catch (ClassNotFoundException e) {
			log.warn(e.getMessage());
		}

		return a;

	}

	@Override
	public void deleteCustomerAccount(long account) throws BusinessException {
		int id = getUserIdByAccount(account);
		try (Connection connection = PostgresConnection.getConnection()) {
			String sql1 = "delete from user_details where userid =?";
			String sql2 = "delete from user_account where userid =?";
			String sql3 = "delete from transactions where account =?";
			PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
			PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
			PreparedStatement preparedStatement3 = connection.prepareStatement(sql3);
			preparedStatement1.setLong(1, id);
			preparedStatement2.setLong(1, id);
			preparedStatement3.setLong(1, account);
			preparedStatement3.executeUpdate();
			preparedStatement2.executeUpdate();
            preparedStatement1.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			log.warn(e.getMessage());
		} catch (SQLException e) {

			log.warn(e.getMessage());
		}

	}

	@Override
	public int getUserIdByAccount(long account) throws BusinessException {
		int userid = 0;
		try (Connection connection = PostgresConnection.getConnection()) {
			String sql = "select userid from user_account where account=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, account);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				userid = resultSet.getInt("userid");
			}
		} catch (ClassNotFoundException e) {
			log.warn(e.getMessage());
		} catch (SQLException e) {

			log.warn(e.getMessage());
		}

		return userid;
	}
	
	
	//empimpl end 
	//userimpl start


	@Override
	public float checkBalance(long account) throws BusinessException {
		float bal = 0;
		try (Connection connection = PostgresConnection.getConnection()) {
			String sql = "select balance from user_account where account=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, account);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next())
			 {
			  bal=resultSet.getFloat("balance");
			 }
		}
		catch (ClassNotFoundException e) {
			log.warn(e.getMessage());
		} catch (SQLException e) {
			log.warn(e.getMessage());
		}
		return bal;
	}

	@Override
	public long getAccountNumber(int id) throws BusinessException {
		
		long account = 0;
		
		try (Connection connection = PostgresConnection.getConnection()) {
			String sql = "select account from user_account where userid=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next())
			 {
			  account=resultSet.getLong("account");
			 }
		}
		catch (ClassNotFoundException e) {
			log.warn(e.getMessage());
		} catch (SQLException e) {
			log.warn(e.getMessage());
		}
		return account;
		
	}

	@Override
	public void addBal(float amount, float prevamount, long account) throws BusinessException {

		String t_type="deposited";
		txnAdd(t_type, account, amount);
		float tamt = amount+prevamount;
		try (Connection connection = PostgresConnection.getConnection()) 
		{
			String sql = "update user_account set balance=? where account=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setFloat(1, tamt);
			preparedStatement.setLong(2, account);
			preparedStatement.executeUpdate();
		}
		catch (ClassNotFoundException e) {
			log.warn(e.getMessage());
		} catch (SQLException e) {
			log.warn(e.getMessage());
		}
		
		
	}

	
	@Override
	public void withdrawBal(float amt, float pamt, long acc) throws BusinessException {

		
		String t_type="Withdrawl";
		txnAdd(t_type, acc, amt);
		float tamt = pamt-amt;
		try (Connection connection = PostgresConnection.getConnection()) 
		{
			String sql = "update user_account set balance=? where account=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setFloat(1, tamt);
			preparedStatement.setLong(2, acc);
			preparedStatement.executeUpdate();
		}
		catch (ClassNotFoundException e) {
			log.warn(e.getMessage());
		} catch (SQLException e) {
			log.warn(e.getMessage());
		}
		
	}
	@Override
	public void txnAdd(String t_type, long account, float amount) throws BusinessException {
		
		try (Connection connection = PostgresConnection.getConnection()) {
			String sql = "insert into transactions(account,amt,transactiontype) values(?,?,?)";

			PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setLong(1,account);
			preparedStatement.setFloat(2, amount);
			preparedStatement.setString(3,t_type);
			preparedStatement.executeUpdate();
		
	}
		catch (ClassNotFoundException e) {
			log.warn(e.getMessage());
		} catch (SQLException e) {
			log.warn(e.getMessage());
		}
	}


	@Override
	public UserTransaction getTxnDetails(long acc , UserTransaction userTransaction) throws BusinessException {
      
		
		try (Connection connection = PostgresConnection.getConnection()) {
			String sql = "select transactionid,transactiontype,amt from transactions where account=? ";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1,acc);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next())
			 {
				
				userTransaction.setTransactionid(resultSet.getLong("transactionid"));
				userTransaction.setTransactiontype(resultSet.getString("transactiontype"));
				userTransaction.setAmt(resultSet.getFloat("amt"));
				System.out.println("transaction id: "+userTransaction.getTransactionid()+" transaction type: "+userTransaction.getTransactiontype()+" transaction amount: "+userTransaction.getAmt());
				}

		
	}
		catch (ClassNotFoundException e) {
			log.warn(e.getMessage());
		} catch (SQLException e) {
			log.warn(e.getMessage());
		}
		
		return null;
	}

	@Override
	public String getUserNameByAccount(long account) throws BusinessException {
		String name = null;
		try (Connection connection = PostgresConnection.getConnection()) {
			String sql = "select name from user_account where account=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1,account);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				name = resultSet.getString("name");
			}
		} catch (ClassNotFoundException e) {
			log.warn(e.getMessage());
		} catch (SQLException e) {
			log.warn(e.getMessage());
		}
		return name;
	}

	@Override
	public void moneyTransfer(float tr_amt, long acc, long tr_acc) throws BusinessException {
		addTransfer(tr_amt, checkBalance(tr_acc), tr_acc,acc);
		withdrawlTransfer(tr_amt, checkBalance(acc), acc, tr_acc);

	}
	
	@Override
	public void addTransfer(float amount, float prevamount, long account ,long prevacc) throws BusinessException {

		String t_type=("credited by account no.: "+prevacc);
		txnAdd(t_type, account, amount);
		float tamt = amount+prevamount;
		try (Connection connection = PostgresConnection.getConnection()) 
		{
			String sql = "update user_account set balance=? where account=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setFloat(1, tamt);
			preparedStatement.setLong(2, account);
			preparedStatement.executeUpdate();
		}
		catch (ClassNotFoundException e) {
			log.warn(e.getMessage());
		} catch (SQLException e) {
			log.warn(e.getMessage());
		}
		
		
	}
	
	@Override
public void withdrawlTransfer(float amt, float pamt, long acc,long toacc) throws BusinessException {

		
		String t_type=("transfered to: "+toacc);
		txnAdd(t_type, acc, amt);
		float tamt = pamt-amt;
		try (Connection connection = PostgresConnection.getConnection()) 
		{
			String sql = "update user_account set balance=? where account=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setFloat(1, tamt);
			preparedStatement.setLong(2, acc);
			preparedStatement.executeUpdate();
		}
		catch (ClassNotFoundException e) {
			log.warn(e.getMessage());
		} catch (SQLException e) {
			log.warn(e.getMessage());
		}
		
	}

	@Override
	public List<UserAccount> getAllCustomerDetails() throws BusinessException {
		
		ArrayList<UserAccount> list= new ArrayList<>();
		try (Connection connection = PostgresConnection.getConnection()) {
			String sql = "select userid,name,pan,aadhar,balance,city,state,pincode,account from user_account order by userid";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				UserAccount userAccount = new UserAccount(0, null, 0, null, null, null, null, false, null, 0);
				userAccount.setUserid(resultSet.getInt("userid"));
				userAccount.setName(resultSet.getString("name"));
				userAccount.setAadhar(resultSet.getString("aadhar"));
				userAccount.setBalance(resultSet.getFloat("balance"));
				userAccount.setAccount(resultSet.getLong("account"));
				userAccount.setPan(resultSet.getString("pan"));
				userAccount.setCity(resultSet.getString("city"));
				userAccount.setPincode(resultSet.getString("pincode"));
				userAccount.setState(resultSet.getString("state"));
				list.add(userAccount);
			}

		} catch (ClassNotFoundException e) {
			log.warn(e.getMessage());
		} catch (SQLException e) {
			log.warn(e.getMessage());
		}

		if(list.size()==0) {
			throw new BusinessException("No Customer Exist");
		}
		else {
		return list;
		}

	}

	@Override
	public UserAccount getCustomerByName(String name) throws BusinessException {
		UserAccount userAccount = new UserAccount(0, null, 0, null, null, null, null, false, null, 0);
		try (Connection connection = PostgresConnection.getConnection()) {
			String sql = "select userid,name,pan,aadhar,balance,city,state,pincode,account from user_account where name=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, name);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				userAccount.setUserid(resultSet.getInt("userid"));
				userAccount.setName(resultSet.getString("name"));
				userAccount.setAadhar(resultSet.getString("aadhar"));
				userAccount.setBalance(resultSet.getFloat("balance"));
				userAccount.setAccount(resultSet.getLong("account"));
				userAccount.setPan(resultSet.getString("pan"));
				userAccount.setCity(resultSet.getString("city"));
				userAccount.setPincode(resultSet.getString("pincode"));
				userAccount.setState(resultSet.getString("state"));
			}

		} catch (ClassNotFoundException e) {
			log.warn(e.getMessage());
		} catch (SQLException e) {
			log.warn(e.getMessage());
		}

		return userAccount;
	}

	@Override
	public UserAccount getCustomerById(int userid) throws BusinessException {
		UserAccount userAccount = new UserAccount(0, null, 0, null, null, null, null, false, null, 0);
		try (Connection connection = PostgresConnection.getConnection()) {
			String sql = "select userid,name,pan,aadhar,balance,city,state,pincode,account from user_account where userid=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, userid);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				userAccount.setAccount(resultSet.getLong("account"));
				userAccount.setUserid(resultSet.getInt("userid"));
				userAccount.setAadhar(resultSet.getString("aadhar"));
				userAccount.setBalance(resultSet.getFloat("balance"));
				userAccount.setPan(resultSet.getString("pan"));
				userAccount.setName(resultSet.getString("name"));
				userAccount.setCity(resultSet.getString("city"));
				userAccount.setState(resultSet.getString("state"));
				userAccount.setPincode(resultSet.getString("pincode"));
			}
		} catch (ClassNotFoundException e) {
			log.warn(e.getMessage());
		} catch (SQLException e) {
			log.warn(e.getMessage());
		}

		return userAccount;
	}

	@Override
	public UserAccount getCustomerByAccount(long account) throws BusinessException {
		UserAccount userAccount = new UserAccount(0, null, 0, null, null, null, null, false, null, 0);
		try (Connection connection = PostgresConnection.getConnection()) {
			String sql = "select userid,name,pan,aadhar,balance,city,state,pincode,account from user_account where account=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, account);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				userAccount.setAccount(resultSet.getLong("account"));
				userAccount.setUserid(resultSet.getInt("userid"));
				userAccount.setAadhar(resultSet.getString("aadhar"));
				userAccount.setBalance(resultSet.getFloat("balance"));
				userAccount.setPan(resultSet.getString("pan"));
				userAccount.setName(resultSet.getString("name"));
				userAccount.setCity(resultSet.getString("city"));
				userAccount.setState(resultSet.getString("state"));
				userAccount.setPincode(resultSet.getString("pincode"));
			}
		} catch (ClassNotFoundException e) {
			log.warn(e.getMessage());
		} catch (SQLException e) {
			log.warn(e.getMessage());
		}

		return userAccount;
	}

	@Override
	public UserAccount getCustomerByPan(String pan) throws BusinessException {
		UserAccount userAccount = new UserAccount(0, null, 0, null, null, null, null, false, null, 0);
		try (Connection connection = PostgresConnection.getConnection()) {
			String sql = "select userid,name,pan,aadhar,balance,city,state,pincode,account from user_account where pan=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, pan);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				userAccount.setAccount(resultSet.getLong("account"));
				userAccount.setUserid(resultSet.getInt("userid"));
				userAccount.setAadhar(resultSet.getString("aadhar"));
				userAccount.setBalance(resultSet.getFloat("balance"));
				userAccount.setPan(resultSet.getString("pan"));
				userAccount.setName(resultSet.getString("name"));
				userAccount.setCity(resultSet.getString("city"));
				userAccount.setState(resultSet.getString("state"));
				userAccount.setPincode(resultSet.getString("pincode"));
			}
		} catch (ClassNotFoundException e) {
			log.warn(e.getMessage());
		} catch (SQLException e) {
			log.warn(e.getMessage());
		}

		return userAccount;
	}

	@Override
	public List<UserAccount> getCustomerByState(String state) throws BusinessException {
		ArrayList<UserAccount> list= new ArrayList<>();
		try (Connection connection = PostgresConnection.getConnection()) {
			String sql = "select userid,name,pan,aadhar,balance,city,state,pincode,account from user_account where state=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, state);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				UserAccount userAccount = new UserAccount(0, null, 0, null, null, null, null, false, null, 0);
				userAccount.setUserid(resultSet.getInt("userid"));
				userAccount.setName(resultSet.getString("name"));
				userAccount.setAadhar(resultSet.getString("aadhar"));
				userAccount.setBalance(resultSet.getFloat("balance"));
				userAccount.setAccount(resultSet.getLong("account"));
				userAccount.setPan(resultSet.getString("pan"));
				userAccount.setCity(resultSet.getString("city"));
				userAccount.setPincode(resultSet.getString("pincode"));
				userAccount.setState(resultSet.getString("state"));
				list.add(userAccount);
			}

		} catch (ClassNotFoundException e) {
			log.warn(e.getMessage());
		} catch (SQLException e) {
			log.warn(e.getMessage());
		}

		if(list.size()==0) {
			throw new BusinessException("No Customer Exist");
		}
		else {
		return list;
		}
	}

	@Override
	public List<UserTransaction> getAllTransactions() throws BusinessException {
		ArrayList<UserTransaction> list= new ArrayList<>();
		try (Connection connection = PostgresConnection.getConnection()) {
			String sql = "select transactionid,transactiontype,amt,account from transactions";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				UserTransaction userTransaction = new UserTransaction(0, 0, 0, null);
				userTransaction.setTransactionid(resultSet.getLong("transactionid"));
				userTransaction.setAccountno(resultSet.getLong("account"));
				userTransaction.setTransactiontype(resultSet.getString("transactiontype"));
				userTransaction.setAmt(resultSet.getFloat("amt"));
				list.add(userTransaction);
			}

		} catch (ClassNotFoundException e) {
			log.warn(e.getMessage());
		} catch (SQLException e) {
			log.warn(e.getMessage());
		}

		if(list.size()==0) {
			throw new BusinessException("No Customer Exist");
		}
		else {
		return list;
		}
	}

	@Override
	public List<UserTransaction> getTransactionsByAccount(long account) throws BusinessException {
		ArrayList<UserTransaction> list= new ArrayList<>();
		try (Connection connection = PostgresConnection.getConnection()) {
			String sql = "select transactionid,transactiontype,amt,account from transactions where account=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, account);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				UserTransaction userTransaction = new UserTransaction(0, 0, 0, null);
				userTransaction.setTransactionid(resultSet.getLong("transactionid"));
				userTransaction.setAccountno(resultSet.getLong("account"));
				userTransaction.setTransactiontype(resultSet.getString("transactiontype"));
				userTransaction.setAmt(resultSet.getFloat("amt"));
				list.add(userTransaction);
			}

		} catch (ClassNotFoundException e) {
			log.warn(e.getMessage());
		} catch (SQLException e) {
			log.warn(e.getMessage());
		}

		if(list.size()==0) {
			log.info("No Customer Exist");
			throw new BusinessException("No Customer Exist");
			
		}
		else {
		return list;
		}
	}

	@Override
	public boolean isValidEmp(Emp emp) throws BusinessException {
		boolean z=false;
		try (Connection connection = PostgresConnection.getConnection()) {
			String sql = "select user_id from emp_details where user_id=? and password=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, emp.getEmpid());
			preparedStatement.setString(2, emp.getEmppassword());
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				z=true;
			}
			else {
				z=false;
			}
		}
		catch (SQLException | ClassNotFoundException e) {
			log.warn(e.getMessage());
		throw new BusinessException("invalid login creditentials");
	}
		return z;
	}

	@Override
	public boolean createUser(User user) throws BusinessException {
		boolean z=false;
		try (Connection connection = PostgresConnection.getConnection()) {
			String sql = "insert into user_details(name,pass,phone) values(?,?,?)";

			PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, user.getCustname());
			preparedStatement.setString(2, user.getPass());
			preparedStatement.setString(3, user.getPhone());
			int c = preparedStatement.executeUpdate();
			if (c == 1) {
				ResultSet resultSet = preparedStatement.getGeneratedKeys();
				if (resultSet.next()) {
					user.setUserid(resultSet.getInt(1));
					z=true;
				}
			} else {
				z=false;
				throw new BusinessException("Product Registration Failure Please Retry!!!");
				
			}
		} catch (ClassNotFoundException | SQLException e) {
			log.warn(e.getMessage());
			throw new BusinessException("Internal error occured... Kindly conatct SYSADMIN........");
		} catch (BusinessException e) {
			log.warn(e.getMessage());
		}

		return z;
	}

	@Override
	public boolean createUserAccount(UserAccount userAccount) throws BusinessException {
		
		boolean z=false;
		try (Connection connection = PostgresConnection.getConnection()) {
			String sql = "insert into user_account(userid,name,pan,aadhar,balance,city,state,status,pincode) values(?,?,?,?,?,?,?,?,?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, userAccount.getUserid());
			preparedStatement.setString(2, userAccount.getName());
			preparedStatement.setString(3, userAccount.getPan());
			preparedStatement.setString(4, userAccount.getAadhar());
			preparedStatement.setFloat(5, userAccount.getBalance());
			preparedStatement.setString(6, userAccount.getCity());
			preparedStatement.setString(7, userAccount.getState());
			preparedStatement.setBoolean(8, userAccount.isStatus());
			preparedStatement.setString(9, userAccount.getPincode());
			int c = preparedStatement.executeUpdate();
			if (c == 1) {
				ResultSet resultSet = preparedStatement.getGeneratedKeys();
				if (resultSet.next()) {
					userAccount.setAccount(resultSet.getLong(10));
					z=true;
				}
			} else {
				z=false;
				throw new BusinessException("Product Registration Failure Please Retry!!!");
				
			}

		} catch (ClassNotFoundException | SQLException e) {
			log.warn(e.getMessage());
			throw new BusinessException("Internal error occured... Kindly conatct SYSADMIN........");
		} catch (BusinessException e) {
			log.warn(e.getMessage());
		}
		return z;
	}

	@Override
	public boolean isValidUser(User user) throws BusinessException {
		boolean z=false;
		try (Connection connection = PostgresConnection.getConnection()) {
			String sql = "select userid from user_details where userid=? and pass=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, user.getUserid());
			preparedStatement.setString(2, user.getPass());
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				z=true;
			}
			else {
				z=false;
			}
		}
		catch (SQLException | ClassNotFoundException e) {
			log.warn(e.getMessage());
		throw new BusinessException("invalid login creditentials");
	}
		return z;
	}

}
