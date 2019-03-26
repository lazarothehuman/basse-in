package mz.co.basse.test.core;

import java.sql.Connection;
import java.sql.DriverManager;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.springframework.core.io.ClassPathResource;

/**
 * Provê a Funcionalidade de preparar o banco de dados de testes antes da
 * execução de cada um dos métodos de teste. Desta forma, cada método de teste
 * pode alterar o banco de dados a vontade, é garantido que antes do teste
 * seguinte iniciar, o banco será restaurado.
 * 
 *
 * 
 */
abstract public class DBUnitTestCase {

	@Before
	public void prepareDatabase() throws Exception {
		// database connection
		Class.forName("com.mysql.jdbc.Driver");
		Connection jdbcConnection = DriverManager.getConnection(
				"jdbc:mysql://localhost/bassetest", "basse", "basselda");
		IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);
		connection.getConfig().setProperty(
				DatabaseConfig.PROPERTY_DATATYPE_FACTORY,
				new MySqlDataTypeFactory());
		ReplacementDataSet dataSet = new ReplacementDataSet(
				new FlatXmlDataSetBuilder().build(new ClassPathResource(
						getDataSetFileName()).getInputStream()));
		dataSet.addReplacementObject("[null]", null);
		try {
			DatabaseOperation.CLEAN_INSERT.execute(
					connection,
					new ReplacementDataSet(new FlatXmlDataSetBuilder()
							.build(new ClassPathResource("Cleanup-dataset.xml")
									.getInputStream())));
			DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
		} finally {
			connection.close();
		}
	}

	/**
	 * Método onde os testes devem indicar o nome do ficheiro com os dados
	 * necessários para o teste. Este ficheiro deve ficar localizado no mesmo
	 * pacote que a classe de teste.
	 * 
	 * @return
	 */
	abstract protected String getDataSetFileName();
}
