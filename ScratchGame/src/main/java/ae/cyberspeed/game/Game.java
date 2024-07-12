package ae.cyberspeed.game;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class Game {

    private int columns;
    private int rows;
    private Map<String, Symbol> symbols;

    private Probability probabilities;

    @JsonProperty("win_combinations")
    private Map<String, WinCombination> winCombinations;

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public Probability getProbabilities() {
        return probabilities;
    }

    public void setProbabilities(Probability probabilities) {
        this.probabilities = probabilities;
    }

    public Map<String, WinCombination> getWinCombinations() {
        return winCombinations;
    }

    public void setWinCombinations(Map<String, WinCombination> winCombinations) {
        this.winCombinations = winCombinations;
    }

    public Map<String, Symbol> getSymbols() {
        return symbols;
    }

    public void setSymbols(Map<String, Symbol> symbols) {
        this.symbols = symbols;
    }

}
