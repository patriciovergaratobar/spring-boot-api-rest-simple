package cl.pvergara.apirest.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.pvergara.apirest.app.models.dao.IUsuarioDao;
import cl.pvergara.apirest.app.models.entity.Usuario;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

	@Autowired
	private IUsuarioDao usuarioDao;
	
	@Override
	@Transactional(readOnly = true)
	public Usuario getByKey(Long key) {

		return usuarioDao.findById(key).orElse(new Usuario());
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Usuario> findAll() {
		
		return (List<Usuario>) usuarioDao.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Usuario getByUserName(String username) {

		return usuarioDao.getByUserName(username);
	}

	@Override
	public Usuario create(Usuario data) {
		
		return (Usuario) usuarioDao.save(data);
	}

	@Override
	public Boolean update(Usuario entity) {
		
		if (usuarioDao.existsById(entity.getUsuarioId())) {

			usuarioDao.save(entity);
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	@Override
	public Boolean delete(Long key) {

		if (usuarioDao.existsById(key)) {

			usuarioDao.deleteById(key);
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
}
