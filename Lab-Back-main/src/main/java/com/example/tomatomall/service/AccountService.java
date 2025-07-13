package com.example.tomatomall.service;

import com.example.tomatomall.vo.AccountVO;

public interface AccountService {
    String login(String username, String password);

    Boolean register(AccountVO accountVO);

    AccountVO getCurrentAccountInfo();

    AccountVO getAccountInfo(String username);

    Boolean updateAccountInfo(AccountVO accountVO);

    AccountVO getAccountInfo(Integer accountId);
}
