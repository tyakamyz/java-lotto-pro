package lotto.model;

import java.util.HashMap;
import java.util.Map;

public class WinningStatus {

    private final Map<MatchPoint, Integer> winningStatus;

    public WinningStatus() {
        winningStatus = new HashMap<>();
        for (MatchPoint value : MatchPoint.values()) {
            winningStatus.put(value, 0);
        }
    }

    protected void recordResults(int count, boolean matchBonus) {

        MatchPoint matchPoint = MatchPoint.findMatchPointByMatchPointCount(count, matchBonus);
        if(matchPoint != MatchPoint.MISS){
            winningStatus.put(matchPoint, winningStatus.get(matchPoint) + 1);
        }
    }

    public int findWinningCount(MatchPoint matchPoint) {
        return winningStatus.get(matchPoint);
    }

    public double findEarningsRate(PurchasePrice purchasePrice) {
        long sum = 0;

        for (MatchPoint matchPoint : MatchPoint.values()) {
            sum = sum + matchPoint.sumCashPrizeByMatchPoint(winningStatus.get(matchPoint));
        }
        return  purchasePrice.averageRate(sum);
    }
}
