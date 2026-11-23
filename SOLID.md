# Atividade 21 — Auditoria SOLID

## Task 01 — Os cinco princípios

As linhas abaixo se referem à versão desta entrega. Os arquivos Java ficam no módulo `prg03kaelrochaamorim`, em `src/main/java/br/com/ifba`.

| Princípio | Arquivo e linha | Exemplo no projeto |
| --- | --- | --- |
| SRP — Responsabilidade única | `personagem/entity/Item.java:7` | `Item` representa um item do inventário com nome e valor. O cálculo de dano e a gestão do inventário pertencem a outras classes. |
| OCP — Aberto para extensão, fechado para modificação | `personagem/entity/PersonagemService.java:10` | O serviço chama `calcularDano()` pela classe abstrata. Um novo tipo de personagem pode definir sua fórmula por sobrescrita, sem mudar o serviço. |
| LSP — Substituição de Liskov | `personagem/entity/Guerreiro.java:14` | `Guerreiro` implementa o contrato de `Personagem.calcularDano()`. Pode ser passado a `processarDano(Personagem)` nas mesmas condições de `Mago` ou `Arqueiro`. |
| ISP — Segregação de interfaces | `usuario/interfaces/Autenticavel.java:11` | O contrato oferece apenas `autenticar(login, senha)`. Um cliente que só autentica não depende das operações de cadastro e consulta. |
| DIP — Inversão de dependência | `usuario/service/UsuarioService.java:13` | O serviço recebe `UsuarioIDao` pelo construtor. A aplicação conecta o DAO JPA e os testes fornecem `FakeUsuarioDao` pela mesma interface. |

## Task 02 — Violações encontradas

Referências ao código anterior à refatoração (commit `8f62dfe`).

1. **SRP em `usuario/view/UsuarioListar.java:46` e `:88`.** A janela reagia aos comandos do usuário e também definia as colunas, mantinha os registros e convertia os usuários em linhas. Mudar a apresentação dos dados na tabela exigia editar a classe que coordena busca, edição e remoção.
2. **DIP em `infrastructure/dao/GenericDao.java:18`.** O DAO genérico obtinha o gerenciador por uma chamada direta a `JPAUtil`, que define a inicialização do Hibernate e do Flyway. Para fornecer um gerenciador falso, os testes precisavam criar uma subclasse e sobrescrever o método.

## Task 03 — Correções

1. **SRP.** `UsuarioTabelaModelo` concentra as colunas e a transformação dos registros em linhas. `UsuarioListar` cuida dos eventos e das chamadas ao controller. O índice visual continua sendo convertido no índice do modelo antes da edição ou exclusão.
2. **DIP.** `GenericDao` recebe um `Supplier<EntityManager>` pelo construtor. O `Main` fornece `JPAUtil::getEntityManager` para a aplicação e `GenericDaoTest` fornece o gerenciador falso pelo mesmo contrato. A criação continua sob demanda, uma vez por operação.
