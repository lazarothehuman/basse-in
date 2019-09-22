package mz.co.basse.inbasse.finance.core.dao;

import java.util.List;

import mz.co.basse.inbasse.finance.core.model.Bank;


public interface BankDao {
	
	void createBank(Bank bank);

	void update(Bank bank);

	Bank findBank(String name);

	List<Bank> findBanks(Boolean active);
}
