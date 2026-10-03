# AutoHub

Sistema de seguro de veículos em Java, desenvolvido como atividade continuada da disciplina de Programação Orientada a Objetos.

O projeto cobre o cadastro de segurados (pessoa e empresa), veículos, apólices e sinistros, com persistência em arquivo, regras de validação, testes automatizados e telas em Swing.

## Funcionalidades

- Cadastro de **segurado pessoa** e **segurado empresa**, com validação de CPF e CNPJ (dígitos verificadores), endereço, datas e valores.
- Cadastro de **veículos** vinculados a um segurado, com categoria.
- Cadastro de **apólices** e **sinistros** vinculados a um veículo.
- Persistência dos objetos em disco por serialização.
- Telas gráficas com as quatro operações de cada cadastro: incluir, alterar, excluir e buscar.

## Arquitetura

O código é organizado em camadas, cada uma em um pacote dentro de `br.edu.cs.poo.ac.seguro`:

| Pacote | Responsabilidade |
|---|---|
| `entidades` | Classes de domínio: `Segurado`, `SeguradoPessoa`, `SeguradoEmpresa`, `Veiculo`, `Apolice`, `Sinistro`, `Endereco` e os enums `CategoriaVeiculo` e `TipoSinistro` |
| `daos` | Acesso a dados. Um DAO por entidade, gravando e lendo objetos em arquivo |
| `mediators` | Regras de negócio e validações. Os mediators são Singletons e usam os DAOs |
| `telas` | Interface gráfica em Swing |
| `testes` | Testes automatizados com JUnit 5 |

O fluxo de uma operação segue sempre o mesmo caminho:

```
Tela  →  Mediator (valida)  →  DAO (persiste)  →  arquivo em disco
```

Os mediators devolvem `null` quando a operação dá certo, ou a mensagem do erro de validação. As telas apenas exibem o resultado.

## Telas

As telas usam apenas Swing, com o tema Nimbus, e cada campo usa o componente adequado ao tipo do dado:

| Tela | Destaques |
|---|---|
| Segurado Pessoa | Máscaras de CPF, CEP e data |
| Segurado Empresa | Máscaras de CNPJ, CEP e data; caixa de seleção para locadora de veículos |
| Veículo | Lista suspensa de categoria; botões de opção para o tipo de proprietário |
| Apólice | Vinculada a um veículo pela placa |
| Sinistro | Lista suspensa de tipo; máscaras de data e hora |

## Tecnologias

- Java 17
- Swing
- JUnit 5
- Lombok
- PersistenciaObjetos (biblioteca de persistência fornecida na disciplina)

## Como executar

O projeto está configurado para o Eclipse.

1. Clone o repositório:
   ```
   git clone https://github.com/LuisFilipe192/AutoHub.git
   ```
2. No Eclipse, use **File → Import → Existing Projects into Workspace** e selecione a pasta clonada.
3. Confira se o Lombok está instalado no Eclipse. Os arquivos `lombok.jar` e `PersistenciaObjetos.jar` já estão na pasta `lib` e no build path do projeto.

### Abrir uma tela

Cada tela tem seu próprio `main`. Clique com o botão direito na classe, por exemplo `TelaSeguradoPessoa`, e escolha **Run As → Java Application**.

Para cadastrar um veículo é preciso ter um segurado cadastrado, e para cadastrar uma apólice ou um sinistro é preciso ter um veículo. A ordem natural de uso é: segurado, veículo, apólice ou sinistro.

### Rodar os testes

Clique com o botão direito no pacote `testes` e escolha **Run As → JUnit Test**. São 90 testes, cobrindo entidades, DAOs e mediators.

Os testes limpam as pastas de dados antes de cada execução, então os cadastros feitos pelas telas são apagados quando os testes rodam.

## Dados

Os objetos são gravados em pastas na raiz do projeto, uma por entidade (`SeguradoPessoa`, `SeguradoEmpresa`, `Veiculo`, `Apolice`, `Sinistro`). Elas são criadas automaticamente e não são versionadas.

## Autores

- Matheus Costa da Rocha ([@Mth71](https://github.com/Mth71))
- Luis Filipe Alves Silva Santos ([@LuisFilipe192](https://github.com/LuisFilipe192))# AutoHub