package br.com.sgc.bean;

import java.io.Serializable;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import br.com.sgc.dao.ClienteDAO;
import br.com.sgc.dao.PessoaDAO;
import br.com.sgc.domain.Cliente;
import br.com.sgc.domain.Pessoa;
import br.com.sgc.util.HibernateUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ClienteBean implements Serializable {

	private Cliente cliente;
	private List<Cliente> clientes;
	private List<Pessoa> pessoas;

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	@PostConstruct
	public void listar() {
		try {
			ClienteDAO clienteDAO = new ClienteDAO();
			clientes = clienteDAO.listar("dataCadastro");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os clientes");
			erro.printStackTrace();
		}

	}

	public void novo() {
		try {
			cliente = new Cliente();

			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoas = pessoaDAO.listar("nome");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar criar um novo cliente");
			erro.printStackTrace();
		}

	}

	public void editar(ActionEvent evento) {
		try {
			cliente = (Cliente) evento.getComponent().getAttributes().get("clienteSelecionada");

			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoas = pessoaDAO.listar("nome");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar remover um cliente");
			erro.printStackTrace();
		}

	}

	public void salvar() {
		try {
			ClienteDAO clienteDAO = new ClienteDAO();
			clienteDAO.merge(cliente);

			cliente = new Cliente();

			clientes = clienteDAO.listar("dataCadastro");

			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoas = pessoaDAO.listar("nome");

			Messages.addGlobalInfo("Cliente salvo com sucesso");

		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar salvar um cliente");
			erro.printStackTrace();
		}

	}

	public void excluir(ActionEvent evento) {
		try {
			cliente = (Cliente) evento.getComponent().getAttributes().get("clienteSelecionada");
			ClienteDAO clienteDAO = new ClienteDAO();
			clienteDAO.excluir(cliente);

			clientes = clienteDAO.listar();

			Messages.addGlobalInfo("Cliente removido com sucesso");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar remover um clinte");
			erro.printStackTrace();
		}
	}

	public void imprimir() {
		try {
			String caminho = Faces.getRealPath("/reports/clientes.jasper");

			Map<String, Object> parametros = new HashMap<>();

			Connection conexao = HibernateUtil.getConexao();

			JasperPrint relatorio = JasperFillManager.fillReport(caminho, parametros, conexao);

			JasperPrintManager.printReport(relatorio, true);
		} catch (JRException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar gerar o relatório");
			erro.printStackTrace();
		}
	}
}
