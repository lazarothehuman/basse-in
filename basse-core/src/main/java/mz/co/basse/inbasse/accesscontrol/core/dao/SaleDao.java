package mz.co.basse.inbasse.accesscontrol.core.dao;

import mz.co.basse.inbasse.finance.core.model.Sale;

public interface SaleDao {

	Sale findLastSales(int year);

}
