package co.edu.icesi.fi.tics.tssc.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import co.edu.icesi.fi.tics.tssc.model.TsscStory;

@Repository
public class TsscStoryDao implements ITsscStoryDao{
	
	@PersistenceContext
	private EntityManager entityManager;
	
	
	@Override
	public void save(TsscStory entity) {
		entityManager.persist(entity);
	}

	@Override
	public void update(TsscStory enity) {
		entityManager.merge(enity);
	}

	@Override
	public void delete(TsscStory entity) {
		entityManager.remove(entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TsscStory> findAll() {
		String jpql = "SELECT a FROM TsscStory a";
		return entityManager.createQuery(jpql).getResultList();
	}

	@Override
	public TsscStory findById(long id) {
		return entityManager.find(TsscStory.class, id);
	}

}
