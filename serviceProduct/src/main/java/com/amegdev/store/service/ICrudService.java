package com.amegdev.store.service;

import java.util.List;

public interface ICrudService<T> {
	
	List<T> getAll();
	T create(T entity);
	T update(T entity);
	T delete(Integer id);	
	T get(Integer id);
}
