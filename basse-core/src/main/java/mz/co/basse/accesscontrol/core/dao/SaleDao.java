package mz.co.basse.accesscontrol.core.dao;

import mz.co.basse.finance.core.model.Sale;

public interface SaleDao {

	Sale findLastSales(int year);

}
