package com.xm.escenario.servicio;

import static com.xm.core.BasePrueba.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.xm.autenticacion.servicio.testdatabuilder.DtoRolEscenarioTestDataBuilder;
import com.xm.dominio.enums.EscenarioEnum;
import com.xm.dominio.excepcion.ExcepcionAutorizacion;
import com.xm.rol.modelo.dto.DtoRolEscenario;
import com.xm.rol.puerto.dao.DaoRol;

@ExtendWith(MockitoExtension.class)
public class ServicioValidarEscenarioTest {
	public static final List<String> ROLES = List.of("SO-Despacho");
	public static final int LISTA_ITEMS = 2;
	public static final int LISTA_ITEMS_VACIA = 0;

	@Mock
	private DaoRol daoRol;

	@InjectMocks
	private ServicioValidarEscenario servicioValidarEscenario;

	@Test
	void deberiaValidarEscenarioTest() {
		List<DtoRolEscenario> dtoRolEscenarios = new DtoRolEscenarioTestDataBuilder().construirLista(LISTA_ITEMS);

		when(daoRol.consultarPorNombres(anyList())).thenReturn(dtoRolEscenarios);

		servicioValidarEscenario.ejecutar(ROLES, EscenarioEnum.DESPACHO);

		verify(daoRol).consultarPorNombres(anyList());
	}

	@Test
	void deberiaLanzarExcepcionPorEscenarioInvalidoTest() {
		List<DtoRolEscenario> dtoRolEscenarios = new DtoRolEscenarioTestDataBuilder().construirLista(LISTA_ITEMS_VACIA);

		when(daoRol.consultarPorNombres(anyList())).thenReturn(dtoRolEscenarios);

		assertThrows(() -> servicioValidarEscenario.ejecutar(ROLES, EscenarioEnum.REDESPACHO),
				ExcepcionAutorizacion.class, ExcepcionAutorizacion.MENSAJE_NO_AUTORIZADO);
	}
}
