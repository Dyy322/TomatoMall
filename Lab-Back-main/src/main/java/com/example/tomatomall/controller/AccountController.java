package com.example.tomatomall.controller;

import com.example.tomatomall.service.AccountService;
import com.example.tomatomall.vo.AccountVO;
import com.example.tomatomall.vo.Response;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Resource
    AccountService accountService;


    /**
     * 获取用户详情
     */
    @GetMapping("/{username}")
    public Response<AccountVO> getUser(@PathVariable String username) {

        return Response.buildSuccess(accountService.getAccountInfo(username));

    }

    /**
     * 创建新的用户
     */
    @PostMapping
    public Response<String> createUser(@RequestBody AccountVO accountVO) {
        if(accountService.register(accountVO)){
            return Response.buildSuccess("注册成功");
        }
        return null;
    }

    /**
     * 更新用户信息
     */
    @PutMapping
    public Response<String> updateUser(@RequestBody AccountVO accountVO) {
        if(accountService.updateAccountInfo(accountVO)){
            return Response.buildSuccess("更新成功");
        }
        return null;
    }

    /**
     * 登录
     */
    @PostMapping("/login")
    public Response<String> login(@RequestBody AccountVO accountVO) {
        return Response.buildSuccess(accountService.login(accountVO.getUsername(), accountVO.getPassword()));
    }
}
