package ru.whatever.frzd.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public abstract class BaseServiceImpl<T, ID,
        DAO extends CrudRepository<T, ID>> implements BaseService<T, ID> {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public T saveOrUpdate(final T entity) {
        return getDao().save(entity);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public T find(final ID id) {
        return getDao().findById(id).orElse(null);
    }

    protected List<Long> toLongArray(final String seq) {
        return Stream.of(seq.split("[,]")).map(Long::valueOf).collect(Collectors.toList());
    }

    protected void refresh(final T entity) {
        em.flush();
        em.refresh(entity);
    }

    protected abstract DAO getDao();

    public EntityManager getEm() {
        return em;
    }
}
