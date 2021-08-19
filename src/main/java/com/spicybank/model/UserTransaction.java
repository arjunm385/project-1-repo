package com.spicybank.model;

public class UserTransaction {
	
	private long accountno;
	private float amt;
	private long transactionid;
	private String transactiontype;
	public UserTransaction(long accountno, float amt, long transactionid, String transactiontype) {
		super();
		this.accountno = accountno;
		this.amt = amt;
		this.transactionid = transactionid;
		this.transactiontype = transactiontype;
	}
	public long getAccountno() {
		return accountno;
	}
	public void setAccountno(long accountno) {
		this.accountno = accountno;
	}
	public float getAmt() {
		return amt;
	}
	public void setAmt(float amt) {
		this.amt = amt;
	}
	public long getTransactionid() {
		return transactionid;
	}
	public void setTransactionid(long transactionid) {
		this.transactionid = transactionid;
	}
	public String getTransactiontype() {
		return transactiontype;
	}
	public void setTransactiontype(String transactiontype) {
		this.transactiontype = transactiontype;
	}
	@Override
	public String toString() {
		return "UserTransaction [accountno=" + accountno + ", amt=" + amt + ", transactionid=" + transactionid
				+ ", transactiontype=" + transactiontype + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (accountno ^ (accountno >>> 32));
		result = prime * result + Float.floatToIntBits(amt);
		result = prime * result + (int) (transactionid ^ (transactionid >>> 32));
		result = prime * result + ((transactiontype == null) ? 0 : transactiontype.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserTransaction other = (UserTransaction) obj;
		if (accountno != other.accountno)
			return false;
		if (Float.floatToIntBits(amt) != Float.floatToIntBits(other.amt))
			return false;
		if (transactionid != other.transactionid)
			return false;
		if (transactiontype == null) {
			if (other.transactiontype != null)
				return false;
		} else if (!transactiontype.equals(other.transactiontype))
			return false;
		return true;
	}
	
	

}
