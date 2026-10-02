# POO 2026.2 — Atividade Continuada

Sistema de seguro de veículos em Java, desenvolvido para a disciplina de Programação Orientada a Objetos (CESAR School, 2026.2).

## Estrutura

Pacote base: `br.edu.cs.poo.ac.seguro`

| Pacote | Conteúdo |
| --- | --- |
| `entidades` | Classes de domínio (Segurado, Veiculo, Apolice, Sinistro...) |
| `daos` | Persistência dos dados |
| `mediators` | Regras de negócio e validações |
| `telas` | Interfaces gráficas em Swing |
| `testes` | Testes automatizados (JUnit 5) |

## Como rodar

1. Importar o projeto no Eclipse.
2. Adicionar ao build path: `PersistenciaObjetos.jar`, Lombok e JUnit 5.
3. Rodar os testes: botão direito no pacote `testes` → **Run As → JUnit Test**.
