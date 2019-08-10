package mz.co.basse.accesscontrol.web.products;

import java.math.BigDecimal;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Textbox;

import mz.co.basse.accesscontrol.core.manager.AccessControlManager;
import mz.co.basse.accesscontrol.core.model.Client;
import mz.co.basse.accesscontrol.core.model.Product;
import mz.co.basse.web.util.AutoClosableController;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class AddProductController extends AutoClosableController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2646080299705701746L;
	
	@Wire
	private Textbox name;

	@Wire
	private BigDecimal price;

	@Wire
	private Checkbox limited;
	
	@WireVariable
	private AccessControlManager accessControlManager;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
	}
	
	

	@Listen("onClick = #saveButton")
	public void save() {

		Product product = new Product();
		product.setName(name.getText().trim());
//		product.setPricePerUnit(price);
//		product.setLimited((Boolean) null);
//		accessControlManager.createOrUpdateProduct(product);
		close();
	}

}
