package nextstep.lotto.domain;

import nextstep.lotto.domain.MatchCount.LottoWinningPrice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MatchCountCollection implements Iterable<MatchCount> {

    private final List<MatchCount> matchCounts;

    public MatchCountCollection(List<MatchCount> matchCounts) {
        this.matchCounts = matchCounts;
    }

    private MatchCountCollection(Map<LottoWinningPrice, MatchCount> matchCountMap) {

        List<MatchCount> matchCounts = new ArrayList<>();
        for (Map.Entry<LottoWinningPrice, MatchCount> entry : matchCountMap.entrySet()) {
            MatchCount value = entry.getValue();
            matchCounts.add(value);
        }

        Collections.sort(matchCounts);
        this.matchCounts = matchCounts;
    }

    public static MatchCountCollection matchPurchaseLottoWithWinningLotto(PurchaseLotto purchaseLotto, WinningLotto winningLotto) {

        Map<LottoWinningPrice, MatchCount> matchCountMap = MatchCount.initMatchCountCache();
        for (Lotto eachLotto : purchaseLotto) {
            Integer calculatedCount = winningLotto.matchWithPurchaseLottoCount(eachLotto);
            Boolean bonusBallContains = winningLotto.isBonusBallContains(eachLotto);
            LottoWinningPrice lottoWinningPrice = LottoWinningPrice.winningPrice(calculatedCount, bonusBallContains);
            loadMatchCountMap(matchCountMap, lottoWinningPrice, matchCountMap.get(lottoWinningPrice));
        }

        return new MatchCountCollection(matchCountMap);
    }


    private static void loadMatchCountMap(Map<LottoWinningPrice, MatchCount> matchCountMap, LottoWinningPrice key, MatchCount value) {

        if (key == LottoWinningPrice.NONE) {
            return;
        }

        if (Objects.isNull(value)) {
            matchCountMap.put(key, new MatchCount(key, 1));
            return;
        }

        matchCountMap.put(key, value.addToMatchCount());
    }

    public Integer size() {
        return matchCounts.size();
    }

    public List<MatchCount> getMatchCounts() {
        return matchCounts;
    }

    @Override
    public Iterator<MatchCount> iterator() {
        return matchCounts.iterator();
    }
}