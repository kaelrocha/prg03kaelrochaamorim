# Atividade 21 — Auditoria SOLID

## Task 01 — Os cinco princípios no projeto

Os caminhos abaixo partem de `prg03kaelrochaamorim/src/main/java/br/com/ifba`.

**SRP (responsabilidade única).** Em `personagem/entity/Item.java:7`, a classe `Item` guarda o nome e o valor de um item. A lista de itens fica em `Personagem`.

**OCP (aberto para extensão, fechado para modificação).** Em `personagem/entity/PersonagemService.java:10`, `processarDano` chama `calcularDano()`. Para acrescentar um tipo de personagem, basta implementar a fórmula na nova subclasse. O serviço continua igual.

**LSP (substituição de Liskov).** Em `personagem/entity/Guerreiro.java:14`, `Guerreiro` implementa `calcularDano()` e pode ser passado a `processarDano` do mesmo jeito que `Mago` e `Arqueiro`. Cada um calcula o dano com sua própria fórmula.

**ISP (segregação de interfaces).** Em `usuario/interfaces/Autenticavel.java:11`, a interface só declara `autenticar(login, senha)`. Quem usa esse contrato pode verificar as credenciais sem depender das operações de cadastro e consulta.

**DIP (inversão de dependência).** Em `usuario/service/UsuarioService.java:13`, o construtor recebe a interface `UsuarioIDao`. No programa, ele recebe o DAO que usa JPA. Nos testes, recebe o `FakeUsuarioDao`.

## Task 02 — Violações encontradas

As linhas desta seção são do código antes das correções, no commit `8f62dfe`.

Em `usuario/view/UsuarioListar.java:46` e `usuario/view/UsuarioListar.java:88`, a própria tela definia as colunas e montava as linhas da tabela, além de tratar busca, edição e exclusão. Por isso, até uma mudança nas colunas exigia mexer na classe que trata os cliques da tela. Esse foi o caso de SRP.

Em `infrastructure/dao/GenericDao.java:18`, o DAO chamava `JPAUtil` diretamente para obter um `EntityManager`. Para usar um gerenciador falso nos testes, era preciso criar uma subclasse e sobrescrever o método. Esse foi o caso de DIP.

## Task 03 — O que mudei

Para corrigir o SRP, passei as colunas e a montagem das linhas para `UsuarioTabelaModelo`. A tela continua tratando os cliques e chamando o controller. Depois de ordenar a tabela, ela ainda converte a linha clicada para a posição correta no modelo antes de editar ou excluir.

Para corrigir o DIP, fiz o `GenericDao` receber um `Supplier<EntityManager>` pelo construtor. O `Main` passa `JPAUtil::getEntityManager` e o teste passa uma função que cria o gerenciador falso. O DAO continua pedindo um gerenciador a cada operação.
