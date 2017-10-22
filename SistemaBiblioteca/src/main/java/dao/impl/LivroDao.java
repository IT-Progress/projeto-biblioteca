package dao.impl;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import dao.DAO;
import dao.Transactional;
import model.Livro;

public class LivroDao implements DAO<Livro> {

	@Inject
	private EntityManager manager;

	public List<Livro> findAll() {
		return manager.createQuery("Select u from Livro u").getResultList();
	}

	public Livro findById(Long id) {
		Query query = manager.createQuery("Select u from Livro u where u.id = :pId");
		query.setParameter("pId", id);
		return (Livro) query.getSingleResult();
	}

	public List<Livro> findByName(String nome) {
		Query query = manager.createQuery("Select u from Livro u where u.nome like :pNome");
		query.setParameter("pNome", "%" + nome + "%");
		return query.getResultList();
	}

	public List<Livro> findByAutor(String autor) {
		Query query = manager.createQuery("Select u from Livro u where u.autor like :pAutor");
		query.setParameter("pAutor", "%" + autor + "%");
		return query.getResultList();
	}

	@Transactional
	public boolean save(Livro livro) {
		try {
			if (livro.getId() != null) {
				manager.merge(livro);
			} else {
				manager.persist(livro);
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Transactional
	public boolean delete(Long id) {
		Livro livro = findById(id);
		try {
			manager.remove(livro);
		} catch (Exception e) {
			return false;
		}
		return true;

	}

}
