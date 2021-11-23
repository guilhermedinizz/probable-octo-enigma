# super-duper-octo-garbanzo

## O que foi testado?

- Contrato Do Seguro

## Ferramenta utilizadas

- Java 8
- Spring Boot
- Selenium (Para o Teste Automatizado)
- Cucumber (Caso de teste)
- javafaker (Para gerar dados aleatórios)
- Slf4j (Logs)

# Para executar

IDE: `src/test/java/com/guilherme/accenture/AccentureApplication.java`

## Pelo maven

`mvn test`

### Existe também a possibilidade de trocar o navegador facilmente por variavel de sistemas.

Automação suporta Chrome (chrome), Chrome em headless (chromeheadless) e Firefox

- Browser: -Dbrowser=navegadorDesejado

O comando ficaria algo como `mvn test -Dbrowser=firefox`

#### Ou

Alterando as propriedades no pow.xml para:

````xml
<properties>
<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
<java.version>1.8</java.version>
<default.ambiente>dev</default.ambiente>
<default.browser>chrome</default.browser>
<driver.update>n</driver.update>
<default.remote>n</default.remote>
</properties>
````

### Logs

Para alterar o nivel dos logs, basta adicionar no `application.properties`: logging.level.root=DEBUG


