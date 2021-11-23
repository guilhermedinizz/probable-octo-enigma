package com.guilherme.accenture.pageobjects;

import com.guilherme.accenture.base.BaseForPages;
import com.guilherme.accenture.base.Wait;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "paginainicial")
public class PaginaInicial extends BaseForPages {

    @Value("${app.baseurl}")
    private String baseurl;
    private final By divSupport = By.id("visitsupport");
    private final By divContent = By.id("site-content");
    private final By menuCarro = By.id("nav_automobile");
    private final By menuCaminhao = By.id("nav_truck");
    private final By menuMoto = By.id("nav_motorcycle");
    private final By menuCamper = By.id("nav_camper");


    public void iniciar() {
        acessarUrl(baseurl);
        if (Wait.setWait(() -> Wait.visibilityOfElementLocated(divSupport))) {
            Wait.setWait(2000);
            Assert.assertTrue("A div com conteudo não apareceu", isDisplayed(divContent));
        }
    }

    public void clicarSeguroParaUmTipo(String tipoVeiculo) {
        log.debug("clicarSeguroParaUmTipo");
        tipoVeiculo = tipoVeiculo.toLowerCase();
        if (tipoVeiculo.equals("carro")) {
            clicar(menuCarro);
        } else if (tipoVeiculo.equals("caminhao")) {
            clicar(menuCaminhao);
        } else if (tipoVeiculo.equals("moto")) {
            clicar(menuMoto);
        } else {
            clicar(menuCamper);
        }
    }

}
