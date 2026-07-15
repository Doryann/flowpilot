package com.doryann.flowpilot.service;

import com.doryann.flowpilot.entity.AppUser;
import com.doryann.flowpilot.shared.AuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppOidcUserService implements OAuth2UserService<OidcUserRequest, OidcUser> {

    private final AppUserService appUserService;

    private final OidcUserService delegate = new OidcUserService();

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) {
        OidcUser oidcUser = delegate.loadUser(userRequest);

        String registrationId = userRequest
                .getClientRegistration()
                .getRegistrationId();

        AuthProvider provider = AuthProvider.fromRegistrationId(registrationId);

        String providerSubject = oidcUser.getSubject();
        String email = oidcUser.getEmail();
        String displayName = oidcUser.getFullName();
        String avatarUrl = oidcUser.getPicture();

        AppUser appUser = appUserService.createOrUpdateOAuthUser(
                provider,
                providerSubject,
                email,
                displayName,
                avatarUrl
        );

        //TODO:
        // Pour l’instant, on retourne l’utilisateur OIDC standard.
        // Ensuite, on pourra créer un principal custom contenant appUser.getId().
        return oidcUser;
    }
}