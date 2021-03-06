package br.edu.ufersa.ropke.locadoramaven.controller;

import br.edu.ufersa.ropke.locadoramaven.exception.InvalidParameterException;
import br.edu.ufersa.ropke.locadoramaven.exception.NotFoundException;
import br.edu.ufersa.ropke.locadoramaven.exception.AuthenticationException;
import br.edu.ufersa.ropke.locadoramaven.model.BO.FuncionarioBO;
import br.edu.ufersa.ropke.locadoramaven.model.BO.GerenteBO;
import br.edu.ufersa.ropke.locadoramaven.view.View;
import br.edu.ufersa.ropke.locadoramaven.view.ViewSwitcher;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
	@FXML
	private Label usuarioSenhaIncorretos;
	@FXML
	private TextField login;
	@FXML
	private PasswordField senha;

	@FXML
	public void entrar() {
		GerenteBO gerenteBO = new GerenteBO();
		String stringLogin = login.getText();
		String stringSenha = senha.getText();

		// Busca pelos gerentes
		try {
			if (gerenteBO.autenticar(stringLogin, stringSenha)) {
				usuarioSenhaIncorretos.setVisible(false);
				ViewSwitcher.switchTo(View.PRINCIPAL_GERENTE);
			}
		} catch (AuthenticationException | InvalidParameterException | NotFoundException e) {
			// Se dar errado, busca pelos funcionários
			FuncionarioBO funcionarioBO = new FuncionarioBO();

			try {
				if (funcionarioBO.autenticar(stringLogin, stringSenha)) {
					usuarioSenhaIncorretos.setVisible(false);
					ViewSwitcher.switchTo(View.PRINCIPAL_FUNCIONARIO);
				}
			} catch (AuthenticationException | InvalidParameterException | NotFoundException f) {
				usuarioSenhaIncorretos.setVisible(true);
			}
		}
	}

	@FXML
	public void alterarSenha() {
		ViewSwitcher.switchTo(View.ALTERA_SENHA);
	}
}
