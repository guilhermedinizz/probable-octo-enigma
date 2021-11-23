package com.guilherme.accenture.driver;

import com.guilherme.accenture.base.Util;
import com.guilherme.accenture.util.Data;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class WebDriverFactory {
    // ------------------------------ FIELDS ------------------------------
    public static WebDriver webDriverInstance;
    static final Logger log = LoggerFactory.getLogger(WebDriverFactory.class.getSimpleName());
    static final HashMap<String, WebDriver> webDriverMap = new HashMap<>();
    static final String resourcePath = Util.pegarResourcePath("target/Download");

    // -------------------------- OTHERS METHODS --------------------------

    /**
     * Criando novo Driver Padrão
     */
    public static void criarNovoWebDriver() {
        criarNovoWebDriver(Data.get("browser"));
    }

    /**
     * Pegando a instancia do MAP e colocando na variavel de instancia atual
     *
     * @param instance Nome da instancia
     */
    public static void getWebDriverInstance(String instance) {
        webDriverInstance = webDriverMap.get(instance);
    }

    /**
     * Verificando qual driver pegar
     *
     * @param instance Nome da instancia
     */
    public static void criarNovoWebDriver(@NotNull String instance) {

        log.debug("Pegar driver padrão 'Firefox'");
        if (instance.equals(""))
            setBrowser(instance, FirefoxDriver.getDriver());

        log.debug("Pegando driver do Firefox");
        if (instance.equals("firefox"))
            setBrowser(instance, FirefoxDriver.getDriver());

        if (instance.equals("chrome"))
            setBrowser(instance, ChromeDriver.getDriver());

        if (instance.equals("chromeheadless"))
            setBrowser(instance, ChromeDriver.getDriver(true));
    }
    // -------------------------- PRIVATE METHODS --------------------------

    /**
     * Verificando se existe algum driver aberto com o nome da instancia se não abre um novo
     *
     * @param instance  Nome da instancia
     * @param webDriver Driver para colocar no Map
     */
    private static void setBrowser(String instance, WebDriver webDriver) {
        if (getKey(instance) != null)
            getWebDriverInstance(instance);
        else {
            webDriverMap.put(instance, webDriver);
            getWebDriverInstance(instance);
        }
    }

    /**
     * Retorna o key se ele existe
     *
     * @param key Nome da key
     * @return Retorna um String com o nome da key  se exitir
     */
    @Nullable
    private static String getKey(String key) {
        for (String aKey : webDriverMap.keySet()) {
            if ((aKey).equals(key))
                return aKey;
        }
        return null;
    }
    // -------------------------- END OF OTHERS METHODS --------------------------
}
