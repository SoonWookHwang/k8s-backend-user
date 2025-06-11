package com.welab.k8sbackenduser.api.open;

import com.welab.k8sbackenduser.common.dto.ApiResponseDto;
import com.welab.k8sbackenduser.remote.alim.RemoteAlimService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api/user/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserController {
    private final RemoteAlimService remoteAlimService;

    @GetMapping(value = "/hello")
    public ApiResponseDto<String> hello() {
        String remoteMessage = remoteAlimService.hello().getData();
        String userResponse = "웰컴 투 백엔드 유저. 리모트 알림 메시지= " + remoteMessage;
        return ApiResponseDto.createOk(userResponse);
    }
}