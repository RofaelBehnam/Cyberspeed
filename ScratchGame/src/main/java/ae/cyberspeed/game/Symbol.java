package ae.cyberspeed.game;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Symbol {
    @JsonProperty("reward_multiplier")
    private Double rewardMultiplier;
    private String type;
    private Integer extra;
    private String impact;

    public Symbol() {
    }

    public Symbol(Double rewardMultiplier, String type) {
        this.rewardMultiplier = rewardMultiplier;
        this.type = type;
    }

    public Double getRewardMultiplier() {
        return rewardMultiplier;
    }

    public void setRewardMultiplier(Double rewardMultiplier) {
        this.rewardMultiplier = rewardMultiplier;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getExtra() {
        return extra;
    }

    public void setExtra(Integer extra) {
        this.extra = extra;
    }

    public String getImpact() {
        return impact;
    }

    public void setImpact(String impact) {
        this.impact = impact;
    }
}
