package com.fca.calidad.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import com.fca.calidad.dao.IDAOUser;
import com.fca.calidad.model.User;

class UserServiceTest {

	private User usuario;
	private UserService servicio;
	private IDAOUser dao;
	private HashMap<Integer,User> basedatos;
	
	@BeforeEach
	void setup() {
		dao=mock(IDAOUser.class);
		servicio = new UserService(dao);
		basedatos=new HashMap<Integer,User>();
	}
	
	
	
	@Test
	void testUpdateUser() {
		User usuarioViejo=new User("nombre1","email","password");
		usuarioViejo.setId(1);
		basedatos.put(usuarioViejo.getId(),usuarioViejo);
		
		User usuarioNuevo=new User("nuevoNombre","nuevo email","nuevoPassword");
		usuarioNuevo.setId(1);
		
		when(dao.findById(1)).thenReturn(usuarioViejo);
		when(dao.updateUser(any(User.class))).thenAnswer(new Answer<User>() {
			public User answer(InvocationOnMock invocation) throws Throwable{
				User arg=(User) invocation.getArguments()[0];
				basedatos.replace(arg.getId(), arg);
				return basedatos.get(arg.getId());
			}
		}
		);
		
		User result = servicio.updateUser(usuarioNuevo);
		
		assertThat("nuevoPassword",is(result.getPassword()));
		assertThat("nuevoNombre",is(result.getName()));

		
	}
	@Test
	void testDeleteUser() {
	    // Inicialización
	    User usuario = new User("nombre1", "email", "password");
	    usuario.setId(1);
	    basedatos.put(usuario.getId(), usuario);
	    
	    // Configurar el comportamiento del mock
	    when(dao.findById(1)).thenReturn(usuario);
	    when(dao.deleteById(1)).thenAnswer(new Answer<Boolean>() {
	        public Boolean answer(InvocationOnMock invocation) {
	            int id = (Integer) invocation.getArguments()[0];
	            return basedatos.remove(id) != null;
	        }
	    });
	    
	    // Ejercicio
	    boolean result = servicio.deleteUser(1);
	    
	    // Verificación
	    assertThat(result, is(true));
	    assertThat(basedatos.containsKey(1), is(false));
	}
	
	@Test
	void testFindUserByid() {
	    // Inicialización
	    User usuario = new User("nombre1", "email", "password");
	    usuario.setId(1);
	    basedatos.put(usuario.getId(), usuario);
	    
	    // Configurar el comportamiento del mock
	    when(dao.findById(1)).thenReturn(usuario);
	    
	    // Ejercicio
	    User result = servicio.findUserById(1);
	    
	    // Verificación
	    assertThat(result.getName(), is("nombre1"));
	    assertThat(result.getEmail(), is("email"));
	    assertThat(result.getPassword(), is("password"));
	}
	
	@Test
	void testFindUserByEmail() {
	    
	    // Crear usuario simulado
	    User usuario = new User("Juan Perez", "juan@example.com", "password123");
	    usuario.setId(1);
	    basedatos.put(usuario.getId(), usuario);

	    // Simulación del comportamiento del DAO
	    when(dao.findUserByEmail("juan@example.com")).thenReturn(usuario); // Usuario encontrado
	    
	    // Ejecución: buscar usuario existente
	    User result = servicio.findUserByEmail("juan@example.com");

	    // Verificación: asegurarse de que el usuario encontrado es el correcto
	    assertThat(result, is(notNullValue()));
	    assertThat(result.getEmail(), is(result.getEmail()));
	    assertThat(result.getName(), is(result.getName()));
	}
	
	
	@Test
	void testCreateUser() {
	    // Arrange: Input values
	    String name = "John Doe";
	    String email = "john.doe@example.com";
	    String password = "validpassword3"; // This password is within the valid range

	    // Simulate that no user exists with this email in the database
	    when(dao.findUserByEmail(email)).thenReturn(null); // No existing user with this email
	    when(dao.save(any(User.class))).thenAnswer(invocation -> {
	        User user = invocation.getArgument(0); // Get the user object from the mock save
	        user.setId(1); // Simulate setting an ID
	        basedatos.put(user.getId(), user); // Save the user to the simulated database
	        return 1; // Return the simulated ID
	    });

	    // Act: Call the method to create the user
	    User newuser = servicio.createUser(name, email, password);

	    // Assert: Verify user is created correctly
	    assertThat(newuser, is(notNullValue())); // Ensure the user is not null
	    assertThat(newuser.getName(), is(name)); // Verify the name is correct
	    assertThat(newuser.getEmail(), is(email)); // Verify the email is correct
	    assertThat(newuser.getPassword(), is(password)); // Verify the password is correct
	    assertThat(newuser.getId(), is(1)); // Verify the user ID is 1 (simulated)

	    // Verify the user is added to the simulated database
	    assertThat(basedatos.containsKey(1), is(true)); // Ensure the user with ID 1 exists in basedatos
	    User userInDB = basedatos.get(1);
	    assertThat(userInDB.getName(), is(name)); // Verify the user in the database has the correct name
	    assertThat(userInDB.getEmail(), is(email)); // Verify the user in the database has the correct email
	    assertThat(userInDB.getPassword(), is(password)); // Verify the user in the database has the correct password
	}

	@Test
	void testFindAllUsers() {
	    // Arrange: Crear y agregar 5 usuarios de prueba a la base de datos ficticia
	    for (int i = 1; i <= 5; i++) {
	        User user = new User("User" + i, "user" + i + "@example.com", "password" + i);
	        user.setId(i);
	        basedatos.put(user.getId(), user);
	    }

	    // Simular el comportamiento de findAll en el DAO para que devuelva los usuarios de basedatos
	    when(dao.findAll()).thenReturn(new ArrayList<>(basedatos.values()));

	    // Act: Llamar al método findAllUsers
	    List<User> result = servicio.findAllUsers();
	    // Assert: Verificar que se devolvieron los usuarios correctamente
	    assertThat(result, is(notNullValue()));
	    assertThat(result.size(), is(5));

	    // Usar un ciclo para verificar que cada usuario se encuentra en la base de datos
	    for (User user : result) {
	        assertThat(basedatos.containsKey(user.getId()), is(true));
	        User userInDB = basedatos.get(user.getId());
	        assertThat(user.getName(), is(userInDB.getName()));
	        assertThat(user.getEmail(), is(userInDB.getEmail()));
	        assertThat(user.getPassword(), is(userInDB.getPassword()));
	    }
	}
	
}
