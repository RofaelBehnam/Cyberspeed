package ae.cyberspeed;

import ae.cyberspeed.game.Game;
import ae.cyberspeed.game.Result;
import ae.cyberspeed.game.Symbol;
import ae.cyberspeed.game.WinCombination;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {
    private static ObjectMapper mapper;
    private static int betAmount = 1;
    private static String configPath = "config.json";
    private static Game game;
    private static String[][] board;
    private static final String MISS = "MISS";

    private static void constructBoard() {
        board = new String[game.getRows()][game.getColumns()];
        for(int i = 0; i < game.getRows(); i++) {
            for (int j = 0; j < game.getColumns(); j++ ) {
                int size = game.getProbabilities().getStandardSymbols().get(i).getSymbols().entrySet().size();
                int rand = new Random().nextInt(size);
                Iterator<Map.Entry<String, String>> it = game.getProbabilities().getStandardSymbols().get(i).getSymbols().entrySet().iterator();
                while (rand-- > 0) {
                    board[i][j] = it.next().getKey();
                }
                if (board[i][j] == null) {
                    board[i][j] = MISS;
                }
            }
        }
    }

    private static double calculateReward() throws IOException {
        Result result = new Result();
        result.setMatrix(board);
        // get reward of all repetitive wining combinations
        Map<Integer, Double> repetitionValue = new HashMap<>();
        Iterator<Map.Entry<String, WinCombination>> it = game.getWinCombinations().entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry<String, WinCombination> entry = it.next();
            if(entry.getValue().getCount() != null)
                repetitionValue.put(entry.getValue().getCount().intValue(), entry.getValue().getRewardMultiplier().doubleValue());
        }

        // get reward of all symbols
        Map<String, Double> standardSymbolsReward = new HashMap<>();
        Map<String, Double> bonusSymbolsReward = new HashMap<>();

        for(Map.Entry<String, Symbol> entry: game.getSymbols().entrySet()) {
            if(entry.getKey().charAt(0) >= 'A' && entry.getKey().charAt(0) <= 'Z') {
                standardSymbolsReward.put(entry.getKey(), entry.getValue().getRewardMultiplier());
            } else {
                double extraMultiplier = 0;
                if(entry.getValue().getRewardMultiplier() != null) {
                    extraMultiplier = entry.getValue().getRewardMultiplier();
                } else {
                    if(entry.getValue().getExtra() != null) {
                        extraMultiplier = entry.getValue().getExtra();
                    }
                }
                bonusSymbolsReward.put(entry.getKey(),extraMultiplier);
            }
        }

        Map<String, Integer> countSymbols = new HashMap<>();
        for (int i = 0; i < board.length; i ++) {
            for (int j = 0; j < board[i].length; j ++) {
                int count = 0;
                if (countSymbols.containsKey(board[i][j])) {
                    count = countSymbols.get(board[i][j]);
                }
                countSymbols.put(board[i][j], count + 1);
            }
        }
        double totalReward = 0;
        for(Map.Entry<String, Integer> entrySet: countSymbols.entrySet()) {
            if (entrySet.getKey().compareTo(MISS) != 0) {
                double reward = betAmount * standardSymbolsReward.get(entrySet.getKey());
                int count = entrySet.getValue();
                for (int i = 2; i <= count; i++) {
                    if(repetitionValue.containsKey(i)) {
                        reward *= repetitionValue.get(i);
                        // add applied_winning_combinations
                        List l = new ArrayList<>();
                        if (result.getAppliedWinningCombinations().containsKey(entrySet.getKey()))
                            l = result.getAppliedWinningCombinations().get(entrySet.getKey());
                        l.add("same_symbol_" + i + "_times");
                        result.getAppliedWinningCombinations().put(entrySet.getKey(), l);
                    }
                }
                totalReward += reward;
            }
        }


        // calculate bonus reward
        for (int i = 0; i < board.length; i ++) {
            for (int j = 0; j < board[i].length; j ++) {
                if(board[i][j].charAt(0) == '+') {
                    if (bonusSymbolsReward.containsKey(board[i][j])) {
                        totalReward += bonusSymbolsReward.get(board[i][j]);
                        result.setAppliedBonusSymbol(board[i][j]);
                    }
                }
                if(board[i][j].charAt(0) == '*') {
                    if (bonusSymbolsReward.containsKey(board[i][j])) {
                        totalReward *= bonusSymbolsReward.get(board[i][j]);
                        result.setAppliedBonusSymbol(board[i][j]);
                    }
                }
            }
        }
        result.setReward(totalReward);
        mapper.writeValue(System.out, result);
        return totalReward;
    }

    public static void main(String[] args) throws IOException {
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--config":
                    configPath = args[++i];
                    break;
                case "--betting-amount":
                    betAmount = Integer.parseInt(args[++i]);
                    break;
            }
        }
        mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.INCLUDE_SOURCE_IN_LOCATION, true);
        game = mapper.readValue(new File(configPath), Game.class);
        constructBoard();
        System.out.println(calculateReward());

    }
}