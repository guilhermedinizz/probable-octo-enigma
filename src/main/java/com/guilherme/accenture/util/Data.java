package com.guilherme.accenture.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

public class Data {
    public static final Properties p = System.getProperties();
    // ------------------------------ FIELDS ------------------------------
    private final static Logger log = LoggerFactory.getLogger(Data.class.getSimpleName());
    private static final HashMap<String, String> data = new HashMap<>();
    // -------------------------- OTHER METHODS --------------------------

    /**
     * Grava a propriedade que deseja (com log)
     *
     * @param nomeProperties  Nome para a propriedade
     * @param valorProperties Valor que deseja dar a propriedade
     */
    public static void set(String nomeProperties, String valorProperties) {
        set(nomeProperties, valorProperties, true);
    }

    /**
     * Grava a propriedade que deseja (sem log)
     *
     * @param nomeProperties  Nome para a propriedade
     * @param valorProperties Valor que deseja dar a propriedade
     */
    public static void set(String nomeProperties, String valorProperties, boolean writeLog) {
        remove(nomeProperties);
        data.put(nomeProperties, valorProperties);

        if (writeLog)
            log.info("Gravando... " + nomeProperties + " = " + valorProperties);
    }

    /**
     * Pega a propriedade desejada
     *
     * @param nomeProperties Nome da propriedade que deseja pegar
     * @return Retorna o valor dado a propriedade anteriormente
     */
    public static String get(String nomeProperties) {
        String valor = data.get(nomeProperties);

        if (valor == null) {
            log.debug("Tentando pegar do System Properties...");
            log.debug("Properties - " + nomeProperties + "...");
            valor = p.getProperty(nomeProperties);
        }

        log.debug("Pegando valor... " + nomeProperties + " = " + valor);
        return valor;
    }

    /**
     * Pega de um arquivo os valores predefinodos para gravar como propriedade
     *
     * @param file nome do arquivo para pegar as propriedades
     */
    public static void getResourceProperties(String file) {
        try {
            InputStream resource = Data.class.getClassLoader().getResourceAsStream(file);
            p.load(resource);
        } catch (Exception e) {
            log.warn("Falha ao tentar ler as propriedades...\n" + e);
        }
    }

    /**
     * Remove a propriedade com o valor da memoria
     *
     * @param nomeProperites nome da propriedade que deseja que seja removido
     */
    public static void remove(String nomeProperites) {
        data.remove(nomeProperites);
    }

    /**
     * Limpa completamente as propriedades
     */
    public static void clear() {
        data.clear();
    }
}
