package com.guilherme.accenture.steps;

import com.cucumber.listener.Reporter;
import com.github.javafaker.Faker;
import com.guilherme.accenture.AccentureApplication;
import com.guilherme.accenture.driver.WebDriverFactory;
import com.guilherme.accenture.driver.WebDriverRecicle;
import com.guilherme.accenture.pageobjects.*;
import com.guilherme.accenture.util.Data;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.runner.RunWith;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;

import static com.guilherme.accenture.driver.WebDriverFactory.webDriverInstance;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AccentureApplication.class)
@SpringBootTest
public class ContratoDeSeguroStepDefs {

    protected Faker faker;
    @Autowired
    private DadosVeiculosPage dadosVeiculos;
    @Autowired
    private DadosDoSeguradoPage dadosDoSegurado;
    @Autowired
    private PaginaInicial paginaInicial;
    @Autowired
    private DadosDoProdutoPage dadosDoProduto;
    @Autowired
    private PrecoDoProdutoPage precoDoProdutoPage;
    @Autowired
    private EnviarCotacaoPage enviarCotacao;

    @Before
    public void setUpBase() {
        faker = new Faker();
        WebDriverFactory.criarNovoWebDriver();
    }

    @Given("estou na pagina inicial")
    public void acessarPaginaInicial() {
        paginaInicial.iniciar();
    }


    @When("^preencho os dados do veiculo para \"([^\"]*)\"$")
    public void preenchoOsDadosDoVeiculo(String tipoDoVeiculo) throws IOException {
        paginaInicial.clicarSeguroParaUmTipo(tipoDoVeiculo);
        dadosVeiculos.aguardarPaginaCarregar();
        dadosVeiculos.preencherInformacoesPeloTipoDeVeiculo(tipoDoVeiculo);
        dadosVeiculos.clicarEmNext();
    }

    @And("^preencho os dados do segurado$")
    public void preenchoOsDadosDoSegurado() {
        dadosDoSegurado.aguardarPaginaCarregar();

        String nome = faker.name().firstName();
        String sobrenome = faker.name().lastName();
        String site = faker.internet().url().replaceAll(" ", "");
        String cidade = faker.address().city();
        String endereco = faker.address().streetName();
        if (endereco.length() > 50) endereco = endereco.substring(50);
        String cep = faker.address().zipCode().replaceAll("-", "");
        if (cep.length() > 7) {
            int contador = cep.length() - 8;
            cep = cep.substring(contador);
        }

        dadosDoSegurado.preencherCampoDoNome(nome);
        dadosDoSegurado.preencherCampoDoSobrenome(sobrenome);
        dadosDoSegurado.preencherCampoDeAniversario("12/07/2001");
        dadosDoSegurado.selecionarGenero(false);
        dadosDoSegurado.preencherRua(endereco);
        dadosDoSegurado.selecionarPais("Brazil");
        dadosDoSegurado.preencherCampoCep(cep);
        dadosDoSegurado.preencherCidade(cidade);
        dadosDoSegurado.selecionarOcupacao("Employee");
        dadosDoSegurado.clicarEmUmHobbie();
        dadosDoSegurado.preencherWebSite(site);
        dadosDoSegurado.clicarEmNext();
    }

    @And("^preencho os dados do produto com o valor de cobertura de \"([^\"]*)\", \"([^\"]*)\" e cobertura por dano de \"([^\"]*)\" para o tipo \"([^\"]*)\"$")
    public void preenchoOsDadosDoProduto(String valor, String meriRanting, String coberturaPorDano, String tipoDoVeiculo) {
        dadosDoProduto.aguardarPaginaCarregar();
        dadosDoProduto.preencherDadosDoProduto(valor, meriRanting, coberturaPorDano, tipoDoVeiculo);
        dadosDoProduto.clicarEmNext();
    }

    @And("^seleciono a opcao \"([^\"]*)\" de preco$")
    public void selecionoAlgumaOpcaoDePreco(String opcao) {
        precoDoProdutoPage.aguardarPaginaCarregar();
        precoDoProdutoPage.validarMensagem("Please, complete the first three steps to see the price table.");
        precoDoProdutoPage.selecionarProduto(opcao);
        precoDoProdutoPage.clicarEmNext();
    }

    @And("^preencho os dados para o envio da cotacao$")
    public void preenchoOsDadosParaOEnvioDaCotacao() {
        String email = faker.internet().emailAddress();
        String telefone = faker.phoneNumber().cellPhone().replaceAll("[^0-9a-zA-Z]+", "");
        String username = faker.name().username();
        String senha = faker.internet().password(10, 32, true, true, true);

        enviarCotacao.aguardarPaginaCarregar();
        enviarCotacao.preencherEmail(email);
        enviarCotacao.preencherTelefone(telefone);
        enviarCotacao.preencherUsername(username);
        enviarCotacao.preencherSenha(senha);
        enviarCotacao.preencherConfirmarSenha(senha);
    }

    @Then("^cotacao enviada com sucesso$")
    public void cotacaoEnviadaComSucesso() {
        enviarCotacao.clicarEmEnviar();
        enviarCotacao.validarMensagem("Sending e-mail success!");
    }

    @After
    public void tearDown(Scenario scenario) {
        final byte[] screenshot = ((TakesScreenshot) webDriverInstance).getScreenshotAs(OutputType.BYTES);
        scenario.embed(screenshot, "image/png");
        WebDriverRecicle.recicleWebDriver();
        Reporter.loadXMLConfig(new File(Data.get("reportConfigPath")));
    }

}
