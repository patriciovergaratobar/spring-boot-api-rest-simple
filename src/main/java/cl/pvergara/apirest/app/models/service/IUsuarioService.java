package cl.pvergara.apirest.app.models.service;

import cl.pvergara.apirest.app.models.entity.Usuario;

public interface IUsuarioService extends ICrudService<Usuario, Long> {

	public Usuario getByUserName(String username);
}
