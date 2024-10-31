package com.fca.calidad.service;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import com.fca.calidad.dao.IDAOUser;
import com.fca.calidad.model.User;
import com.fca.calidad.service.LoginService;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

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

}
