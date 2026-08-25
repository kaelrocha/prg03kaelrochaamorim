# Documento de Requisitos

## Projeto: [Nome provisório] — RPG de Turnos (Antiguidade + Jujutsu Kaisen)

**Instituição:** IFBA
**Curso:** ADS
**Disciplina:** Programação Orientada a Objetos (POO)
**Autor:** Kael Rocha Amorim
**Data:** 23/08/2026
**Versão:** 1.0

---

## 1. Visão Geral do Projeto

O projeto consiste no desenvolvimento de um **RPG de combate por turnos**, ambientado em um cenário de **antiguidade clássica** (mitologia, guerreiros, templos, impérios), incorporando o sistema de poderes das **Técnicas Amaldiçoadas** inspirado no universo de *Jujutsu Kaisen*.

O jogo será desenvolvido em **Java**, aplicando os conceitos de Programação Orientada a Objetos ao longo do semestre. O projeto é evolutivo: novas funcionalidades serão apresentadas e implementadas em etapas futuras, conforme o andamento da disciplina.

Nesta primeira entrega, o escopo é **apenas o Documento de Requisitos**, sem necessidade de código funcional completo.

---

## 2. Objetivo do Projeto

Desenvolver um jogo de RPG por turnos em Java, aplicando conceitos de POO (encapsulamento, herança, polimorfismo e abstração), no qual o jogador controla um personagem que enfrenta inimigos utilizando ataques físicos, defesa e técnicas amaldiçoadas (equivalentes à "magia" do RPG clássico), evoluindo por meio de níveis, experiência e equipamentos amaldiçoados.

---

## 3. Público-Alvo

Projeto acadêmico, desenvolvido para fins de aprendizado da disciplina de POO. Jogável via terminal/console (ao menos nas primeiras versões).

---

## 4. Escopo do Projeto

### 4.1 O que está incluso (fases previstas ao longo do semestre)
- Sistema de personagens com atributos (vida, ataque, defesa, velocidade)
- Sistema de combate por turnos, com ordem definida por velocidade/iniciativa
- Sistema de técnicas amaldiçoadas (equivalente a magias/habilidades especiais)
- Sistema de inimigos (Maldições) — criadas e/ou baseadas no universo de JJK
- Sistema de progressão (níveis e experiência)
- Sistema de equipamentos (armas e objetos amaldiçoados que aprimoram atributos)

### 4.2 O que está fora do escopo (por enquanto)
- Interface gráfica (o jogo iniciará em modo texto/console)
- Multiplayer
- Sistema de salvamento em banco de dados (pode ser considerado em fase futura)

> Observação: como o projeto é incremental, este escopo poderá ser expandido em versões futuras do documento, à medida que a disciplina apresentar novos requisitos.

---

## 5. Requisitos Funcionais

| Código | Descrição |
|--------|-----------|
| RF01 | O sistema deve permitir a criação de um personagem jogável, com nome, vida, ataque, defesa e velocidade. |
| RF02 | O sistema deve permitir que o personagem realize um ataque físico contra um alvo. |
| RF03 | O sistema deve permitir que o personagem utilize uma técnica amaldiçoada (habilidade especial) contra um alvo. |
| RF04 | O sistema deve permitir que o personagem realize uma ação de defesa, reduzindo o dano recebido no próximo turno. |
| RF05 | O sistema deve calcular a ordem dos turnos com base no atributo de velocidade de cada personagem (iniciativa). |
| RF06 | O sistema deve controlar os pontos de vida de cada personagem, reduzindo-os ao sofrer dano. |
| RF07 | O sistema deve verificar se um personagem está vivo ou derrotado após cada ataque. |
| RF08 | O sistema deve encerrar o combate quando um dos lados (jogador ou inimigo) for derrotado. |
| RF09 | O sistema deve conceder experiência ao jogador ao vencer um combate. |
| RF10 | O sistema deve permitir que o jogador suba de nível ao acumular experiência suficiente, aumentando seus atributos. |
| RF11 | O sistema deve permitir a criação de inimigos (Maldições) com atributos próprios. |
| RF12 | O sistema deve permitir o uso de equipamentos (armas e objetos amaldiçoados) que aprimorem atributos do personagem. |
| RF13 | O sistema deve permitir equipar e trocar itens/armas do personagem. |

---

## 6. Requisitos Não Funcionais

| Código | Descrição |
|--------|-----------|
| RNF01 | O sistema deve ser desenvolvido em Java. |
| RNF02 | O sistema deve seguir os princípios de Programação Orientada a Objetos (encapsulamento, herança, polimorfismo, abstração). |
| RNF03 | O código deve ser organizado em classes coesas e reutilizáveis, facilitando a adição de novas funcionalidades ao longo do semestre. |
| RNF04 | O sistema deve rodar via terminal/console nas primeiras versões. |
| RNF05 | O sistema deve ser de fácil manutenção e extensão, já que novas funções serão incorporadas progressivamente. |

---

## 7. Regras de Negócio

- RN01: A ordem dos turnos é determinada pela velocidade de cada personagem — quanto maior a velocidade, antes ele age.
- RN02: Um personagem com vida igual a 0 é considerado derrotado e não pode mais agir.
- RN03: A ação de defesa reduz o dano recebido apenas no turno seguinte ao seu uso.
- RN04: Técnicas amaldiçoadas podem ter custo de energia/mana e efeitos diferentes de um ataque físico comum.
- RN05: Equipamentos amaldiçoados concedem bônus a um ou mais atributos (vida, ataque, defesa, velocidade) enquanto equipados.
- RN06: O ganho de experiência ao final do combate é proporcional à dificuldade do inimigo derrotado.
- RN07: Ao subir de nível, os atributos do personagem aumentam automaticamente.

---

## 8. Estrutura de Classes (Visão Inicial)

O projeto já possui uma primeira modelagem da classe base `Personagem`, com atributos essenciais e o método de ataque. A estrutura será expandida com herança (ex: `Jogador` e `Maldicao` herdando de `Personagem`) e novas classes conforme o semestre avançar (`Tecnica`, `Item`, `Combate`, etc.).

**Classe base atual (`Personagem`):**

```java
public class Personagem {
    private String nome;
    private int vida;
    private int ataque;

    public Personagem(String nome, int vida, int ataque){
        this.nome = nome;
        this.vida = vida;
        this.ataque = ataque;
    }

    public void atacar(Personagem alvo) {
        System.out.println(nome + " ataca " + alvo.getNome() + " causando " + ataque + " de dano!");
        alvo.receberDano(ataque);
    }

    public void receberDano(int dano){
        vida -= dano;
        if (vida < 0){
            vida = 0;
        }
    }

    public boolean estaVivo(){
        return vida > 0;
    }

    public String getNome(){ return nome; }
    public int getVida(){ return vida; }
    public int getAtaque(){ return ataque; }
}
```

**Evoluções previstas para as próximas etapas:**
- Adicionar atributos `defesa` e `velocidade` à classe `Personagem`
- Criar subclasses `Jogador` e `Maldicao` (herança)
- Criar classe `Tecnica` (técnica amaldiçoada), com nome, custo e efeito
- Criar classe `Item`/`Equipamento`, com atributo(s) que modifica(m)
- Criar classe `Combate`, responsável por gerenciar a ordem dos turnos e o fluxo da batalha

---

## 9. Tecnologias Previstas

- **Linguagem:** Java
- **Paradigma:** Programação Orientada a Objetos
- **Execução:** Console/terminal (fase inicial)
- **Ferramentas:** IDE NetBeans

---

## 10. Observações Finais

Este documento representa a primeira versão dos requisitos do projeto e deve ser atualizado conforme novas funcionalidades forem exigidas pela disciplina, garantindo que o desenvolvimento do RPG se mantenha organizado e alinhado aos princípios de POO, minimizando retrabalho nas próximas etapas.
