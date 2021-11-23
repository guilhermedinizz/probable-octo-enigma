package com.guilherme.accenture.util;

import org.jetbrains.annotations.Contract;

public class StringUtils {

    public static String randomAlphaNumeric(int count) {
        String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    /**
     * Retorna o string em uma das cores conforme a cor escolhida
     * *Opcional: Cores.COR.clarear() para clarear a cor desejada
     * <p>
     *
     * @param text    Texto a ser colorido
     * @param cor     Selecionar dentro de Cores a cor desejada
     * @param fundo   Selecionar dentro de Fundo a cor do fundo desejado
     * @param formato Selecionar dentro de Formatacao o Tipo de fonte desejada
     */

    @Contract(pure = true)
    public static String colored(String text, Cores cor, Fundo fundo, Formatacao formato) {
        String colored;

        if (cor == null)
            cor = Cores.PRETO;

        if (formato != null && fundo != null)
            colored = "\033[" + formato.value + ";" + cor.value + ";" + fundo.value + "m" + text + "\033[0m";

        else if (fundo != null)
            colored = "\033[" + cor.value + ";" + fundo.value + "m" + text + "\033[0m";

        else if (formato != null)
            colored = "\033[" + formato.value + ";" + cor.value + "m" + text + "\033[0m";

        else colored = "\033[" + cor.value + "m" + text + "\033[0m";

        return colored;
    }

    public enum Cores {
        PRETO("0;30"),
        VERMELHO("0;31"),
        VERDE("0;32"),
        MARROM("0;33"),
        AZUL("0;34"),
        ROXO("0;35"),
        CIANO("0;36"),
        CINZACLARO("0;37");

        public String value;

        Cores(String i) {
            this.value = i;
        }

        public Cores clarear() {
            this.value = value.replace("0", "1");
            return this;
        }
    }

    public enum Formatacao {
        NEGRITO(1),
        ITALICO(3),
        SUBLINHADO(4),
        PISCANDO(5),
        INVERTIDO(7),
        OCULTO(8),
        RISCADO(9);

        public int value;

        Formatacao(int i) {
            this.value = i;
        }
    }

    public enum Fundo {
        PRETO(40),
        VERMELHO(41),
        VERDE(42),
        MARROM(43),
        AZUL(44),
        ROXO(45),
        CIANO(46),
        CINZACLARO(47);

        public int value;

        Fundo(int i) {
            this.value = i;
        }
    }
}
