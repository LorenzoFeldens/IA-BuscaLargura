# IA – Busca em Largura: Missionários e Canibais

Trabalho individual da disciplina de **Inteligência Artificial**: resolução do problema dos
**Missionários e Canibais** com **busca em largura** (BFS), em Java com interface gráfica em Swing.

O relatório do trabalho está em [`Trabalho Individual I - Lorenzo Feldens.pdf`](Trabalho%20Individual%20I%20-%20Lorenzo%20Feldens.pdf).

## O problema

Missionários e canibais precisam atravessar um rio em um barco com capacidade limitada. Em nenhuma das
margens (nem no barco) os canibais podem ficar em maior número que os missionários, quando houver
missionários ali. O objetivo é levar todos da margem esquerda para a direita.

## Como funciona

1. Na tela inicial, você informa o número de **missionários**, de **canibais** e a **capacidade do barco**.
2. O programa gera todos os **operadores** possíveis (quantos missionários e canibais atravessam), respeitando
   a capacidade do barco.
3. Gera também todos os **estados** válidos, ou seja, as distribuições em que nenhuma margem fica em perigo.
4. A busca em largura parte do estado inicial (todos na margem esquerda) e expande os estados em ordem de
   nível, pela lista de abertos, até chegar ao estado objetivo ou concluir que não há solução.

## Visualizações

| Botão | O que mostra |
|---|---|
| **Operadores Possíveis** | Todos os movimentos `<missionários, canibais>` permitidos |
| **Estados Possíveis** | Todos os estados válidos do problema |
| **Árvore Solução** | A árvore de busca, com os botões **Próximo** (executa um passo) e **Reset** |
| **Caminho Solução** | A sequência de travessias da solução |

## Estrutura do projeto

| Caminho | Descrição |
|---|---|
| `MissionariosCanibais/src/missionarioscanibais/Algoritmo.java` | Geração de operadores e estados e a busca em largura |
| `MissionariosCanibais/src/missionarioscanibais/Estado.java` | Estado: missionários e canibais em uma margem, margem em que está o barco, pai e filhos |
| `MissionariosCanibais/src/missionarioscanibais/Movimento.java` | Operador: quantos missionários e canibais atravessam |
| `MissionariosCanibais/src/GUI/` | Telas Swing: parâmetros, estados, árvore e caminho |

## Como executar

Projeto do **NetBeans** (Ant, Java 8). Abra a pasta `MissionariosCanibais` no NetBeans e execute, ou pela linha
de comando:

```sh
cd MissionariosCanibais
ant run
```

## Tecnologias

- Java 8 + Swing
- NetBeans / Apache Ant
