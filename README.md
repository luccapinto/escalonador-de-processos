
# Escalonador de Processos

## Descrição
Este repositório contém a implementação de um escalonador de processos, desenvolvido em Java e Python. O projeto demonstra a gestão e escalonamento de processos com diferentes políticas, registrando e analisando o desempenho com base em várias métricas. Foi desenvolvido como parte do curso de Sistemas Operacionais na Universidade de São Paulo.

## O escalonador é composto de:
### Bloco de Controle de Processo (BCP)
Possui todas as características do processo:
* Nome;
* Seus registradores (como exemplo X e Y);
* Program Counter;
* Lista de instruções (Comando, E/S ou Saída);
* Seu estado (PRONTO, EXECUTANDO, BLOQUEADO, TERMINADO);
* Seu tempo de espera caso esteja efetuando Entrada/Saída.
  
### Tabela de Processos (TabelaDeProcessos)
Contém toda a lista de processos, usaremos duas tabelas: Prontos e Bloqueados.

### Escalonador (Escalonador)
Acessa a tabela de processos e, usando Round Robin, executa e administra os processos de acordo com o quantum e sua posição na fila.

Não temos um quantum verdadeiro nesse escalonador, então decrementamos o tempo de espera de cada processo a cada execução de comando. Caso nenhum comando esteja pronto, ele decrementa todos os bloqueados até que pelo menos um saia da lista de bloqueados.

### Extrator de médias dos logs para auxiliar no relatório (ExtraiMedia)
Extrai as últimas três linhas de cada log (contendo o número do quantum, média de trocas e média de instruções) e coloca no arquivo MediasExtraidas.txt. Criado para facilitar a leitura dos logs e agilizar o processo de fazer um relatório do melhor quantum.

### Carregador de programas (Main)
A função principal é responsável por: 
* Extrair os processos da pasta "programas" e carregar em uma lista que será enviada para a tabela de processos.
* Extrair o valor do quantum.
* Criar o escalonador e executá-lo.

### Pastas
* "programas" -> Todos os arquivos que se tornarão processos e o arquivo com o número do quantum.
* "logfiles" -> Todos os logs gerados com diferentes quantums para analisarmos e podermos gerar o relatório.

## Instalação
Para executar este projeto, você precisa ter o Java e o Python instalados na sua máquina.

Clone o repositório:
```bash
git clone https://github.com/seu-usuario/seu-repositorio.git
cd seu-repositorio
```

## Uso
### Java
Compile os arquivos Java:
```bash
javac Main.java Escalonador.java BCP.java LogFile.java TabelaDeProcessos.java
```

Inicie o programa principal:
```bash
java Main
```

### Python
Para executar o script Python que extrai médias:
```bash
python ExtraiMedia.py
```

## Estrutura dos Arquivos
- `Main.java`: Contém a implementação do programa principal que inicializa o escalonador.
- `Escalonador.java`: Implementação das políticas de escalonamento de processos.
- `BCP.java`: Implementação do Bloco de Controle de Processos (BCP).
- `LogFile.java`: Implementação da classe para manipulação de arquivos de log.
- `TabelaDeProcessos.java`: Implementação da tabela de processos.
- `ExtraiMedia.py`: Script em Python para extrair e calcular as médias de desempenho.
- `log.txt`: Arquivo de log gerado durante a execução dos testes.
- `MediasExtraidas.txt`: Arquivo contendo as médias extraídas dos logs.

## Projeto criado como exercício de programação da matéria de Sistemas Operacionais
### Integrantes do grupo:
* Lucca Rodrigues Assunção Pinto
* Marcos Vilela Rezende Júnior

