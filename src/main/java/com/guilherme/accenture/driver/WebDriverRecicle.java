package com.guilherme.accenture.driver;

import org.jetbrains.annotations.Contract;

public class WebDriverRecicle extends WebDriverFactory {
    // -------------------------- OTHERS METHODS --------------------------

    /**
     * Veririfa se o Driver atual esta aberto
     *
     * @return Retorna um Boolean se esta ou não aberto o driver
     */
    @Contract(pure = true)
    public static boolean verificarWebDriverAberto() {
        return webDriverInstance != null;
    }

    /**
     * Recicla o Driver Atual
     */
    public static void recicleWebDriver() {
        closeWebDriver();
    }

    // -------------------------- PRIVATE METHODS --------------------------

    /**
     * Recicla o Driver
     */
    private static void closeWebDriver() {
        webDriverMap.forEach((key, webDriver) -> webDriver.quit());
        webDriverMap.clear();
    }
    // -------------------------- END OF OTHERS METHODS --------------------------
}
