#######################################################################################################################
# Programa para facilitar a nossa vida na hora de fazer o relatório, extrai as informações que precisamos de cada log #
#######################################################################################################################

import os

# Extrai as últimas 3 linhas (Média de trocas, Média de instruções e o quantum)
def extraiUltimasLinhas(caminhoArquivo):
    with open(caminhoArquivo, 'r', encoding='utf-8') as file:
        lines = file.readlines()[-3:]
    return lines
# Joga as linhas extraídas no documento
def SalvaNoArquivo(lines, arquivoSaida):
    with open(arquivoSaida, 'a', encoding='utf-8') as output_file:
        output_file.writelines(lines)

def main():
    caminhoPasta = 'logfiles'  # Caminho de onde ele vai tirar os arquivos de logs
    arquivoSaida = 'MediasExtraidas.txt'  # Para onde ele vai exportar as informações extraídas (o arquivo não pode existir)

    # Pega todos os arquivos de log da pasta logfiles
    filenames = [f for f in os.listdir(caminhoPasta) if f.endswith('.txt')]
    
    # Coloca eles em ordem numérica
    sorted_filenames = sorted(filenames, key=lambda f: int(''.join(filter(str.isdigit, f))))

    for filename in sorted_filenames:
        caminhoArquivo = os.path.join(caminhoPasta, filename)
        lines = extraiUltimasLinhas(caminhoArquivo)
        SalvaNoArquivo(lines, arquivoSaida)
        with open(arquivoSaida, 'a', encoding='utf-8') as output_file:
            output_file.write('\n')

if __name__ == "__main__":
    main()
