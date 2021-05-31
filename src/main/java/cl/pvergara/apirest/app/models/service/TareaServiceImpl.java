package cl.pvergara.apirest.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.pvergara.apirest.app.models.dao.ITareaDao;
import cl.pvergara.apirest.app.models.entity.Tarea;

@Service
public class TareaServiceImpl implements ITareaService {

	@Autowired
	private ITareaDao tareaDao;
	
	@Override
	public Tarea getByKey(Long key) {

		return tareaDao.findById(key).orElse(new Tarea());
	}

	@Override
	public List<Tarea> findAll() {
		// TODO Auto-generated method stub
		return (List<Tarea>) tareaDao.findAll();
	}

	@Override
	public Tarea create(Tarea entity) {

		if (!tareaDao.existsById(entity.getTareaId())) {
			
			return tareaDao.save(entity);
		}
		
		return null;
	}

	@Override
	public Boolean update(Tarea entity) {
		
		if (tareaDao.existsById(entity.getTareaId())) {
			
			tareaDao.save(entity);
			return Boolean.TRUE;
		}
		
		return Boolean.FALSE;
	}

	@Override
	public Boolean delete(Long key) {
		
		if (tareaDao.existsById(key)) {

			tareaDao.deleteById(key);
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

}
