package mn.astvision.starter.filter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import mn.astvision.starter.exception.auth.AuthorizationException;
import mn.astvision.starter.util.JwtTokenUtil;
import mn.astvision.starter.util.LocalizationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author digz6666
 */
@Slf4j
public class JwtAuthorizationTokenFilter extends OncePerRequestFilter {

    @Value("${jwt.token.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private LocalizationUtil localizationUtil;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain chain
    ) throws ServletException, IOException {
        String authToken = request.getHeader(tokenHeader);
        if (ObjectUtils.isEmpty(authToken)) {
            chain.doFilter(request, response);
            return;
        }

        if (!jwtTokenUtil.validateToken(authToken))
            throw new AuthorizationException(localizationUtil.buildMessage("error.UNAUTHORIZED"));

        try {
            String username = jwtTokenUtil.getUsernameFromToken(authToken);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            chain.doFilter(request, response);
        } catch (MalformedJwtException e) {
            throw new AuthorizationException(localizationUtil.buildMessage("auth.jwt.malformed"));
        } catch (ExpiredJwtException e) {
            throw new AuthorizationException(localizationUtil.buildMessage("auth.jwt.expired"));
        } catch (Exception e) {
//            log.error(e.getMessage(), e);
            throw new AuthorizationException(e.getMessage());
        }
    }
}
