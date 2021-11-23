package com.guilherme.accenture.pageobjects;

import com.guilherme.accenture.base.BaseForPages;
import com.guilherme.accenture.base.Wait;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@ConfigurationProperties(prefix = "dadosveiculos")
public class DadosVeiculosPage extends BaseForPages {

    private final By selectMarca = By.id("make");
    private final By selectModelo = By.id("model");
    private final By inputCilindrada = By.id("cylindercapacity");
    private final By inputPotenciaDoMotor = By.id("engineperformance");
    private final By inputDataDeFabicacao = By.id("dateofmanufacture");
    private final By selectNumeroDeAssentos = By.id("numberofseats");
    private final By selectNumeroDeAssentosMoto = By.id("numberofseatsmotorcycle");
    private final By buttonSimDirecaoDireita = By.xpath("//*[@id=\"insurance-form\"]/div/section[1]/div[5]/p/label[1]/span");
    private final By buttonNaoDirecaoDireita = By.xpath("//*[@id=\"insurance-form\"]/div/section[1]/div[5]/p/label[2]/span");
    private final By selectipoDeCombustivo = By.id("fuel");
    private final By inputCapacidadeDeCarga = By.id("payload");
    private final By inputPesoVeiculo = By.id("totalweight");
    private final By inputPrecoDeTabela = By.id("listprice");
    private final By inputNumeroDaPlaca = By.id("licenseplatenumber");
    private final By inputMilhas = By.id("annualmileage");
    private final By divDadosVeiculos = By.className("idealsteps-wrap");
    private final By buttonNext = By.id("nextenterinsurantdata");

    public void aguardarPaginaCarregar() {
        if (Wait.setWait(() -> Wait.visibilityOfElementLocated(divDadosVeiculos))) {
            Wait.setWait(2000);
            Assert.assertTrue("O formulario não está aparecendo", isDisplayed(divDadosVeiculos));
        }
    }

    public void selecionarMarca(String marca) {
        log.debug("selecionarMarca");
        selecionarCombo(selectMarca, marca);
    }

    public void selecionarModelo(String modelo) {
        log.debug("selecionarModelo");
        selecionarCombo(selectModelo, modelo);
    }

    public void preencherCampoDeCilindrada(String cilindrada) {
        log.debug("preenchendoPotencia");
        preencherCampo(inputCilindrada, cilindrada);
    }

    public void preencherCampoDePotencia(String potencia) {
        log.debug("preenchendoPotencia");
        preencherCampo(inputPotenciaDoMotor, potencia);
    }

    public void preencherDataDeFabicacao(String data) {
        log.debug("preenchendoData");
        preencherCampo(inputDataDeFabicacao, data);
    }

    public void selecionarDirecaoDireita(Boolean direcaoNaDireita) {
        log.debug("selecionarDirecaoADireita");
        if (direcaoNaDireita) {
            clicar(buttonSimDirecaoDireita);
        } else {
            clicar(buttonNaoDirecaoDireita);
        }
    }

    public void selecionarNumerosDeAssento(String numeroDeAssentos) {
        log.debug("selecionarNumeroDeAssentos");
        selecionarCombo(selectNumeroDeAssentos, numeroDeAssentos);
    }

    private void selecionarNumerosDeAssentoMoto(String assetos) {
        log.debug("selecionarNumerosDeAssentoMoto");
        selecionarCombo(selectNumeroDeAssentosMoto, assetos);
    }

    public void selecionarTipoDeCombustivel(String tipoDeCombustivel) {
        log.debug("selecionarTipoDeCombustivel");
        selecionarCombo(selectipoDeCombustivo, tipoDeCombustivel);
    }

    public void preencherCampoDeCapacidadeDeCarga(String capacidade) {
        log.debug("preenchendoCapacidade");
        preencherCampo(inputCapacidadeDeCarga, capacidade);
    }

    public void preencherCampoDePesoDoVeiculo(String peso) {
        log.debug("preenchendoPeso");
        preencherCampo(inputPesoVeiculo, peso);
    }

    public void preencherCampoValorDeTabela(String valor) {
        log.debug("preenchendoPeso");
        preencherCampo(inputPrecoDeTabela, valor);
    }

    public void preencherCampoDaPlaca(String placa) {
        log.debug("preenchendoPlaca");
        preencherCampo(inputNumeroDaPlaca, placa);
    }

    public void preencherCampoDeMilhasAnuais(String milhas) {
        log.debug("preenchendoMilhas");
        preencherCampo(inputMilhas, milhas);
    }

    public void clicarEmNext() throws IOException {
        log.debug("clicarEmNext");
        clicar(buttonNext);
    }

    public void preencherInformacoesPeloTipoDeVeiculo(String tipoDoVeiculo) {
        tipoDoVeiculo = tipoDoVeiculo.toLowerCase();
        selecionarMarca("Audi");
        if (tipoDoVeiculo.equals("moto")) {
            selecionarModelo("Scooter");
            preencherCampoDeCilindrada("1000");
        }
        preencherCampoDePotencia("100");
        preencherDataDeFabicacao("12/01/2017");

        if (tipoDoVeiculo.equals("moto")) {
            selecionarNumerosDeAssentoMoto("2");
        } else {
            selecionarNumerosDeAssento("2");
        }
        if (!tipoDoVeiculo.equals("moto")) {
            selecionarTipoDeCombustivel("Gas");
            preencherCampoDaPlaca("1234");
        }
        if (tipoDoVeiculo.equals("caminha") || tipoDoVeiculo.equals("camper")) {
            preencherCampoDePesoDoVeiculo("5000");
            preencherCampoDeCapacidadeDeCarga("1000");
        }
        if (tipoDoVeiculo.equals("camper")) {
            selecionarDirecaoDireita(true);
        }
        preencherCampoValorDeTabela("1000");
        preencherCampoDeMilhasAnuais("10000");
    }


}
