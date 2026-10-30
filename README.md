# Sudoku no Terminal — Desafio DIO

Jogo de Sudoku em Java que roda no terminal, feito para o desafio da DIO.
Baseado no projeto de referência: [digitalinnovationone/sudoku](https://github.com/digitalinnovationone/sudoku).

## Funcionalidades
1. Iniciar um novo jogo
2. Colocar um número
3. Remover um número
4. Visualizar o jogo atual
5. Verificar o status do jogo (não iniciado, incompleto ou completo, com ou sem erros)
6. Limpar o jogo
7. Finalizar o jogo
8. Sair

As casas fixas do tabuleiro não podem ser alteradas.

## Estrutura
```
src/br/com/dio/sudoku/
├── Main.java               menu e interação com o usuário
├── model/
│   ├── Board.java          tabuleiro 9x9 e regras do jogo
│   ├── Space.java          uma casa do tabuleiro
│   └── GameStatusEnum.java status do jogo
└── util/
    └── BoardTemplate.java  desenho do tabuleiro no terminal
```

## Melhorias em relação ao original
- Coordenadas de 1 a 9 (mais natural para quem joga)
- Validação das entradas: o jogo pede de novo quando o valor é inválido, em vez de travar
- Confirmação antes de limpar o jogo aceita "sim"/"não"

## Como executar
O tabuleiro inicial é passado por argumentos no formato `coluna,linha;valorEsperado,fixo`.
Os argumentos do desafio estão em [`args.txt`](args.txt).

```bash
javac -encoding UTF-8 -d out $(find src -name "*.java")
java -cp out br.com.dio.sudoku.Main $(cat args.txt)
```

No IntelliJ, cole o conteúdo de `args.txt` em **Run > Edit Configurations > Program arguments**.
