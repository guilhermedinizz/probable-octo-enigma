package com.guilherme.accenture.pageobjects;

import com.guilherme.accenture.base.BaseForPages;
import com.guilherme.accenture.base.Wait;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@ConfigurationProperties(prefix = "dadosdoproduto")
public class DadosDoProdutoPage extends BaseForPages {

    private final By divContainer = By.id("idealsteps-container");
    private final By formulario = By.id("insurance-form");
    private final By inputDataInicial = By.id("startdate");
    private final By selectValorMaximoDoSeguro = By.id("insurancesum");
    private final By selectMeritRating = By.id("meritrating");
    private final By selectSeguroDeDanos = By.id("damageinsurance");
    private final By buttonEuroProtection = By.xpath("//*[@id='insurance-form']/div/section[3]/div[5]/p/label[1]/span | //*[@id=\"insurance-form\"]/div/section[3]/div[4]/p/label[1]/span");
    private final By buttonLegalDefenseInsurance = By.xpath("//*[@id='insurance-form']/div/section[3]/div[5]/p/label[2]/span");
    private final By selectCarroReserva = By.id("courtesycar");
    private final By buttonNext = By.id("nextselectpriceoption");

    public void aguardarPaginaCarregar() {
        if (Wait.setWait(() -> Wait.visibilityOfElementLocated(divContainer))) {
            Wait.setWait(2000);
            Assert.assertTrue("O formulario não está aparecendo", isDisplayed(formulario));
        }
    }

    public void preencherDataInicial(String data) {
        log.debug("preencherDataInical");
        preencherCampo(inputDataInicial, data);
    }

    public void selecionarValorMaximoDoSeguro(String valor) {
        log.debug("selecionarValorMaximoDoSeguro");
        selecionarCombo(selectValorMaximoDoSeguro, valor);
    }

    public void selecionarMeritRating(String meritRating) {
        log.debug("selecionarMeritRating");
        selecionarCombo(selectMeritRating, meritRating);
    }

    public void selecionarSeguroDeDanos(String seguroDeDanos) {
        log.debug("selecionarSeguroDeDanos");
        selecionarCombo(selectSeguroDeDanos, seguroDeDanos);
    }

    public void clicarEuroProtection() {
        log.debug("clicarEuroProtection");
        clicar(buttonEuroProtection);
    }

    public void clicarLegalDefenseInsurance() {
        log.debug("clicarLegalDefenseInsurance");
        clicar(buttonLegalDefenseInsurance);
    }

    public void selecionarCarroReserva(String carroReserva) {
        log.debug("selecionarCarroReserva");
        selecionarCombo(selectCarroReserva, carroReserva);
    }

    public void clicarEmNext() {
        log.debug("clicarEmNext");
        clicar(buttonNext);
    }

    public void preencherDadosDoProduto(String valor, String meriRanting, String coberturaPorDano, String tipoDoVeiculo) {
        tipoDoVeiculo = tipoDoVeiculo.toLowerCase();
        preencherDataInicial(LocalDate.now().plusDays(31).format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        selecionarValorMaximoDoSeguro(valor);
        if (tipoDoVeiculo.equals("carro")) {
            selecionarMeritRating(meriRanting);
            selecionarCarroReserva("Yes");
        }
        selecionarSeguroDeDanos(coberturaPorDano);

        clicarEuroProtection();

    }
}
