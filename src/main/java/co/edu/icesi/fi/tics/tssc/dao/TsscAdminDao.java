package co.edu.icesi.fi.tics.tssc.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import co.edu.icesi.fi.tics.tssc.model.TsscAdmin;
import co.edu.icesi.fi.tics.tssc.model.TsscStory;

@Repository
@Scope("singleton")
public class TsscAdminDao implements ITsscAdminDao{
	
	@PersistenceContext
	private EntityManager entityManager;
	
	
	@Override
	public void save(TsscAdmin entity) {
		entityManager.persist(entity);
	}

	@Override
	public void update(TsscAdmin enity) {
		entityManager.merge(enity);
	}

	@Override
	public void delete(TsscAdmin entity) {
		entityManager.remove(entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TsscAdmin> findAll() {
		String jpql = "SELECT a FROM TsscAdmin a";
		return entityManager.createQuery(jpql).getResultList();
	}

	@Override
	public TsscAdmin findById(long id) {
		return entityManager.find(TsscAdmin.class, id);
	}

}
