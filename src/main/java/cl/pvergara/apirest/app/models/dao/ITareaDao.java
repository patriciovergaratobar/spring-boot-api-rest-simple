package cl.pvergara.apirest.app.models.dao;

import org.springframework.data.repository.CrudRepository;
import cl.pvergara.apirest.app.models.entity.Tarea;

public interface ITareaDao  extends CrudRepository<Tarea, Long> {

	/*
	@Query("SELECT t FROM tareas t INNER JOIN t.usuario_id  WHERE t.usuario_id = ?1")
	public Tarea getByUser(Long userId); 
	*/
}
