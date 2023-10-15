import os

def extract_last_lines(file_path):
    with open(file_path, 'r', encoding='utf-8') as file:
        lines = file.readlines()[-3:]
    return lines

def save_to_output(lines, output_path):
    with open(output_path, 'a', encoding='utf-8') as output_file:
        output_file.writelines(lines)

def main():
    log_dir = 'logfiles'  # Substitua pelo caminho correto
    output_path = 'testes.txt'  # Substitua pelo caminho correto

    # Obtém uma lista de todos os arquivos .txt no diretório
    filenames = [f for f in os.listdir(log_dir) if f.endswith('.txt')]
    
    # Ordena os arquivos considerando os números neles
    sorted_filenames = sorted(filenames, key=lambda f: int(''.join(filter(str.isdigit, f))))

    for filename in sorted_filenames:
        file_path = os.path.join(log_dir, filename)
        lines = extract_last_lines(file_path)
        save_to_output(lines, output_path)
        # Adiciona uma linha em branco para separar as entradas no arquivo de saída
        with open(output_path, 'a', encoding='utf-8') as output_file:
            output_file.write('\n')

if __name__ == "__main__":
    main()
