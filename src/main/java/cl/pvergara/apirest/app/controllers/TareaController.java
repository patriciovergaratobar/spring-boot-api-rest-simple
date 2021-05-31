package cl.pvergara.apirest.app.controllers;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import cl.pvergara.apirest.app.models.entity.Tarea;
import cl.pvergara.apirest.app.models.response.ResultadoGenerico;
import cl.pvergara.apirest.app.models.service.ITareaService;

@RestController
@RequestMapping("/tarea")
public class TareaController {

	@Autowired
	private ITareaService tareaService;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public Tarea getById(@PathVariable Long id) {
    	
        return tareaService.getByKey(id);
    }
    
    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public List<Tarea> getAll() {
    	
    	return tareaService.findAll();
    }
    
    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
    public ResultadoGenerico<Tarea> create(@RequestBody Tarea data) {
    	
    	final Tarea tarea = tareaService.create(data);
    	final ResultadoGenerico<Tarea> resultado = new ResultadoGenerico<Tarea>();
    	if (tarea == null) {

    		resultado.setEstado(Boolean.FALSE);
        	resultado.setMsg("Ya existe un registro con el mismo ID.");
        	resultado.setData(null);
    	} else {
    		
    		resultado.setEstado(Boolean.TRUE);
        	resultado.setMsg("Registro guardado.");
        	resultado.setData(Optional.of(tarea));
    	}
    	
    	return resultado;
    }
    
    @RequestMapping(value = "", method = RequestMethod.PATCH, produces = "application/json")
    public ResultadoGenerico<Tarea> update(@RequestBody Tarea data) {
    	
    	final ResultadoGenerico<Tarea> resultado = new ResultadoGenerico<Tarea>();
    	resultado.setEstado(tareaService.update(data));
    	resultado.setMsg("Actualización de estado.");
    	resultado.setData(Optional.of(data));
    	return resultado;
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public ResultadoGenerico<Tarea> delete(@PathVariable Long id) {
    	
    	final ResultadoGenerico<Tarea> resultado = new ResultadoGenerico<Tarea>();
    	resultado.setEstado(tareaService.delete(id));
    	resultado.setMsg("Actualización de estado.");
    	resultado.setData(Optional.empty());
    	return resultado;
    }
}
