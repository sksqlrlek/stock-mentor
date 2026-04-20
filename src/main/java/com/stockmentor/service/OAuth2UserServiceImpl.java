package com.stockmentor.service;

import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.stockmentor.config.OAuth2AttributeExtractor;
import com.stockmentor.entity.User;
import com.stockmentor.exception.BusinessException;
import com.stockmentor.exception.ErrorCode;
import com.stockmentor.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OAuth2UserServiceImpl extends DefaultOAuth2UserService{
    
    private final UserRepository userRepo;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String socialId = OAuth2AttributeExtractor.extractSocialId(provider, attributes);
        String nickname = OAuth2AttributeExtractor.extractNickname(provider, attributes);
        String email = OAuth2AttributeExtractor.extractEmail(provider, attributes);

        User user = userRepo.findBySocialProviderAndSocialId(
            provider.toUpperCase(), socialId)
            .orElseGet(() -> userRepo.save(User.createSocial(email, nickname, provider.toUpperCase(), socialId)));

        return new DefaultOAuth2User(
            Collections.singleton(
                new SimpleGrantedAuthority("ROLE_USER")), 
                attributes, 
                userRequest.getClientRegistration()
                            .getProviderDetails()
                            .getUserInfoEndpoint()
                            .getUserNameAttributeName());
    }
}


