package com.ssafy.eggmoney.stock.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@NoArgsConstructor
@Getter
public class StockUserResponse {
    private int buyAverage;
    private int amount;
    private int totalInvestment;
    private int value;
    private int roi;

    public StockUserResponse(int buyAverage, int amount, int price) {
        this.buyAverage = buyAverage;
        this.amount = amount;
        this.totalInvestment = buyAverage * amount;
        this.value = price * amount;
        this.roi = BigDecimal.valueOf(value - totalInvestment)
                .divide(BigDecimal.valueOf(totalInvestment), 2, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100)).intValue();
    }
}