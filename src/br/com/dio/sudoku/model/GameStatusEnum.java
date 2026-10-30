package br.com.dio.sudoku.model;

public enum GameStatusEnum {

    NON_STARTED("não iniciado"),
    INCOMPLETE("incompleto"),
    COMPLETE("completo");

    private final String label;

    GameStatusEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
