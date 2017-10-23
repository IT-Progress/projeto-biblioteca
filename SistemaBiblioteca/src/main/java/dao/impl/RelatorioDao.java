package dao.impl;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import dao.DAO;
import model.Relatorio;

public class RelatorioDao implements DAO<Relatorio>{
	@Inject
	private EntityManager manager;
	
	public List<Relatorio> findAll() {
		return manager.createQuery("Select e.id, u.nome, l.titulo, e.emprestimo, e.devolucao"
								+ "from Usuario u"
								+ "inner join Emprestimo e on u.id = e.usuario_id"
								+ "inner join Livro l on l.id = e.livro_id").getResultList();
	}

	public List<Relatorio> findAllPersonalizado() {
		Query query = manager.createQuery("Select r from Relatorio r");
		return query.getResultList();
	} 
	
	public Relatorio findById(Long id) {
		Query query = manager.createQuery("Select u from Usuario u where u.id = :pId");
		query.setParameter("pId", id);
		return (Relatorio) query.getSingleResult();
	}
	
	public List<Relatorio> findByName2(String nome) {
		Query query = manager.createQuery("Select u from Relatorio u where u.livro_id like :pNome");
		query.setParameter("pNome", "%" + nome + "%");
		return query.getResultList();
	}

	public boolean save(Relatorio t) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<Relatorio> findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean delete(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

}
