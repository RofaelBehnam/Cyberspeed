package ae.cyberspeed.game;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Probability {
    @JsonProperty("standard_symbols")
    private List<SymbolsProbability> standardSymbols;

    @JsonProperty("bonus_symbols")
    private  BonusSymbolsProbability bonusSymbol;

    public List<SymbolsProbability> getStandardSymbols() {
        return standardSymbols;
    }

    public void setStandardSymbols(List<SymbolsProbability> standardSymbols) {
        this.standardSymbols = standardSymbols;
    }

    public BonusSymbolsProbability getBonusSymbol() {
        return bonusSymbol;
    }

    public void setBonusSymbol(BonusSymbolsProbability bonusSymbol) {
        this.bonusSymbol = bonusSymbol;
    }
}
