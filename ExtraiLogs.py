#######################################################################################################################
# Programa para facilitar a nossa vida na hora de fazer o relatório, extrai as informações que precisamos de cada log #
#######################################################################################################################

import os

# Extrai as últimas 3 linhas (Média de trocas, Média de instruções e o quantum)
def extract_last_lines(file_path):
    with open(file_path, 'r', encoding='utf-8') as file:
        lines = file.readlines()[-3:]
    return lines
# Joga as linhas extraídas no documento
def save_to_output(lines, output_path):
    with open(output_path, 'a', encoding='utf-8') as output_file:
        output_file.writelines(lines)

def main():
    log_dir = 'logfiles'  # Caminho de onde ele vai tirar os arquivos de logs
    output_path = 'LogsExtraidos.txt'  # Para onde ele vai exportar as informações extraídas (o arquivo não pode existir)

    # Pega todos os arquivos de log da pasta logfiles
    filenames = [f for f in os.listdir(log_dir) if f.endswith('.txt')]
    
    # Coloca eles em ordem numérica
    sorted_filenames = sorted(filenames, key=lambda f: int(''.join(filter(str.isdigit, f))))

    for filename in sorted_filenames:
        file_path = os.path.join(log_dir, filename)
        lines = extract_last_lines(file_path)
        save_to_output(lines, output_path)
        with open(output_path, 'a', encoding='utf-8') as output_file:
            output_file.write('\n')

if __name__ == "__main__":
    main()
