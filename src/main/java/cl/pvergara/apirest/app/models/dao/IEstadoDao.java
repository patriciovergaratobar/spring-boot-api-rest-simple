package cl.pvergara.apirest.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import cl.pvergara.apirest.app.models.entity.Estado;

public interface IEstadoDao  extends CrudRepository<Estado, Long> {

}
