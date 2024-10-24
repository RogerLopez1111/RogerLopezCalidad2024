package com.fca.calidad.doubles;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DependencyTest {
private Dependency dependency;
private SubDependency sub;
@BeforeEach
void setup() {
	sub=mock(SubDependency.class);
	dependency=new Dependency(sub);
}

	@Test
	void test() {
		System.out.print(sub.getClassName());
	}
@Test
public void testDependency() {
	when (sub.getClassName()).thenReturn("Hola");
	assertThat(sub.getClassName(), is("Hola"));
}
	
}
