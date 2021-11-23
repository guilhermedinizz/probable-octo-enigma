package com.guilherme.accenture.base;

import com.guilherme.accenture.driver.WebDriverRecicle;
import com.guilherme.accenture.util.Data;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.guilherme.accenture.base.Wait.*;
import static com.guilherme.accenture.driver.WebDriverFactory.webDriverInstance;

public class Page {
    // ------------------------------ FIELDS ------------------------------
    protected static final Logger log = LoggerFactory.getLogger(Page.class.getSimpleName());
    public static String screenshotPath = "";

    // -------------------------- OTHER METHODS --------------------------

    /**
     * Pega uma lista de elemento apartir do localizador
     *
     * @param locator localizador do WebElement
     * @return retorna uma lista de elemento referenciado pelo localizador
     */
    private static List<WebElement> getElements(By locator) {
        log.debug("retorna uma lista de elemento referenciado pelo localizador...");
        return webDriverInstance.findElements(locator);
    }

    /**
     * Retorna o valor da URL que esta navegendo no momento;
     *
     * @return String da URL;
     */
    public static String pegarURLAtual() {
        log.debug("Pegando URL Atual...");
        return webDriverInstance.getCurrentUrl();
    }

    /**
     * Checks if a given element exists in page
     *
     * @param locator The element selector by *name*
     * @return true if element exists or false otherwise
     */
    public static boolean existsElement(By locator) {
        log.debug("Verifica se existe o elemento passado por parametro na tela...");
        return getElements(locator).size() != 0;
    }

    /**
     * Esconde um elemento
     *
     * @param locator localizador do elemento By cssDelector
     */
    public static void hideElement(By locator) {
        log.debug("Escondendo o elemento...");
        js("arguments[0].style.display='none'", getElement(locator));
    }

    /**
     * Faz highlight no elemento
     *
     * @param locator localizador do elemento
     */
    protected static void highlightElement(By locator) {
        log.debug("Fazendo highlight no elemento...");
        try {
            js("arguments[0].style.border='3px solid " + Data.get("color") + "'", locator);
        } catch (Exception e) {
            log.debug("Não pode aplicar o highligth", e);
        }
    }

    /**
     * Pega o webElement paratir do localizador
     *
     * @param locator localizador do elemento
     * @return retorna o WebElement refereniado pelo localizador
     */
    private static WebElement getElement(By locator) {
        log.debug("Pega o webElement partir do localizador...");
        highlightElement(locator);
        return webDriverInstance.findElement(locator);
    }

    /**
     * Executa comandos do tipo JavaScript
     *
     * @param script  Comando que deve ser executado
     * @param objects Objeto para complementar o comando
     * @return Se tiver resultado, Retorna um objeto com as informaçoes que o script retorna
     */
    private static Object js(String script, Object... objects) {
        return ((JavascriptExecutor) webDriverInstance).executeScript(script, objects);
    }

    /**
     * Tirar um Print em formato png da tela.
     * Lembre-se que arquivos de mesmo nome serão sobrescritos
     *
     * @param nomeArquivo Nome do print que sera feito;
     */
    public static void capturarScreenshot(String nomeArquivo, boolean paginaInteira) {
        log.debug("Pegando Print da tela...");
        String nomeMetodo = "capturarScreenshot";

        setWait();

        if (!("".equals(nomeArquivo) || nomeArquivo == null)) {

            nomeArquivo = nomeArquivo.replaceAll(":", "_");
            nomeArquivo = nomeArquivo.replaceAll(" ", "");

            try {
                if (WebDriverRecicle.verificarWebDriverAberto()) {
                    File diretorio = new File("target/screenshots");
                    if (!diretorio.exists()) {
                        log.debug("Nao existe! \nCriando");
                        diretorio.mkdirs();
                    }

                    log.debug(String.format("Diretorio saida prints: %s", diretorio.getCanonicalPath()));

                    File destFile = new File(diretorio + "/" + nomeArquivo + ".png");
                    File arquivo = ((TakesScreenshot) webDriverInstance).getScreenshotAs(OutputType.FILE);
                    FileUtils.copyFile(arquivo, destFile);

                    log.debug(String.format("Gravando screenshot: %s", destFile.getCanonicalPath()));

                    if (diretorio.getCanonicalPath().contains("/agent/tcagent"))
                        screenshotPath = nomeArquivo + ".png";
                    else
                        screenshotPath = "screenshots/" + nomeArquivo + ".png";

                } else
                    screenshotPath = "";
            } catch (IOException e) {
                log.warn(String.format("\n\n**************Erro!!**************\n\n%s\n\n%s --> %s", e, nomeMetodo, nomeArquivo));
            }
        }
    }

    /**
     * Acessa a URL indicada
     *
     * @param url URL para acessar
     */
    public void acessarUrl(String url) {
        log.debug("acessar a url");
        webDriverInstance.get(url);
    }

    /**
     * Atualiza a janela
     */
    public void atualizarTela() {
        log.debug("Refresh da tela...");
        webDriverInstance.navigate().refresh();
    }

    /**
     * Clica no botao passado no localizador
     *
     * @param locator Localizador By do elemento.
     */
    public void clicar(By locator) {
        log.debug("Clicando no botão... " + locator.toString());
        setWait(() -> Wait.elementToBeClickable(locator));

        while (existsElement(By.xpath("//svg"))) {
            setWait(1000);
            log.info("Aguardando carregamento...");
        }

        clicar(locator, checkElement(locator));
    }

    /**
     * Realiza a rolagem da tela
     */
    public void scrollToBottomPage() {
        Actions actions = new Actions(webDriverInstance);
        actions.keyDown(Keys.CONTROL).sendKeys(Keys.END).perform();
        actions.keyDown(Keys.CONTROL).release().perform();
    }

    /**
     * Custom
     * Realiza a rolagem da tela com Javascript
     */
    public void scrollToElementJS(WebElement element) {
        try {
            int Y = (element.getLocation().getY() - 500);
            js("javascript:window.scrollTo(0," + Y + ");");
        } catch (Exception e) {
            log.info("Elemento não encontrado via Javascript");
        }
    }

    /**
     * Pega o texto de um elemento pelo locator dele
     *
     * @param locator locatod do elemento
     * @return retona o texto do elemento
     */
    public String getText(By locator) {
        log.debug("Pega texto de um elemento");

        setWait(() -> visibilityOfElementLocated(locator));

        WebElement element = checkElement(locator);

        return (element != null) ?
                element.getText() : getElement(locator).getText();
    }

    /**
     * Pega o texto de um elemento pelo Element dele
     *
     * @param element Elemento
     * @return retorena o texto do elemento
     */
    protected String getText(WebElement element) {
        log.debug("Pega texto de um elemento");
        setWait(() -> visibilityOf(element));
        return element.getText();
    }

    /**
     * Preenche um campo
     *
     * @param locator localizador do campo
     * @param texto   Texto que ira preencher o campo
     * @param keyss   Comando para mandar de teclado
     */
    public WebElement preencherCampo(By locator, String texto, Keys... keyss) {
        log.debug("Preenche qualquer campo...");
        setWait(() -> visibilityOfElementLocated(locator));

        WebElement element = checkElement(locator);

        privatePreencherCampo(locator, texto, element, keyss);

        return element;
    }

    /**
     * Apenas a operação de limpar e preencher
     *
     * @param element elemento
     * @param texto   texto a ser preenchido
     */
    public void preencherCampo(WebElement element, String texto) {
        try {
            element.clear();
            element.sendKeys(texto);
        } catch (InvalidElementStateException e) {
            Actions performAct = new Actions(webDriverInstance);
            performAct.sendKeys(element, texto).build().perform();
        } catch (Exception e) {
            js("arguments[0].value='" + texto + "';", element);
        }
    }

    /**
     * Seleciona opção num combo
     *
     * @param locator localizador do campo
     * @param texto   texto que ira selecionar
     */
    public void selecionarCombo(By locator, String texto) {
        log.debug("Seleciona opção num combo...");

        setWait(() -> visibilityOfElementLocated(locator));
        WebElement element = checkElement(locator);

        Select dropdown = new Select(element);
        dropdown.selectByVisibleText(texto);
    }

    /**
     * Veririca se esta apacendo o elemento
     *
     * @param locator localizador do elemento
     * @return se estiver aparecendo o elemento, retorna verdadeiro
     */
    protected Boolean isDisplayed(By locator) {
        log.debug("Verirficando se esta mostrando na tela");
        setWait(locator);
        WebElement element = checkElement(locator);

        return (element != null) ?
                element.isDisplayed() : getElement(locator).isDisplayed();
    }

    /**
     * Verifica se a lista de elementos esta visivel na tela
     *
     * @param locator localizador do tipo By
     * @return Retorna a listasta verificada de elemento aparetir do localizador
     */
    @NotNull
    protected List<WebElement> checkElements(By locator) {
        log.debug("Verificando uma lista de elemento...");

        setWait(() -> presenceOfAllElementsLocatedBy(locator));
        List<WebElement> listaElementos = getElements(locator);

        for (WebElement element : listaElementos) {
            if (visibilityOf(element))
                highlightElement(element);
        }
        return listaElementos;
    }

    /**
     * Localiza e evidencia o elemento
     *
     * @param locator localizador do elemento
     * @return elemento que passar pelas condições
     */
    protected WebElement checkElement(By locator) {
        log.debug("verificando os elementos passador por locator ou por lista de elemento...");

        setWait(() -> presenceOfAllElementsLocatedBy(locator));
        WebElement element = getElement(locator);

        if (visibilityOf(element))
            highlightElement(element);

        return element;
    }

    /**
     * Preenche um campo
     *
     * @param locator locator do elemento
     * @param texto   texto para ser preenchido
     * @param element elemento que deve ser preenchido
     * @param keyss   se precisar fazer um comando, mandar um elemento do tipo KEYS
     */
    private void privatePreencherCampo(By locator, String texto, WebElement element, Keys[] keyss) {
        int cont = 0;
        List<String> stringList = new ArrayList<>();

        if (texto != null) {
            while (cont != texto.length())
                stringList.add(texto.substring(cont, cont++ + 1));
        }

        if (element != null) {
            if (keyss != null) {
                if (texto != null)
                    preencherCampo(element, texto);

                for (Keys keys : keyss) {
                    setWait(500); // sem este Wait 'atropela' o auto-complete
                    try {
                        element.sendKeys(keys);
                    } catch (Exception e) {
                        Actions performAct = new Actions(webDriverInstance);
                        performAct.sendKeys(element, keys).build().perform();
                    }
                }

            } else if (texto != null)
                preencherCampo(element, texto);

        } else
            log.error(String.format("Elemento não apresentado na tela... %s", locator));
    }

    /**
     * Clicando no elemento por Driver ou por JS se não der certo por Driver
     *
     * @param locator Locator do elemento
     * @param element Elemento retirado do Locator
     */
    private void clicar(By locator, WebElement element) {
        if (element != null)
            element.click();
        else checkElement(locator).click();
    }

    /**
     * Faz highlight no elemento
     *
     * @param element elemento
     */
    private void highlightElement(WebElement element) {
        log.debug("Fazendo highlight no elemento...");
        js("arguments[0].style.border='3px solid " + Data.get("color") + "'", element);
    }
    //--------------------------------- END OF OTHER METHODS ------------------------------------
}
