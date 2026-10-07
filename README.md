# prg03kaelrochaamorim

Projeto para a conclusão da matéria de POO do Curso de ADS do IFBA



31/08/2026 - Fazendo alterações e confirmando com Pull Request.

28/09/2026 - Sobrecarga de construtores em Personagem:
"Personagem(String nome)" cria um personagem novo, sempre no nível 1, padrão
para quem está começando. "Personagem(String nome, int nivelInicial)" existe
para quando um personagem precisa nascer já em um nível diferente de 1, como ao importar
uma save antiga ou criar um personagem de teste em nível avançado.

06/10/2026 - Busca por List vs Map:
Com dez usuários, buscar com um "for" na "List" ou consultar o "Map" são praticamente
instantâneos, a diferença é imperceptível. Com dez mil usuários, o "for" precisa
percorrer em média metade da lista a cada busca (fica cada vez mais lento conforme
a lista cresce), enquanto o "Map" continua respondendo na mesma velocidade, porque
acessa direto pela chave (hash), sem precisar varrer nada.

