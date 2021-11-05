package lotto.domain;

import static java.util.stream.Collectors.*;
import static lotto.constant.ErrorMessage.*;
import static lotto.constant.LottoConstant.*;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import lotto.generator.NumberGenerator;
import lotto.utils.LottoNumberMapper;
import lotto.utils.MessageBuilder;

public class LottoNumbers {
	private final List<LottoNumber> lottoNumbers;

	private LottoNumbers(List<LottoNumber> lottoNumbers) {
		this.lottoNumbers = lottoNumbers;
	}

	public static LottoNumbers createBy(NumberGenerator numberGenerator) {
		List<LottoNumber> lottoNumbers = mapToLottoNumbers(numberGenerator.generate());
		return new LottoNumbers(lottoNumbers);
	}

	public static LottoNumbers of(List<Integer> numbers) {
		return new LottoNumbers(mapToLottoNumbers(numbers));
	}

	private static List<LottoNumber> mapToLottoNumbers(List<Integer> numbers) {
		List<LottoNumber> lottoNumbers = LottoNumberMapper.mapToLottoNumbers(numbers);
		validateLottoNumbers(lottoNumbers);

		return lottoNumbers;
	}

	private static void validateLottoNumbers(List<LottoNumber> lottoNumbers) {
		List<Integer> numbers = lottoNumbers.stream()
											.map(LottoNumber::getNumber)
											.collect(toList());
		Set<LottoNumber> lottoNumbersSet = new LinkedHashSet<>(lottoNumbers);

		if (lottoNumbersSet.size() != VALID_LOTTO_NUMBER_COUNT) {
			throw new IllegalArgumentException(MessageBuilder.build(DUPLICATED_LOTTO_NUMBER, numbers));
		}
	}

	public int countWinningNumbers(LottoNumbers lastWinningNumbers) {
		return (int) this.lottoNumbers.stream()
									  .filter(lastWinningNumbers::contains)
									  .count();
	}

	public boolean contains(LottoNumber lottoNumber) {
		return this.lottoNumbers.contains(lottoNumber);
	}

	public int getSize() {
		return this.lottoNumbers.size();
	}

	public List<LottoNumber> getValues() {
		return this.lottoNumbers;
	}

	@Override
	public String toString() {
		return this.lottoNumbers.stream()
								.map(LottoNumber::getNumber)
								.collect(toList())
								.toString();
	}
}