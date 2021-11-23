package com.guilherme.accenture.pageobjects;

import com.guilherme.accenture.base.BaseForPages;
import com.guilherme.accenture.base.Wait;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@ConfigurationProperties(prefix = "enviarcotacao")
public class EnviarCotacaoPage extends BaseForPages {

    private final By divContainer = By.id("idealsteps-container");
    private final By formulario = By.id("insurance-form");
    private final By inputEmail = By.id("email");
    private final By inputTelefone = By.id("phone");
    private final By inputUsername = By.id("username");
    private final By inputSenha = By.id("password");
    private final By inputConfiarmarSenha = By.id("confirmpassword");
    private final By inputComentario = By.id("Comments");
    private final By buttonEnviar = By.id("sendemail");
    private final By textMensagem = By.id("/html/body/div[4]/h2");

    public void aguardarPaginaCarregar() {
        if (Wait.setWait(() -> Wait.visibilityOfElementLocated(divContainer))) {
            Wait.setWait(2000);
            Assert.assertTrue("O formulario não está aparecendo", isDisplayed(formulario));
        }
    }

    public void preencherEmail(String email) {
        log.debug("preenchendoEmail");
        preencherCampo(inputEmail, email);
    }

    public void preencherTelefone(String telefone) {
        log.debug("preenchendoTelefone");
        preencherCampo(inputTelefone, telefone);
    }

    public void preencherUsername(String username) {
        log.debug("preenchendoUsername");
        preencherCampo(inputUsername, username);
    }

    public void preencherSenha(String senha) {
        log.debug("preenchendoSenha");
        preencherCampo(inputSenha, senha);
    }

    public void preencherConfirmarSenha(String senha) {
        log.debug("preenchendoConfirmarSeha");
        preencherCampo(inputConfiarmarSenha, senha);
    }

    public void preencherCpmentario(String cometario) {
        log.debug("preenchendoComentario");
        preencherCampo(inputComentario, cometario);
    }

    public void clicarEmEnviar() {
        log.debug("clicarEmEnviar");
        clicar(buttonEnviar);
    }

    public void validarMensagem(String mensagem) {
        log.debug("validarMensagem");
        List<WebElement> mensagens = checkElements(textMensagem);
        Optional<WebElement> elementMsg = mensagens.stream()
                .filter(m -> m.getText().equals(mensagem))
                .findAny();
        Assert.assertFalse("Algum campo não foi preenchido", elementMsg.isPresent());
    }

}
