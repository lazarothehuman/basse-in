package mz.co.basse.inbasse.web.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import mz.co.basse.inbasse.accesscontrol.core.model.User;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;

import org.springframework.core.io.ClassPathResource;
import org.zkoss.util.resource.Labels;

/**
 * Representa um Relatorio PDF do Jasper.
 * 
 * 
 */
public class Report {

	private Map<String, Object> parameters;
	private List<Map<String, Object>> reportObjects;
	private String jasperFileName;
	private User user;

	public Report(Map<String, Object> parameters,
			List<Map<String, Object>> reportObjects, String jasperFileName,
			User user) {
		super();
		this.parameters = parameters;
		this.reportObjects = reportObjects;
		this.jasperFileName = jasperFileName;
		this.user = user;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public InputStream getStream() {
		try {
			List list = reportObjects;
			JRMapCollectionDataSource mapCollectionDataSource = new JRMapCollectionDataSource(
					list);
			
			// JasperCompileManager.compileReportToFile("payment-receipt.jrxml");

			ClassPathResource reportFile = new ClassPathResource(jasperFileName);
			ClassPathResource logo = new ClassPathResource("logo.png");
			addDefaultParameters(logo, parameters);
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					reportFile.getInputStream(), parameters,
					mapCollectionDataSource);
			byte[] content = JasperExportManager.exportReportToPdf(jasperPrint);
			return new ByteArrayInputStream(content);
		} catch (JRException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void addDefaultParameters(ClassPathResource logo,
			Map<String, Object> parameters) throws IOException {
		parameters.put("DEVELOPER_MESSAGE",
				Labels.getLabel("developer.message"));
		parameters.put("IMAGE", logo.getInputStream());
		parameters.put("GENERATION_DATE", Labels.getLabel("generation.date"));
		parameters.put("USER", Labels.getLabel("user"));
		parameters.put("USER_NAME", user.getName());
		parameters.put("GENERATION_DATE_FORMATED", FormatUtils.getDateformat()
				.format(new Date()));
	}

	public String getName() {
		return (String) parameters.get("REPORT_NAME");
	}
}
