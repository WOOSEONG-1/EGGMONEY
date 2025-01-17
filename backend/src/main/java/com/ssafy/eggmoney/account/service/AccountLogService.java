package com.ssafy.eggmoney.account.service;

import com.ssafy.eggmoney.account.dto.response.GetAccountLogResponseDto;
import com.ssafy.eggmoney.account.entity.Account;
import com.ssafy.eggmoney.account.entity.AccountLog;
import com.ssafy.eggmoney.account.entity.AccountLogType;
import com.ssafy.eggmoney.account.repository.AccountLogRepository;
import com.ssafy.eggmoney.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountLogService {
    private final AccountLogRepository accountLogRepository;
    private final AccountRepository accountRepository;

//    메인계좌 로그 조회
    public Page<GetAccountLogResponseDto> getAccountLogs(Long userId, Pageable pageable){
        Optional<Account> account = accountRepository.findByUserId(userId);
        if ( !account.isPresent() ){
            throw new NoSuchElementException("일치 계좌 없음");
        }
        return accountLogRepository.findLogsByAccountId(account.get().getId(), pageable)
                .map( log -> GetAccountLogResponseDto.builder()
                        .accountId(log.getAccount().getId())
                        .currentBalance(log.getCurrentBalance())
                        .tradePrice(log.getTradePrice())
                        .tradeTarget(log.getTradeTarget())
                        .createdAt(log.getCreatedAt())
                        .build());
    }

//    메인계좌 로그 조회 ( 3개월 이내 - 일 단위 )
    public List<GetAccountLogResponseDto> get3MAccountLogs(Long userId, Integer month){
        // 현재 시간
        LocalDateTime endDate = LocalDateTime.now();
        // 3개월 전
        LocalDateTime startDate = endDate.minusMonths(month);
        Account account = accountRepository.findByUserId(userId).orElse(null);

        List<GetAccountLogResponseDto> dto = accountLogRepository.findLatestLogsByDayAndAccountId(account.getId(), startDate, endDate).stream()
                .map( log -> GetAccountLogResponseDto.builder()
                        .accountId(log.getAccount().getId())
                        .currentBalance(log.getCurrentBalance())
                        .tradePrice(log.getTradePrice())
                        .tradeTarget(log.getTradeTarget())
                        .createdAt(log.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
        return dto;
    }

//    메인계좌 로그 생성
    public void createAccountLog(Long userId, AccountLogType logType, int price) {
        Account account = accountRepository.findByUserId(userId).get();

        AccountLog savedLog = accountLogRepository.save(
                AccountLog.builder()
                        .account(account)
                        .currentBalance(account.getBalance() + price)
                        .tradeTarget(logType)
                        .tradePrice(price)
                        .build()
        );
    }

}
