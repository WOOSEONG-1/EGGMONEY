package com.ssafy.eggmoney.loan.controller;

import com.ssafy.eggmoney.loan.dto.request.LoanCreateRequestDto;
import com.ssafy.eggmoney.loan.dto.request.LoanEvaluationRequestDto;
import com.ssafy.eggmoney.loan.dto.response.LoanDetailResponseDto;
import com.ssafy.eggmoney.loan.dto.response.LoanLogListResponseDto;
import com.ssafy.eggmoney.loan.dto.response.LoanPrivateListResponseDto;
import com.ssafy.eggmoney.loan.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/fin/loan")
@RestController
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    /**
     * 대출생성
     * @param requestDto
     * return
     * */
    @PostMapping("/create")
    public ResponseEntity<?> createLoan(@RequestBody LoanCreateRequestDto requestDto) {
        loanService.createLoan(requestDto);
        return ResponseEntity.ok().build();
    }

    /**
     * 개인 대출 조회
     * @param userId
     * return List<LoanPrivateListResponseDto>
     * */
    @GetMapping("/{userId}")
    public ResponseEntity<List<LoanPrivateListResponseDto>> getPrivateLoans(@PathVariable long userId) {
        List<LoanPrivateListResponseDto> result = loanService.getPrivateLoans(userId);

        return ResponseEntity.ok().body(result);
    }

    /**
     * 대출상세조회
     * @param loanId
     * return LoanDetailResponseDto
     * */
    @GetMapping("/detail/{loanId}")
    public ResponseEntity<LoanDetailResponseDto> getDetailLoan(@PathVariable long loanId){
        LoanDetailResponseDto result = loanService.getDetailLoan(loanId);

        return ResponseEntity.ok().body(result);
    }

    /**
     * 대출심사
     * @param loanId, requestDto
     * return
     * */
    @PostMapping("/judge/{loanId}")
    public ResponseEntity<?> evaluation(@PathVariable long loanId, @RequestBody LoanEvaluationRequestDto requestDto ) {
        loanService.loanEvaluation(loanId, requestDto);

        return ResponseEntity.ok().build();
    }

    /**
     * 대출금상환
     * @param loanId
     * return
     * */
    @PostMapping("/send/{loanId}")
    public ResponseEntity<?> repayment(@PathVariable long loanId) {
        loanService.sendRepayment(loanId);

        return ResponseEntity.ok().build();
    }

    /**
     * 대출금 납입 로그 조회
     * @param loanId
     * return List<LoanLogListResponseDto>
     * */
    @GetMapping("/log/{loanId}")
    public ResponseEntity<List<LoanLogListResponseDto>> getLoanLogs(@PathVariable long loanId) {
        List<LoanLogListResponseDto> result = loanService.getLoanLogs(loanId);
        return ResponseEntity.ok().body(result);
    }


}