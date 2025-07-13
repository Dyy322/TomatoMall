package com.example.tomatomall.service.serviceImpl;

import com.example.tomatomall.exception.TomatoMallException;
import com.example.tomatomall.po.Account;
import com.example.tomatomall.repository.AccountRepository;
import com.example.tomatomall.service.AccountService;
import com.example.tomatomall.util.SecurityUtil;
import com.example.tomatomall.util.TokenUtil;
import com.example.tomatomall.vo.AccountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    SecurityUtil securityUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String login(String username, String password) {

        Account account = accountRepository.findByUsername(username);
        if(account==null){
            throw TomatoMallException.usernameOrPasswordError();
        }else{
            if(!passwordEncoder.matches(password,account.getPassword())){
                throw TomatoMallException.usernameOrPasswordError();
            }
        }

        return tokenUtil.getToken(account);
    }

    @Override
    public Boolean register(AccountVO accountVO) {
        Account account = accountRepository.findByUsername(accountVO.getUsername());
        if(account!=null){
            throw TomatoMallException.usernameExist();
        }
        account = accountVO.toPO();
        String rawPassword = account.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        account.setPassword(encodedPassword);
        accountRepository.save(account);
        return true;
    }

    @Override
    public AccountVO getCurrentAccountInfo() {
        Account account = securityUtil.getCurrentAccount();
        return account.toVO();
    }

    @Override
    public AccountVO getAccountInfo(String username) {

        Account account = accountRepository.findByUsername(username);
        if(account==null){
            throw TomatoMallException.usernameNotExist();
        }
        return accountRepository.findByUsername(username).toVO();
    }

    @Override
    public Boolean updateAccountInfo(AccountVO accountVO) {
        Account account=accountRepository.findByUsername(accountVO.getUsername());
        if(accountVO.getPassword()!=null){
            String rawPassword = accountVO.getPassword();
            String encodedPassword = passwordEncoder.encode(rawPassword);
            account.setPassword(encodedPassword);
        }
        if(accountVO.getName()!=null){
            account.setName(accountVO.getName());
        }
        if(accountVO.getAvatar()!=null){
            account.setAvatar(accountVO.getAvatar());
        }
        if(accountVO.getRole()!=null){
            account.setRole(accountVO.getRole());
        }
        if(accountVO.getTelephone()!=null){
            account.setTelephone(accountVO.getTelephone());
        }
        if(accountVO.getEmail()!=null){
            account.setEmail(accountVO.getEmail());
        }
        if(accountVO.getLocation()!=null){
            account.setLocation(accountVO.getLocation());
        }
        accountRepository.save(account);
        return true;
    }

    @Override
    public AccountVO getAccountInfo(Integer accountId) {
        return accountRepository.findById(accountId).map(Account::toVO).orElse(null);
    }
}
