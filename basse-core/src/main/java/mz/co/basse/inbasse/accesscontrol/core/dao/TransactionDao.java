package mz.co.basse.inbasse.accesscontrol.core.dao;

import java.util.List;

import mz.co.basse.inbasse.accesscontrol.core.model.Transaction;

public interface TransactionDao {
	public void create(Transaction transaction);

	public List<Transaction> findTransactions(Boolean active);

	public void update(Transaction transaction);
	
	public Transaction findTransaction(String code);
}
