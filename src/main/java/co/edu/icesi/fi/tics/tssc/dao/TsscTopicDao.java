package co.edu.icesi.fi.tics.tssc.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import co.edu.icesi.fi.tics.tssc.model.TsscTopic;

@Repository
@Scope("singleton")
public class TsscTopicDao implements ITsscTopicDao{

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<TsscTopic> findByName(String name) {
		
		String jpql = "SELECT a FROM TsscTopic a WHERE a.name = '"+name+"'";
		return entityManager.createQuery(jpql).getResultList();
	}

	@Override
	public List<TsscTopic> findByDescription(String description) {
		
		String jpql = "SELECT a FROM TsscTopic a WHERE a.description = '"+description+"'";
		return entityManager.createQuery(jpql).getResultList();
	}

	@Override
	public List<TsscTopic> findAll() {
		String jpql = "SELECT a FROM TsscTopic a";
		return entityManager.createQuery(jpql).getResultList();
	}


	@Override
	public void delete(TsscTopic entity) {
		entityManager.remove(entity);
	}

	@Override
	public void update(TsscTopic entity) {
		entityManager.merge(entity);
		
	}

	@Override
	public void save(TsscTopic entity) {
		entityManager.persist(entity);
	}

	@Override
	public TsscTopic findById(long id) {
		
		return entityManager.find(TsscTopic.class,id);
	}
	
	
}
