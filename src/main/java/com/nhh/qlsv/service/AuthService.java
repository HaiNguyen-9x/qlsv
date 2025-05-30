package com.nhh.qlsv.service;

import com.nhh.qlsv.dto.LoginDto;

public interface AuthService {
    String login(LoginDto loginDto);
}
