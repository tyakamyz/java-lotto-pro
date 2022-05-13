package lotto.model;

import java.util.HashMap;
import java.util.Map;

public class WinningStatus {

    private Map<MatchPoint, Integer> winningStatus;

    public WinningStatus() {
        winningStatus = new HashMap<>();
        for (MatchPoint value : MatchPoint.values()) {
            winningStatus.put(value, 0);
        }
    }

    protected void recordResults(int count) {
        if(count == 3){
            winningStatus.put(MatchPoint.THREE, winningStatus.get(MatchPoint.THREE) + 1);
            return;
        }

        if(count == 4){
            winningStatus.put(MatchPoint.FOUR, winningStatus.get(MatchPoint.FOUR) + 1);
            return;
        }

        if(count == 5){
            winningStatus.put(MatchPoint.FIVE, winningStatus.get(MatchPoint.FIVE) + 1);
            return;
        }

        if(count == 6){
            winningStatus.put(MatchPoint.SIX, winningStatus.get(MatchPoint.SIX) + 1);
        }
    }

    protected int findWinningCount(MatchPoint matchPoint) {
        return winningStatus.get(matchPoint);
    }

    public double findEarningsRate(long lottosTotalPrice) {
        long sum = 0;

        for (MatchPoint matchPoint : MatchPoint.values()) {
            sum = sum + matchPoint.sumCashPrizeByMatchPoint(winningStatus.get(matchPoint));
        }

        return Math.floor(((double) sum/lottosTotalPrice) * 100) / 100.0;
    }
}