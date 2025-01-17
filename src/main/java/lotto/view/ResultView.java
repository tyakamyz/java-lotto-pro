package lotto.view;

import lotto.model.Lottos;
import lotto.model.MatchPoint;
import lotto.model.PurchasePrice;
import lotto.model.WinningStatus;
import lotto.util.MessageUtil;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ResultView {
    private final static String PURCHASE_LOTTOS_COUNT_MESSAGE = "수동으로 %d장, 자동으로 %d장을 구매했습니다.";
    private final static String WINNING_STATISTICS_TITLE_MESSAGE = "당첨 통계";
    private final static String WINNING_STATISTICS_LINE_MESSAGE = "---------";
    private final static String WINNING_STATISTICS_MESSAGE = "%d개 일치 ";
    private final static String WINNING_STATISTICS_RESULT_MESSAGE = "(%d원)- %d개";
    private final static String BONUS_BALL_MATCH_MESSAGE = "\b, 보너스 볼 일치";
    private final static String TOTAL_EARNINGS_RATE_MESSAGE = "총 수익률은 %.2f입니다.";

    public final static String ERROR_DUPLICATION_NUMBER = "중복된 값이 있습니다.";

    private final MessageUtil message;

    public ResultView() {
        message = new MessageUtil();
    }

    public void printPurchaseLottos(int manualLottoPurchaseCount, Lottos lottos) {
        message.printlnMessage();
        message.printlnMessage(String.format(PURCHASE_LOTTOS_COUNT_MESSAGE, manualLottoPurchaseCount, lottos.lottosCount() - manualLottoPurchaseCount));
        message.printlnMessage(lottos.numbersToString());
    }

    public void printWinningStatisticsTitle() {
        message.printlnMessage();
        message.printlnMessage(WINNING_STATISTICS_TITLE_MESSAGE);
        message.printlnMessage(WINNING_STATISTICS_LINE_MESSAGE);
    }

    public void printWinningStatistics(WinningStatus winningStatus) {
        List<MatchPoint> targetMatchPoints = Arrays.stream(MatchPoint.values()).filter(matchPoint -> matchPoint != MatchPoint.MISS).collect(Collectors.toList());
        for (MatchPoint matchPoint : targetMatchPoints) {
            message.printMessage(String.format(WINNING_STATISTICS_MESSAGE, matchPoint.getMatchPointCount()));
            printBonusBall(matchPoint);
            message.printlnMessage(String.format(WINNING_STATISTICS_RESULT_MESSAGE, matchPoint.getCashPrize(), winningStatus.findWinningCount(matchPoint)));
        }
    }

    private void printBonusBall(MatchPoint matchPoint) {
        if(matchPoint.equals(MatchPoint.SECOND)){
            message.printMessage(BONUS_BALL_MATCH_MESSAGE);
        }
    }

    public void printTotalEarningsRate(WinningStatus winningStatus, PurchasePrice purchasePrice) {
        message.printlnMessage(String.format(TOTAL_EARNINGS_RATE_MESSAGE, winningStatus.findEarningsRate(purchasePrice)));
    }
}
