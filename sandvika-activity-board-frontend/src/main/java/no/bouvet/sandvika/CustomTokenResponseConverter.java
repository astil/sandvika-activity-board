package no.bouvet.sandvika;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.util.StringUtils;

import javax.annotation.Nullable;
import java.util.*;

public class CustomTokenResponseConverter implements Converter<Map<String, Object>, OAuth2AccessTokenResponse> {

    private static final Set<String> TOKEN_RESPONSE_PARAMETER_NAMES = new HashSet<>(Arrays.asList(
            OAuth2ParameterNames.ACCESS_TOKEN,
            OAuth2ParameterNames.TOKEN_TYPE,
            OAuth2ParameterNames.EXPIRES_IN,
            OAuth2ParameterNames.REFRESH_TOKEN,
            OAuth2ParameterNames.SCOPE
    ));

    @Override
    public OAuth2AccessTokenResponse convert(@Nullable Map<String, Object> tokenResponseParameters) {
        if (tokenResponseParameters == null) {
            return null;
        }

        String accessToken = (String) tokenResponseParameters.get(OAuth2ParameterNames.ACCESS_TOKEN);

        OAuth2AccessToken.TokenType accessTokenType = null;
        if (OAuth2AccessToken.TokenType.BEARER.getValue().equalsIgnoreCase((String) tokenResponseParameters.get(OAuth2ParameterNames.TOKEN_TYPE))) {
            accessTokenType = OAuth2AccessToken.TokenType.BEARER;
        }

        long expiresIn = 0;
        if (tokenResponseParameters.containsKey(OAuth2ParameterNames.EXPIRES_IN)) {
            try {
                expiresIn = ((Integer) tokenResponseParameters.get(OAuth2ParameterNames.EXPIRES_IN)).longValue();
            } catch (NumberFormatException ignore) { }
        }

        Set<String> scopes = Collections.emptySet();
        if (tokenResponseParameters.containsKey(OAuth2ParameterNames.SCOPE)) {
            String scope = (String) tokenResponseParameters.get(OAuth2ParameterNames.SCOPE);
            scopes = new HashSet<>(Arrays.asList(StringUtils.delimitedListToStringArray(scope, " ")));
        }

        String refreshToken = (String) tokenResponseParameters.get(OAuth2ParameterNames.REFRESH_TOKEN);

        Map<String, Object> additionalParameters = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : tokenResponseParameters.entrySet()) {
            if (!TOKEN_RESPONSE_PARAMETER_NAMES.contains(entry.getKey())) {
                additionalParameters.put(entry.getKey(), entry.getValue());
            }
        }

        return OAuth2AccessTokenResponse.withToken(accessToken)
                .tokenType(accessTokenType)
                .expiresIn(expiresIn)
                .scopes(scopes)
                .refreshToken(refreshToken)
                .additionalParameters(additionalParameters)
                .build();
    }

}
