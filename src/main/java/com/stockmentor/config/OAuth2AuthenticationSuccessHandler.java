package com.stockmentor.config;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.web.util.UriComponentsBuilder;

import com.stockmentor.entity.User;
import com.stockmentor.exception.BusinessException;
import com.stockmentor.exception.ErrorCode;
import com.stockmentor.repository.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{
    
    private final JwtUtil jwtUtil;
    private final UserRepository userRepo;
    private final String successRedirectUri;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        
        DefaultOAuth2User oAuth2User = (DefaultOAuth2User)authentication.getPrincipal();

        String provider = OAuth2AttributeExtractor.extractProvider(request);
        String socialId = OAuth2AttributeExtractor.extractSocialId(provider, oAuth2User.getAttributes());

        User user = userRepo.findBySocialProviderAndSocialId(provider.toUpperCase(), socialId)
                            .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        String accessToken = jwtUtil.generateAccessToken(user.getId());

        String redirectUrl = UriComponentsBuilder
                                .fromUriString(successRedirectUri)
                                .queryParam("token", accessToken)
                                .queryParam("isNewUser", user.getRiskType() == null)
                                .build().toUriString();
        
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}
