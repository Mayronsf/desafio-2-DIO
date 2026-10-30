package br.com.dio.sudoku.model;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static br.com.dio.sudoku.model.GameStatusEnum.COMPLETE;
import static br.com.dio.sudoku.model.GameStatusEnum.INCOMPLETE;
import static br.com.dio.sudoku.model.GameStatusEnum.NON_STARTED;

// Tabuleiro 9x9, acessado como spaces.get(coluna).get(linha)
public class Board {

    private final List<List<Space>> spaces;

    public Board(List<List<Space>> spaces) {
        this.spaces = spaces;
    }

    public List<List<Space>> getSpaces() {
        return spaces;
    }

    public GameStatusEnum getStatus() {
        if (allSpaces().noneMatch(s -> !s.isFixed() && s.getActual() != null)) {
            return NON_STARTED;
        }
        return allSpaces().anyMatch(s -> s.getActual() == null) ? INCOMPLETE : COMPLETE;
    }

    public boolean hasErrors() {
        return getStatus() != NON_STARTED && allSpaces().anyMatch(Space::hasError);
    }

    // Retorna false se a casa for fixa
    public boolean changeValue(int col, int row, int value) {
        Space space = spaces.get(col).get(row);
        if (space.isFixed()) return false;
        space.setActual(value);
        return true;
    }

    public boolean clearValue(int col, int row) {
        Space space = spaces.get(col).get(row);
        if (space.isFixed()) return false;
        space.clearSpace();
        return true;
    }

    public void reset() {
        allSpaces().forEach(Space::clearSpace);
    }

    public boolean gameIsFinished() {
        return !hasErrors() && getStatus() == COMPLETE;
    }

    private Stream<Space> allSpaces() {
        return spaces.stream().flatMap(Collection::stream).filter(Objects::nonNull);
    }
}
