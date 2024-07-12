package ae.cyberspeed.game;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class SymbolsProbability {
    private int column;
    private int row;
    @JsonProperty("symbols")
    private Map<String, String> symbols;

    public SymbolsProbability() {
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public Map<String, String> getSymbols() {
        return symbols;
    }

    public void setSymbols(Map<String, String> symbols) {
        this.symbols = symbols;
    }
}
