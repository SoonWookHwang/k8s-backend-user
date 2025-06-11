package com.welab.k8sbackenduser.remote.alim;

import com.welab.k8sbackenduser.common.dto.ApiResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "RemoteAlimService", url = "http://k8s-backend-alim-service:8080", path = "/backend/alim/v1")
public interface RemoteAlimService {
    @GetMapping(value = "/hello")
    public ApiResponseDto<String> hello();
}