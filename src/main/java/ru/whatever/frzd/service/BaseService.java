package ru.whatever.frzd.service;


public interface BaseService<T, ID> {

    T saveOrUpdate(T entity);

    T find(ID id);
}
