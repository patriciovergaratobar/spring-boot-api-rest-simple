package cl.pvergara.apirest.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import cl.pvergara.apirest.app.controllers.TareaController;
import cl.pvergara.apirest.app.exception.BadRequestException;
import cl.pvergara.apirest.app.models.ResultadoGenericoTest;
import cl.pvergara.apirest.app.models.entity.Estado;
import cl.pvergara.apirest.app.models.entity.Tarea;
import cl.pvergara.apirest.app.models.entity.Usuario;
import cl.pvergara.apirest.app.models.service.ITareaService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@ExtendWith(MockitoExtension.class)
public class TareaControllerIntegrationTest {
	
	private MockMvc mvc;
	
	@Mock
    private ITareaService tareaService;

    @InjectMocks
    private TareaController tareaController;
    
    private JacksonTester<Tarea> jsonTarea;
    private JacksonTester<List<Tarea>> jsonListaTarea;
    private JacksonTester<ResultadoGenericoTest<Tarea>> jsonRespuesta;
    
    @BeforeEach
    public void setup() {
    	
        JacksonTester.initFields(this, new ObjectMapper());
   
        mvc = MockMvcBuilders.standaloneSetup(tareaController)
                .setControllerAdvice(new BadRequestException())
                .build();
    }
    
    @Test
    public void consultaIdExistente() throws Exception {
        
    	final Long key = 1L;
    	final Tarea tarea = new Tarea();
    	tarea.setTareaId(key);
    	tarea.setNombre("Prueba");
    	
    	// given
        given(tareaService.getByKey(key)).willReturn(tarea);

        // when
        MockHttpServletResponse response = mvc.perform(
                get("/tarea/1").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonTarea.write(tarea).getJson());
    }
    
    @Test
    public void consultaIdNoExistente() throws Exception {
        
    	final Long key = 2L;
    	final Tarea tarea = new Tarea();
    	tarea.setTareaId(key);
    	
    	// given
        given(tareaService.getByKey(key)).willReturn(new Tarea());

        // when
        MockHttpServletResponse response = mvc.perform(
                get("/tarea/2").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        Tarea tareaRespuesta = jsonTarea.parse(response.getContentAsString()).getObject();
        assertThat(tareaRespuesta.getTareaId()).isNull();
    }
    
    @Test
    public void obtenerListado() throws Exception {
        
    	final List<Tarea> lista = new ArrayList<>();
    	lista.add(new Tarea());
    	lista.add(new Tarea());
    	
    	// given
        given(tareaService.findAll()).willReturn(lista);

        // when
        MockHttpServletResponse response = mvc.perform(
                get("/tarea").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        
        
        List<Tarea> tareas = jsonListaTarea.parse(response.getContentAsString()).getObject();
        
        assertTrue( tareas.size() == lista.size());
    }

    
    @Test
    public void crearTarea() throws Exception {
        
    	final Long key = 2L;
    	final Tarea tarea = new Tarea();
    	tarea.setTareaId(key);
    	tarea.setNombre("Prueba");
    	tarea.setCreate_at(new Date());
    	tarea.setEstado(new Estado());
    	tarea.setUsuario(new Usuario());
    	tarea.setDescricion("Esta es una descripcion.");
    	
    	// given
        given(tareaService.create(any())).willReturn(tarea);

        
        // when
        MockHttpServletResponse response = mvc.perform(
                post("/tarea")
                	.contentType(MediaType.APPLICATION_JSON)
                	.content(jsonTarea.write(tarea).getJson())
                )
                .andReturn().getResponse();

        // then
        final ResultadoGenericoTest<Tarea> respuesta = jsonRespuesta.parse(response.getContentAsString()).getObject();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertTrue(respuesta.getEstado());
        assertThat(respuesta.getData().getTareaId()).isEqualTo(key);
    }
    
    
    @Test
    public void crearTareaConRetornoNulo() throws Exception {
        
    	final Long key = 2L;
    	final Tarea tarea = new Tarea();
    	tarea.setTareaId(key);
    	tarea.setNombre("Prueba");
    	
    	// given
        given(tareaService.create(any())).willReturn(null);

        // when
        MockHttpServletResponse response = mvc.perform(
        		post("/tarea")
	            	.contentType(MediaType.APPLICATION_JSON)
	            	.content(jsonTarea.write(tarea).getJson())
	            )
                .andReturn().getResponse();

        // then
        final ResultadoGenericoTest<Tarea> respuesta = jsonRespuesta.parse(response.getContentAsString()).getObject();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertFalse(respuesta.getEstado());
    }
    
    @Test
    public void actualizarTarea() throws Exception {
        
    	final Long key = 2L;
    	final Tarea tarea = new Tarea();
    	tarea.setTareaId(key);
    	tarea.setNombre("Prueba");
    	tarea.setCreate_at(new Date());
    	tarea.setEstado(new Estado());
    	tarea.setUsuario(new Usuario());
    	tarea.setDescricion("Esta es una descripcion.");
    	
    	// given
        given(tareaService.update(any())).willReturn(true);

        
        // when
        MockHttpServletResponse response = mvc.perform(
                patch("/tarea")
                	.contentType(MediaType.APPLICATION_JSON)
                	.content(jsonTarea.write(tarea).getJson())
                )
                .andReturn().getResponse();

        // then
        final ResultadoGenericoTest<Tarea> respuesta = jsonRespuesta.parse(response.getContentAsString()).getObject();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertTrue(respuesta.getEstado());
    }
    
    @Test
    public void eliminarTarea() throws Exception {
        
    	final Long key = 1L;
    	
    	// given
        given(tareaService.delete(key)).willReturn(true);

        // when
        MockHttpServletResponse response = mvc.perform(
                delete("/tarea/"+key)
                	.accept(MediaType.APPLICATION_JSON)
                )
                .andReturn().getResponse();

        // then
        final ResultadoGenericoTest<Tarea> respuesta = jsonRespuesta.parse(response.getContentAsString()).getObject();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertTrue(respuesta.getEstado());
    }
}
