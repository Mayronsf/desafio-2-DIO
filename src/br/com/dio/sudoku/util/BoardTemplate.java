package br.com.dio.sudoku.util;

import br.com.dio.sudoku.model.Board;
import br.com.dio.sudoku.model.Space;

// Monta o desenho do tabuleiro para exibir no terminal
public final class BoardTemplate {

    private static final String SEPARATOR = "   +-------+-------+-------+";

    private BoardTemplate() {
    }

    public static String render(Board board) {
        StringBuilder sb = new StringBuilder();
        sb.append("     1 2 3   4 5 6   7 8 9\n");
        for (int row = 0; row < 9; row++) {
            if (row % 3 == 0) {
                sb.append(SEPARATOR).append('\n');
            }
            sb.append(' ').append(row + 1).append(' ');
            for (int col = 0; col < 9; col++) {
                if (col % 3 == 0) {
                    sb.append("| ");
                }
                Space space = board.getSpaces().get(col).get(row);
                sb.append(space.getActual() == null ? " " : space.getActual()).append(' ');
            }
            sb.append("|\n");
        }
        sb.append(SEPARATOR);
        return sb.toString();
    }
}
