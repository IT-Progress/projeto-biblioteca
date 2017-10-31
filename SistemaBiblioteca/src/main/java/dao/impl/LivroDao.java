package dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import dao.DAO;
import dao.Transactional;
import model.Livro;
import model.Relatorio;
import model.SituacaoLivro;
import model.Usuario;


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

	public List<Livro> findByArquivo(byte[] arquivo){
		Query query = manager.createQuery("Select u from Livro u where u.arquivo = :pArquivo");
		query.setParameter("pArquivo", arquivo);
		return query.getResultList();
	}
	
	public List<Livro> findByName(String nome) {
		Query query = manager.createQuery("Select u from Livro u where lower(u.nome) like lower(:pNome)");
		query.setParameter("pNome", "%" + nome + "%");
		return query.getResultList();
	}

	public List<Livro> findByAutor(String autor) {
		Query query = manager.createQuery("Select u from Livro u where u.autor like :pAutor");
		query.setParameter("pAutor", "%" + autor + "%");
		return query.getResultList();
	}

	public List<Livro> findByCategoria(String categoria) {
		Query query = manager.createQuery("Select u from Livro u where u.categoria like :pCategoria");
		query.setParameter("pCategoria", "%" + categoria + "%");
		return query.getResultList();
	}

	public List<Livro> findByTag(String tag) {
		Query query = manager.createQuery("Select u from Livro u where u.tag like :pTag");
		query.setParameter("pTag", "%" + tag + "%");
		return query.getResultList();
	}

	public List<Livro> findBySituacao(String situacao) {
		Query query = manager.createQuery("Select u from Livro u where u.situacao like :pSituacao");
		query.setParameter("pAutor", "%" + situacao + "%");
		return query.getResultList();
	}

	public List<Livro> findByComputacao() {
		return manager.createQuery("Select u from Livro u where u.categoria like 'COMPUTAÇÃO' ").getResultList();
	}

	public List<Livro> findByAdministracao() {
		return manager.createQuery("Select u from Livro u where u.categoria like 'ADMINISTRAÇÃO' ").getResultList();
	}

	public List<Livro> findByOutros() {
		return manager.createQuery("Select u from Livro u where u.categoria like 'OUTROS' ").getResultList();
	}

	public List<Livro> findByString(String string) {
		Query query = manager.createQuery(
				"Select u from Livro u where lower(u.nome) like lower(:pString) OR lower(u.autor) like lower(:pString) OR lower(u.categoria) like lower(:pString) OR lower(u.tag) like lower(:pString) OR lower(u.situacao) like lower(:pString)");
		query.setParameter("pString", "%" + string + "%");
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
	
	@Transactional
	public boolean alugar(Livro livro, Usuario usuario) {
		try {
			livro.setSituacao(SituacaoLivro.INDISPONÍVEL);
			manager.merge(livro);
			
			Relatorio emprestimo = new Relatorio();
			emprestimo.setLivro(livro);
			emprestimo.setUsuario(usuario);
			emprestimo.setDataDeDevolucao(new Date());
			
			Date data = emprestimo.getDataDeDevolucao();
			Calendar devolucao = Calendar.getInstance();
			devolucao.setTime(data);
			devolucao.add(Calendar.DAY_OF_YEAR, 15);
			Date dataCalculada = devolucao.getTime();
	
			emprestimo.setDataDeDevolucao(dataCalculada);
			
			manager.persist(emprestimo);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}
