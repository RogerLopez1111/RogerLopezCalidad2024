package com.fca.calidad.service;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import com.fca.calidad.dao.IDAOUser;
import com.fca.calidad.model.User;
import com.fca.calidad.service.LoginService;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SuppressWarnings("unused")
class LoginServiceTest {

	LoginService service;
	IDAOUser dao;
	
	@Test
	void test() {
		//inicializacion
dao=mock (IDAOUser.class);
service=new LoginService(dao);
User usuario = new User ("nombre1","email","123");
when (dao.findByUserName("nombre1")).thenReturn(usuario);
//ejercicio
boolean result = service.login("nombre1", "123");
//verificacion
assertThat(result,is(true));


	}
	
	@Test
	void test2() {
		//inicializacion
dao=mock (IDAOUser.class);
service=new LoginService(dao);
User usuario = new User ("nombre1","email","123");
when (dao.findByUserName("nombre1")).thenReturn(usuario);

//ejercicio 2 contraseña incorrecta
boolean result2 = service.login("nombre1", "321");
//verificacion
assertThat(result2,is(false));
}
	@Test
	void test3() {
		//inicializacion
dao=mock (IDAOUser.class);
service=new LoginService(dao);
User usuario = new User ("nombre1","email","123");
when (dao.findByUserName("nombre1")).thenReturn(null);

//ejercicio 3 usuario no encontrado
boolean result = service.login("usuario2", "321");
//verificacion
assertThat(result,is(false));
}
	

}
