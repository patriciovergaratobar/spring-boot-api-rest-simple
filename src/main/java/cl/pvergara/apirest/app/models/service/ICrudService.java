package cl.pvergara.apirest.app.models.service;

import java.util.List;


public interface ICrudService<E, K> {

	public E getByKey(K key);

	public List<E> findAll();

	public E create(E entity);

	public Boolean update(E entity);
	
	public Boolean delete(K key);

}
