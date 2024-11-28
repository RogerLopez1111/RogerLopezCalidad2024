package com.fca.calidad.unitaria;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CalculadoraTest {
	
	
	private double num1 = 2;
	private double num2 = 4;
	private Calculadora calculadora = new Calculadora();
	
	
	@BeforeEach
	void setup() {
		num1 = 2;
		num2 = 4;
		calculadora = new Calculadora();
	}

	@Test
	void suma2numerosPositivosTest() {
		//inicializacion
		/*double num1 = 2;
		double num2 = 4;*/
		double resEsperado= 6;
		/*Calculadora calculadora = new Calculadora();*/
		//Ejercicio, llamar al metodo que queremos acabar
		double resEjecucion= calculadora.suma(num1,num2);
		//Verificar
		assertThat(resEsperado, is(resEjecucion));
	}
	
	@Test
	void multiplicanumeroporCeroTest() {
		//inicializacion
		double OP1 = 0;
		double OP2 = 1;
		double ResEsperada=0;
		Calculadora calculadora = new Calculadora();
		
		//Ejercicio llamar al metodo
		double ResEjecucion= calculadora.multiplica(OP1, OP2);
		
		//Verificar
		assertThat(ResEsperada, is(ResEjecucion));
	}
	
	/*@Test
	void testExpectedExceptionFail() {
		NumberFormatException thrown = Assertions.assertThrows(NumberFormatException.class, ()-> {
			Integer.parseInt("hola");
		}, "NumberFormatException error was expected");
	}*/
	
	
	@AfterEach
	void print() {
		System.out.println("Esto se imprime despues de cada prueba");
	}

}
