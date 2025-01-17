package com.ssafy.eggmoney.stock.service;

import com.ssafy.eggmoney.stock.dto.request.StockBuyRequest;
import com.ssafy.eggmoney.stock.dto.request.StockSellRequest;
import com.ssafy.eggmoney.stock.dto.response.StockUserResponse;

import java.util.Map;

public interface StockUserService {
    Map<String, Object> findInvestablePrice(Long userId);
    Map<String, Object> findUserStocks(Long userId);
    void buyStock(StockBuyRequest stockBuy, Long userId);
    void sellStock(StockSellRequest stockSellReq, Long userId);
    StockUserResponse findStockUserInfo(Long stockId, Long userId);
}
