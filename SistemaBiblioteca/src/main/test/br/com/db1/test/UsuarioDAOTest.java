package br.com.db1.test;

import org.junit.Test;

import dao.impl.UsuarioDao;
import model.Usuario;

public class UsuarioDAOTest {

	@Test
	public void autenticar() {
		UsuarioDao UsuarioDao = new UsuarioDao();
		
		Usuario usuario = UsuarioDao.autenticacao("oi", "oi");
		
		System.out.println("Usuario: " + usuario);
	}
}
