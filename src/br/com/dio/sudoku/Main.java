package br.com.dio.sudoku;

import br.com.dio.sudoku.model.Board;
import br.com.dio.sudoku.model.Space;
import br.com.dio.sudoku.util.BoardTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    private static final int BOARD_LIMIT = 9;
    private static final Scanner scanner = new Scanner(System.in);

    private static Board board;

    public static void main(String[] args) {
        // Cada argumento tem o formato "coluna,linha;valorEsperado,fixo"
        Map<String, String> positions = Stream.of(args)
                .collect(Collectors.toMap(k -> k.split(";")[0], v -> v.split(";")[1]));

        while (true) {
            System.out.println("""

                    ===== SUDOKU =====
                    1 - Iniciar um novo jogo
                    2 - Colocar um número
                    3 - Remover um número
                    4 - Visualizar jogo atual
                    5 - Verificar status do jogo
                    6 - Limpar jogo
                    7 - Finalizar jogo
                    8 - Sair""");

            switch (readNumber("Selecione uma opção: ", 1, 8)) {
                case 1 -> startGame(positions);
                case 2 -> inputNumber();
                case 3 -> removeNumber();
                case 4 -> showCurrentGame();
                case 5 -> showGameStatus();
                case 6 -> clearGame();
                case 7 -> finishGame();
                case 8 -> {
                    System.out.println("Até a próxima!");
                    return;
                }
            }
        }
    }

    private static void startGame(Map<String, String> positions) {
        if (board != null) {
            System.out.println("O jogo já foi iniciado");
            return;
        }
        if (positions.size() != BOARD_LIMIT * BOARD_LIMIT) {
            System.out.println("Informe as 81 posições do tabuleiro nos argumentos do programa");
            return;
        }

        List<List<Space>> spaces = new ArrayList<>();
        for (int col = 0; col < BOARD_LIMIT; col++) {
            spaces.add(new ArrayList<>());
            for (int row = 0; row < BOARD_LIMIT; row++) {
                String[] config = positions.get(col + "," + row).split(",");
                int expected = Integer.parseInt(config[0]);
                boolean fixed = Boolean.parseBoolean(config[1]);
                spaces.get(col).add(new Space(expected, fixed));
            }
        }

        board = new Board(spaces);
        System.out.println("O jogo está pronto para começar");
        System.out.println(BoardTemplate.render(board));
    }

    private static void inputNumber() {
        if (isNotStarted()) return;

        int col = readNumber("Informe a coluna (1-9): ", 1, BOARD_LIMIT) - 1;
        int row = readNumber("Informe a linha (1-9): ", 1, BOARD_LIMIT) - 1;
        int value = readNumber("Informe o número (1-9): ", 1, 9);
        if (!board.changeValue(col, row, value)) {
            System.out.printf("A posição [%d,%d] tem um valor fixo%n", col + 1, row + 1);
        }
    }

    private static void removeNumber() {
        if (isNotStarted()) return;

        int col = readNumber("Informe a coluna (1-9): ", 1, BOARD_LIMIT) - 1;
        int row = readNumber("Informe a linha (1-9): ", 1, BOARD_LIMIT) - 1;
        if (!board.clearValue(col, row)) {
            System.out.printf("A posição [%d,%d] tem um valor fixo%n", col + 1, row + 1);
        }
    }

    private static void showCurrentGame() {
        if (isNotStarted()) return;

        System.out.println(BoardTemplate.render(board));
    }

    private static void showGameStatus() {
        if (isNotStarted()) return;

        System.out.println("O jogo atualmente se encontra no status " + board.getStatus().getLabel());
        System.out.println(board.hasErrors() ? "O jogo contém erros" : "O jogo não contém erros");
    }

    private static void clearGame() {
        if (isNotStarted()) return;

        String answer = readLine("Tem certeza que deseja limpar seu jogo e perder todo seu progresso? (sim/não): ");
        if (answer.equalsIgnoreCase("sim")) {
            board.reset();
            System.out.println("Jogo limpo");
        }
    }

    private static void finishGame() {
        if (isNotStarted()) return;

        if (board.gameIsFinished()) {
            System.out.println("Parabéns, você concluiu o jogo!");
            System.out.println(BoardTemplate.render(board));
            board = null;
        } else if (board.hasErrors()) {
            System.out.println("Seu jogo contém erros, verifique o tabuleiro e ajuste-o");
        } else {
            System.out.println("Você ainda precisa preencher alguma(s) posição(ões)");
        }
    }

    private static boolean isNotStarted() {
        if (board == null) {
            System.out.println("O jogo ainda não foi iniciado");
            return true;
        }
        return false;
    }

    // Repete a pergunta até receber um número dentro do intervalo
    private static int readNumber(String message, int min, int max) {
        while (true) {
            String input = readLine(message);
            try {
                int number = Integer.parseInt(input.trim());
                if (number >= min && number <= max) {
                    return number;
                }
            } catch (NumberFormatException ignored) {
            }
            System.out.printf("Valor inválido, informe um número entre %d e %d%n", min, max);
        }
    }

    private static String readLine(String message) {
        System.out.print(message);
        if (!scanner.hasNextLine()) {
            System.exit(0);
        }
        return scanner.nextLine();
    }
}
