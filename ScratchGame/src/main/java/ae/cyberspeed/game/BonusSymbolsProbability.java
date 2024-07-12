package ae.cyberspeed.game;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class BonusSymbolsProbability {
    @JsonProperty("symbols")
    private Map<String, String> symbols;
}
