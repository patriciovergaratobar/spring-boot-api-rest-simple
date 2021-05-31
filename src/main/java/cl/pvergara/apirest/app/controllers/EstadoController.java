package cl.pvergara.apirest.app.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cl.pvergara.apirest.app.models.entity.Estado;
import cl.pvergara.apirest.app.models.response.ResultadoGenerico;
import cl.pvergara.apirest.app.models.service.IEstadoService;

@RestController
@RequestMapping("/estado")
public class EstadoController {

	@Autowired
	private IEstadoService estadoService;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public Estado getById(@PathVariable Long id) {
    	
        return estadoService.getByKey(id);
    }
    
    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public List<Estado> getUsuarios() {
    	
    	return estadoService.findAll();
    }
    
    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
    public ResultadoGenerico<Estado> create(@RequestBody Estado data) {
    	
    	final Estado estado = estadoService.create(data);
    	final ResultadoGenerico<Estado> resultado = new ResultadoGenerico<Estado>();
    	if (estado == null) {
    		
    		resultado.setEstado(Boolean.FALSE);
        	resultado.setMsg("Ya existe un registro con el mismo ID.");
        	resultado.setData(null);
    	} else {
    		
    		resultado.setEstado(Boolean.TRUE);
        	resultado.setMsg("Registro guardado.");
        	resultado.setData(Optional.of(estado));
    	}
    	
    	return resultado;
    }
    
    @RequestMapping(value = "", method = RequestMethod.PATCH, produces = "application/json")
    public ResultadoGenerico<Estado> update(@RequestBody Estado data) {
    	
    	final ResultadoGenerico<Estado> resultado = new ResultadoGenerico<Estado>();
    	resultado.setEstado(estadoService.update(data));
    	resultado.setMsg("Actualización de estado.");
    	resultado.setData(Optional.of(data));
    	return resultado;
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public ResultadoGenerico<Estado> delete(@PathVariable Long id) {
    	
    	final ResultadoGenerico<Estado> resultado = new ResultadoGenerico<Estado>();
    	resultado.setEstado(estadoService.delete(id));
    	resultado.setMsg("Actualización de estado.");
    	resultado.setData(Optional.empty());
    	return resultado;
    }
}
