package com.guilherme.accenture.pageobjects;

import com.guilherme.accenture.base.BaseForPages;
import com.guilherme.accenture.base.Wait;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "dadossegurado")
public class DadosDoSeguradoPage extends BaseForPages {

    private final By divContainer = By.id("idealsteps-container");
    private final By formulario = By.id("insurance-form");
    private final By inputNome = By.id("firstname");
    private final By inputSobrenome = By.id("lastname");
    private final By inputDataAniversario = By.id("birthdate");
    private final By buttonMasculino = By.xpath("//*[@id='insurance-form']/div/section[2]/div[4]/p/label[1]/span");
    private final By buttonFeminino = By.xpath("//*[@id='insurance-form']/div/section[2]/div[4]/p/label[2]/span");
    private final By inputRua = By.id("streetaddress");
    private final By selectPais = By.id("country");
    private final By inputCep = By.id("zipcode");
    private final By inputCidade = By.id("city");
    private final By selectOcupacao = By.id("occupation");
    private final By checkBoxHobbie = By.xpath("//*[@id='insurance-form']/div/section[2]/div[10]/p/label[1]/span");
    private final By inputWebSite = By.id("website");
    private final By buttonNext = By.id("nextenterproductdata");

    public void aguardarPaginaCarregar() {
        if (Wait.setWait(() -> Wait.visibilityOfElementLocated(divContainer))) {
            Wait.setWait(2000);
            Assert.assertTrue("O formulario não está aparecendo", isDisplayed(formulario));
        }
    }

    public void preencherCampoDoNome(String nome) {
        log.debug("preenchendoNome");
        preencherCampo(inputNome, nome);
    }

    public void preencherCampoDoSobrenome(String sobrenome) {
        log.debug("preenchendoNome");
        preencherCampo(inputSobrenome, sobrenome);
    }

    public void preencherCampoDeAniversario(String data) {
        log.debug("preenchendoAniversario");
        preencherCampo(inputDataAniversario, data);
    }

    public void selecionarGenero(boolean eFeminino) {
        if (eFeminino) {
            clicar(buttonFeminino);
        } else {
            clicar(buttonMasculino);
        }
    }

    public void preencherRua(String rua) {
        log.debug("preencherRua");
        preencherCampo(inputRua, rua);
    }

    public void selecionarPais(String pais) {
        log.debug("selecionarPais");
        selecionarCombo(selectPais, pais);
    }

    public void preencherCampoCep(String cep) {
        log.debug("preencherCep");
        preencherCampo(inputCep, cep);
    }

    public void preencherCidade(String cidade) {
        log.debug("preencherCidade");
        preencherCampo(inputCidade, cidade);
    }

    public void selecionarOcupacao(String ocupacao) {
        log.debug("selecionarOcupacao");
        selecionarCombo(selectOcupacao, ocupacao);
    }

    public void clicarEmUmHobbie() {
        log.debug("clicandoEmUmHobbie");
        clicar(checkBoxHobbie);
    }

    public void preencherWebSite(String site) {
        log.debug("preencherCidade");
        preencherCampo(inputWebSite, site);
    }

    public void clicarEmNext() {
        log.debug("clicarEmNext");
        clicar(buttonNext);
    }

}
