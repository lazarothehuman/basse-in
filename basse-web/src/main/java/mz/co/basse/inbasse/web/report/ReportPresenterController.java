package mz.co.basse.inbasse.web.report;

import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Iframe;

import mz.co.basse.inbasse.web.util.SessionHelper;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ReportPresenterController extends SelectorComposer<Component> {

	private static final long serialVersionUID = 6505108298348437813L;

	@Wire
	private Iframe iframe;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		Media media = (Media) SessionHelper.takeObject("reportToGenerate");
		iframe.setContent(media);
	}
}
