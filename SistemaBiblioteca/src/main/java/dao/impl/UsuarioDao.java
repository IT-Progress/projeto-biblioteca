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
	
	public List<Usuario> findAll2() {
		return manager.createQuery("Select u from Relatorio u").getResultList();
	}

	public Usuario findById(Long id) {
		Query query = manager.createQuery("Select u from Usuario u where u.id = :pId");
		query.setParameter("pId", id);
		return (Usuario) query.getSingleResult();
	}

	public List<Usuario> findByName(String nome) {
		Query query = manager.createQuery("Select u from Usuario u where lower(u.nome) like lower(:pNome)");
		query.setParameter("pNome", "%" + nome + "%");
		return query.getResultList();
	}
	
	public List<Usuario> findByName2(String nome) {
		Query query = manager.createQuery("Select u from Relatorio u where u.livro_id like :pNome");
		query.setParameter("pNome", "%" + nome + "%");
		return query.getResultList();
	}

	@Transactional
	public boolean save(Usuario usuario) {
		try {
			if (usuario.getId() != null) {
				manager.merge(usuario);
			} else {
				manager.persist(usuario);
			}
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


	public Usuario autenticacao(String email, String senha) {
		Query query = manager.createQuery("Select u from Usuario u where u.email = :pEmail and u.senha = :pSenha");
		query.setParameter("pEmail", email);
		query.setParameter("pSenha", senha);	
		return (Usuario) query.getSingleResult();
	}
	
}
