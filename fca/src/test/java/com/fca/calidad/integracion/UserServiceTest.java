package com.fca.calidad.integracion;

import static org.junit.jupiter.api.Assertions.*;
import org.dbunit.Assertion;
import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.FileInputStream;
import com.fca.calidad.dao.DAOUserSQLite;
import com.fca.calidad.model.User;
import com.fca.calidad.service.UserService;

class UserServiceTest extends DBTestCase {
private DAOUserSQLite dao;
private UserService userService;

public UserServiceTest() {
	System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "org.sqlite.JDBC");
	System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:sqlite:/Users/rogerlopez/Documents/users.db");
	System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "");
	System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "");


}

@Override
protected IDataSet getDataSet() throws Exception {
	return new FlatXmlDataSetBuilder().build(new FileInputStream("src/resources/initDB.xml"));
}

@BeforeEach
public void setUp() {
	dao=new DAOUserSQLite();
	userService=new UserService(dao);
	IDatabaseConnection connection;
	try {
		connection = getConnection();
		DatabaseOperation.CLEAN_INSERT.execute(connection, getDataSet());
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		fail("fallo SETUP");
	}
	
}
@Test
void createUserTest() {
	
	//ejercicio del código
	User usuario = userService.createUser("name", "email", "password");
	
	//assertion
	int resultadoEsperado = 1;
	
	IDatabaseConnection connection;
	try {
		connection = getConnection();
		IDataSet databaseDataSet = connection.createDataSet();
		ITable tablaReal = databaseDataSet.getTable("users");
		int resultadoActual = tablaReal.getRowCount();
		assertEquals(resultadoEsperado, resultadoActual);
		
	} catch (Exception e) {
		fail("fallo create 1");
		e.printStackTrace();
	}
			
}


@Test
void createUser2Test() {
	
	//ejercicio del código
	User usuario = userService.createUser("name", "email", "password");
	
	IDatabaseConnection connection;
	try {
		connection = getConnection();
		IDataSet databaseDataSet = connection.createDataSet();
		ITable tablaReal = databaseDataSet.getTable("users");
		String nombreReal = (String) tablaReal.getValue(0, "name");
		String nombreEsperado = "name";
		System.out.println("Real =" + nombreReal);
		assertEquals(nombreReal, nombreEsperado);
		System.out.println("E=" + (String) tablaReal.getValue(0, "email"));
		System.out.println("P=" + (String) tablaReal.getValue(0, "password"));
		assertEquals((toString()), tablaReal.getValue(0, "email"),"email");
		assertEquals((toString()), tablaReal.getValue(0, "password"),"password");
	} catch (Exception e) {
		fail("fallo create 2");
		e.printStackTrace();
	}


	}
@Test

void createUser3Test() {
	User usuario = userService.createUser("name", "email", "password");
	IDatabaseConnection connection;
	try {
		connection = getConnection();
		IDataSet databaseDataSet = connection.createDataSet();
		ITable tablaReal = databaseDataSet.getTable("users");
		IDataSet exceptedDataSet = new FlatXmlDataSetBuilder().build(new FileInputStream("src/resources/usuarios.xml"));
		ITable exceptedTable = exceptedDataSet.getTable("users");
		
		ITable filteredTable = DefaultColumnFilter.includedColumnsTable(tablaReal, exceptedTable.getTableMetaData().getColumns());
		
		Assertion.assertEquals(filteredTable, exceptedTable);
	}catch (Exception e) {
		
		e.printStackTrace();
		fail("fallo create 3");
	}


}



}
