Feature: Contratação de Seguro Auto
  Validação dos fluxos de contratação de seguro auto

  @ContratoDeVeiculos
  Scenario Outline: Cadastro de seguro com sucesso
    Given estou na pagina inicial
    When preencho os dados do veiculo para "<TipoDoVeiculo>"
    And preencho os dados do segurado
    And preencho os dados do produto com o valor de cobertura de "<ValorDeCobertura>", "<MeritRanting>" e cobertura por dano de "<CoberturaPorDanos>" para o tipo "<TipoDoVeiculo>"
    And seleciono a opcao "<OpcaoDePreco>" de preco
    And preencho os dados para o envio da cotacao
    Then cotacao enviada com sucesso

    Examples:
      | TipoDoVeiculo | ValorDeCobertura | MeritRanting | CoberturaPorDanos | OpcaoDePreco |
      | Carro         | 3.000.000,00     | Bonus 1      | No Coverage       | Gold         |
      | Moto          | 5.000.000,00     | Bonus 2      | Full Coverage     | Silver       |
      | Caminha       | 20.000.000,00    | Bonus 4      | Full Coverage     | Platinum     |
      | Camper        | 15.000.000,00    | Bonus 5      | Full Coverage     | Ultimate     |
