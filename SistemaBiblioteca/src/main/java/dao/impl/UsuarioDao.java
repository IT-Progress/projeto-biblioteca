package dao.impl;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import dao.DAO;
import dao.Transactional;
import model.Usuario;

public class UsuarioDao implements DAO<Usuario> {
	@Inject
	private EntityManager manager;

	public List<Usuario> findAll() {
		return manager.createQuery("Select u from Usuario u").getResultList();
	}

	public Usuario findById(Long id) {
		Query query = manager.createQuery("Select u from Usuario u where u.id = :pId");
		query.setParameter("pId", id);
		return (Usuario) query.getSingleResult();
	}

	public List<Usuario> findByName(String nome) {
		Query query = manager.createQuery("Select u from Usuario u where u.nome like :pNome");
		query.setParameter("pNome", "%"+nome+"%");
		return query.getResultList();
	}

	@Transactional
	public boolean save(Usuario usuario) {
		try {
			manager.persist(usuario);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Transactional
	public boolean delete(Long id) {
		Usuario usuario = findById(id);
		try {
			manager.remove(usuario);
		} catch (Exception e) {
			return false;
		}
		return true;

	}
}