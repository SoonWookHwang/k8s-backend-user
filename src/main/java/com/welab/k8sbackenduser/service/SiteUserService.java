package com.welab.k8sbackenduser.service;

import com.welab.k8sbackenduser.common.exception.BadParameter;
import com.welab.k8sbackenduser.common.exception.NotFound;
import com.welab.k8sbackenduser.domain.SiteUser;
import com.welab.k8sbackenduser.domain.dto.SiteUserLoginDto;
import com.welab.k8sbackenduser.domain.dto.SiteUserRegisterDto;
import com.welab.k8sbackenduser.domain.event.SiteUserInfoEvent;
import com.welab.k8sbackenduser.domain.repository.SiteUserRepository;
import com.welab.k8sbackenduser.event.producer.KafkaMessageProducer;
import com.welab.k8sbackenduser.secret.hash.SecureHashUtils;
import com.welab.k8sbackenduser.secret.jwt.TokenGenerator;
import com.welab.k8sbackenduser.secret.jwt.dto.TokenDto;
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
    private final TokenGenerator tokenGenerator;

    @Transactional
    public void registerUser(SiteUserRegisterDto registerDto) {
        SiteUser siteUser = registerDto.toEntity();
        siteUserRepository.save(siteUser);
        SiteUserInfoEvent event = SiteUserInfoEvent.fromEntity("Create", siteUser);
        kafkaMessageProducer.send(SiteUserInfoEvent.Topic, event);
    }
    @Transactional(readOnly = true)
    public TokenDto.AccessRefreshToken login(SiteUserLoginDto loginDto) {
        SiteUser user = siteUserRepository.findByUserId(loginDto.getUserId());
        if (user == null) {
            throw new NotFound("사용자를 찾을 수 없습니다.");
        }
        if( !SecureHashUtils.matches(loginDto.getPassword(), user.getPassword())){throw new BadParameter("비밀번호가 맞지 않습니다.");
        }
        return tokenGenerator.generateAccessRefreshToken(loginDto.getUserId(), "WEB");}
}
