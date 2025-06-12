package com.welab.k8sbackenduser.service;

import com.welab.k8sbackenduser.domain.SiteUser;
import com.welab.k8sbackenduser.domain.dto.SiteUserRegisterDto;
import com.welab.k8sbackenduser.domain.event.SiteUserInfoEvent;
import com.welab.k8sbackenduser.domain.repository.SiteUserRepository;
import com.welab.k8sbackenduser.event.producer.KafkaMessageProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SiteUserService {

    private final SiteUserRepository siteUserRepository;
    private final KafkaMessageProducer kafkaMessageProducer;

    @Transactional
    public void registerUser(SiteUserRegisterDto registerDto) {
        SiteUser siteUser = registerDto.toEntity();
        siteUserRepository.save(siteUser);
        SiteUserInfoEvent event = SiteUserInfoEvent.fromEntity("Create", siteUser);
        kafkaMessageProducer.send(SiteUserInfoEvent.Topic, event);
    }
}
