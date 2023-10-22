# Escalonador de processos
Escalonador de tarefas para Time Sharing em uma máquina de um único processador - sistema de multiprogramação.

## O escalonador é composto de:
### Bloco de Controle de Processo (BCP)
Possui todas as caracteristicas do processo:
* Nome;
* Seus registradores (como exemplo X e Y);
* Program Counter;
* Lista de instruções (Comando, E/S ou Saida);
* Seu estado (PRONTO, EXECUTANDO, BLOQUEADO, TERMINADO);
* Seu tempo de espera caso esteja efetuando Entrada/Saída.
  
### Tabela de Processos (ProcessTable)
Contém toda a lista de processos, usaremos duas tabelas: Prontos e Bloqueados.

### Escalonador (Scheduler)
Acessa a tabela de processos e, usando Round Robin, executa e administra os processos de acordo com o quantum e sua posição na fila.

Não temos um quantum verdadeiro nesse escalonador, então decrementamos o tempo de espera de cada processo a cada execução de comando. Caso nenhum comando esteja pronto, ele decrementa todos os bloqueados até que pelo menos um saia da lista de bloqueados.

### Extrator de médias dos logs para auxiliar no relatório (ExtraiLogs)
Extrai as últimas três linhas de cada log (contendo o número do quantum, média de trocas e média de instruções). Criado para facilitar a leitura dos logs e agilizar o processo de fazer um relatório do melhor quantum.

### Carregador de programas (Main)
A funçao principal é responsável por: 
* Extrair os processos da pasta "programas" e carregar em uma lista que será enviada para a tabela de processos.
* Extrair o valor do quantum
* Criar o escalonador e executá-lo

### Pastas
* "programas" -> Todos os arquivos que se tornarão processos e o arquivo com o número do quantum
* "logfiles" -> Todos os logs gerados com diferentes quantums para analisarmos e podermos gerar o relatório

## Programa criado como exercício de programação da matéria de Sistemas Operacionais
### Integrantes do grupo:
*  Armando Augusto Marchini Vidal
*  Lucca Rodrigues Assunção Pinto
*  Marcos Vilela Rezende Júnior
