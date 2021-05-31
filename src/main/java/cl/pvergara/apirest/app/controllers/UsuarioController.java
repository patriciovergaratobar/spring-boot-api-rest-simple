package cl.pvergara.apirest.app.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.pvergara.apirest.app.models.entity.Usuario;
import cl.pvergara.apirest.app.models.response.ResultadoGenerico;
import cl.pvergara.apirest.app.models.service.IUsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
	
	@Autowired
	IUsuarioService usuarioService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public Usuario getById(@PathVariable Long id) {
    	
        return usuarioService.getByKey(id);
    }
    
    @RequestMapping(value = "/username/{username}", method = RequestMethod.GET, produces = "application/json")
    public Usuario getById(@PathVariable String username) {
    	
        return usuarioService.getByUserName(username);
    }
    
    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public List<Usuario> getUsuarios() {
    	
    	return usuarioService.findAll();
    }
    
    @RequestMapping(value = "/custom", method = RequestMethod.GET, produces = "application/json")
    public Map<String,String> getUsuariosByFilter(@RequestParam Map<String,String> allParams) {
    	
    	return allParams;
    }
    
    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
    public Usuario create(@RequestBody Usuario usuario) {
    	
    	return usuarioService.create(usuario);
    }
    
    @RequestMapping(value = "", method = RequestMethod.PATCH, produces = "application/json")
    public ResultadoGenerico<Usuario> update(@RequestBody Usuario usuario) {
    	
    	final ResultadoGenerico<Usuario> resultado = new ResultadoGenerico<Usuario>();
    	resultado.setEstado(usuarioService.update(usuario));
    	resultado.setMsg("Actualización de usuario.");
    	resultado.setData(Optional.of(usuario));
    	return resultado;
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public ResultadoGenerico<Usuario> delete(@PathVariable Long id) {
    	
    	final ResultadoGenerico<Usuario> resultado = new ResultadoGenerico<Usuario>();
    	resultado.setEstado(usuarioService.delete(id));
    	resultado.setMsg("Actualización de usuario.");
    	resultado.setData(Optional.empty());
    	return resultado;
    }
}
