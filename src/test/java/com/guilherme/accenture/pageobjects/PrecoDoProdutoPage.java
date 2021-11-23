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
@ConfigurationProperties(prefix = "precodoproduto")
public class PrecoDoProdutoPage extends BaseForPages {

    private final By divContainer = By.id("idealsteps-container");
    private final By formulario = By.id("insurance-form");
    private final By textMensagem = By.xpath("//*[@id='xLoaderPrice']/p");
    private final By buttonSilver = By.xpath("//*[@id='priceTable']/tfoot/tr/th[2]/label[1]/span");
    private final By buttonGold = By.xpath("//*[@id='priceTable']/tfoot/tr/th[2]/label[2]/span");
    private final By buttonPlatinum = By.xpath("//*[@id='priceTable']/tfoot/tr/th[2]/label[3]/span");
    private final By buttonUltimate = By.xpath("//*[@id='priceTable']/tfoot/tr/th[2]/label[4]/span");
    private final By buttonNext = By.id("nextsendquote");

    public void aguardarPaginaCarregar() {
        if (Wait.setWait(() -> Wait.visibilityOfElementLocated(divContainer))) {
            Wait.setWait(2000);
            Assert.assertTrue("O formulario não está aparecendo", isDisplayed(formulario));
        }
    }

    public void validarMensagem(String mensagem) {
        log.debug("validarMensagem");
        List<WebElement> mensagens = checkElements(textMensagem);
        Optional<WebElement> elementMsg = mensagens.stream()
                .filter(m -> m.getText().equals(mensagem))
                .findAny();
        Assert.assertFalse("Algum campo não foi preenchido", elementMsg.isPresent());
    }

    public void selecionarProduto(String produto) {
        log.debug("selecionarProduto");
        produto = produto.toLowerCase();
        if (produto.equals("silver")) {
            clicar(buttonSilver);
        } else if (produto.equals("gold")) {
            clicar(buttonGold);
        } else if (produto.equals("platinum")) {
            clicar(buttonPlatinum);
        } else {
            clicar(buttonUltimate);
        }
    }

    public void clicarEmNext() {
        clicar(buttonNext);
    }
}
