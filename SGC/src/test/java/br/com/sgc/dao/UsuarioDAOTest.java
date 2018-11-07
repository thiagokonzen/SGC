package br.com.sgc.dao;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Ignore;
import org.junit.Test;

import br.com.sgc.domain.Pessoa;
import br.com.sgc.domain.Usuario;

public class UsuarioDAOTest {

	@Test
	//@Ignore
	public void salvar() {
		PessoaDAO pessoaDAO = new PessoaDAO();
		Pessoa pessoa = pessoaDAO.buscar(1L);

		System.out.println("Pessoa Encontrada");
		System.out.println("Nome: " + pessoa.getNome());
		System.out.println("CPF: " + pessoa.getCpf());

		Usuario usuario = new Usuario();
		usuario.setAtivo(true);
		usuario.setPessoa(pessoa);
		usuario.setSenhaSemCriptografia("123456");

		SimpleHash hash = new SimpleHash("md5", usuario.getSenhaSemCriptografia());
		usuario.setSenha(hash.toHex());

		usuario.setTipo('A');

		UsuarioDAO usuarioDAO = new UsuarioDAO();
		usuarioDAO.salvar(usuario);

		System.out.println("Usuário salvo com sucesso.");
	}
	
	@Test
	@Ignore
	public void autenticar(){
		String cpf = "111.111.111-11";
		String senha = "123456";
		
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		Usuario usuario = usuarioDAO.autenticar(cpf, senha);
		
		System.out.println("Usuário autenticado: " + usuario);
	}
}
