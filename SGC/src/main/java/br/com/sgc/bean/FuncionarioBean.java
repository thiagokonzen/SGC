package br.com.sgc.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.sgc.dao.FuncionarioDAO;
import br.com.sgc.dao.PessoaDAO;
import br.com.sgc.domain.Funcionario;
import br.com.sgc.domain.Pessoa;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class FuncionarioBean implements Serializable {
	private Funcionario funcionario;

	private List<Pessoa> pessoas;
	private List<Funcionario> funcionarios;

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	@PostConstruct
	public void listar() {
		try {
			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			funcionarios = funcionarioDAO.listar("carteiraTrabalho");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os funcionários");
			erro.printStackTrace();
		}
	}

	public void novo() {
		try {
			funcionario = new Funcionario();

			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoas = pessoaDAO.listar("nome");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar criar um novo funcionario");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			funcionarioDAO.merge(funcionario);

			funcionario = new Funcionario();
			funcionarios = funcionarioDAO.listar("carteiraTrabalho");

			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoas = pessoaDAO.listar("nome");

			// Messages.addGlobalInfo(Faces.getResourceBundle("msg").getString("usuarioSalvo"));
			Messages.addGlobalInfo("Funcionário salvo com sucesso");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o funcionario");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			funcionario = (Funcionario) evento.getComponent().getAttributes().get("funcionarioSelecionada");
			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			funcionarioDAO.excluir(funcionario);
			
			funcionarios = funcionarioDAO.listar();
			

			Messages.addGlobalInfo("Funcionário removido com sucesso");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar remover um funcionário");
			erro.printStackTrace();
		}

	}

	public void editar(ActionEvent evento) {
		try {
			funcionario = (Funcionario) evento.getComponent().getAttributes().get("funcionarioSelecionada");

			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoas = pessoaDAO.listar("nome");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar remover um funcionário");
			erro.printStackTrace();
		}

	}

}
