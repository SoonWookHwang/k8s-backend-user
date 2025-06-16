package com.welab.k8sbackenduser.service.probe;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProbeService {

    public void validateLiveness(){
        //TODO: 만일 서비스를 재시작해야하는 상황이면 exception 발생
    }

    public void validateReadiness(){
        //TODO: 만일 서비스 수행을 일시 중지해야하는 상황이면 exception 발생
    }
}
