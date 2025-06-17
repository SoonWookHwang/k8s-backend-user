package com.welab.k8sbackenduser.api.open;

import com.welab.k8sbackenduser.common.dto.ApiResponseDto;
import com.welab.k8sbackenduser.domain.dto.SiteUserLoginDto;
import com.welab.k8sbackenduser.domain.dto.SiteUserRegisterDto;
import com.welab.k8sbackenduser.secret.jwt.dto.TokenDto;
import com.welab.k8sbackenduser.service.SiteUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/user/v1/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserAuthController {
    private final SiteUserService siteUserService;

    @PostMapping(value = "/register")
    public ApiResponseDto<String> register(@RequestBody @Valid SiteUserRegisterDto registerDto) {
        siteUserService.registerUser(registerDto);
        return ApiResponseDto.defaultOk();
    }

    @PostMapping(value = "/login")
    public ApiResponseDto<TokenDto.AccessRefreshToken> login(@RequestBody @Valid SiteUserLoginDto loginDto) {
        TokenDto.AccessRefreshToken token = siteUserService.login(loginDto);
        return ApiResponseDto.createOk(token);
    }

    @GetMapping(value = "/test")
    public ApiResponseDto<String> versionTest(){
        return ApiResponseDto.createOk("버전 1입니다");
    }

}
