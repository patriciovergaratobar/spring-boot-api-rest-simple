package cl.pvergara.apirest.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.pvergara.apirest.app.exception.BadRequestException;
import cl.pvergara.apirest.app.models.dao.IEstadoDao;
import cl.pvergara.apirest.app.models.entity.Estado;

@Service
public class EstadoServiceImpl implements IEstadoService {

	@Autowired
	private IEstadoDao estadoDao;
	
	@Override
	@Transactional(readOnly = true)
	public Estado getByKey(Long key) {

		return estadoDao.findById(key).orElse(new Estado());
	}

	@Override
	@Transactional(readOnly = true)
	public List<Estado> findAll() {

		return (List<Estado>) estadoDao.findAll();
	}

	@Override
	public Estado create(Estado entity) {
		
		if (entity.getEstadoId() == null || entity.getNombre() == null || entity.getDescripcion() == null || entity.getNombre().isEmpty() ) {
			
			throw new BadRequestException("Parametros no valido");
		}
		
		if (!estadoDao.existsById(entity.getEstadoId())) {

			return (Estado) estadoDao.save(entity);
		}
		
		return null;
	}

	@Override
	public Boolean update(Estado entity) {
		
		if (estadoDao.existsById(entity.getEstadoId())) {

			estadoDao.save(entity);
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	@Override
	public Boolean delete(Long key) {
		
		if (estadoDao.existsById(key)) {

			estadoDao.deleteById(key);
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

}
