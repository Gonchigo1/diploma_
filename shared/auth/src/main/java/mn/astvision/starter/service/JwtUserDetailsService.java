package mn.astvision.starter.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mn.astvision.starter.exception.auth.BusinessRoleException;
import mn.astvision.starter.model.auth.BusinessRole;
import mn.astvision.starter.model.auth.enums.ApplicationRole;
import mn.astvision.starter.repository.auth.BusinessRoleRepository;
import mn.astvision.starter.repository.auth.UserRepository;
import mn.astvision.starter.util.LocalizationUtil;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author digz6666
 */
@Slf4j
@Service("jwtUserDetailsService")
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final BusinessRoleRepository businessRoleRepository;
    private final LocalizationUtil localizationUtil;
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String email) throws AuthenticationException {
        mn.astvision.starter.model.auth.User user = userRepository.findByEmailAndDeletedFalse(email);
        if (user == null)
            throw new UsernameNotFoundException(
                    localizationUtil.buildMessage("auth.user.notFound") + ": " + email);

        if (!user.isActive())
            throw new DisabledException(
                    localizationUtil.buildMessage("auth.accountDisabled"));

        if (ObjectUtils.isEmpty(user.getRole()))
            throw new BusinessRoleException(localizationUtil.buildMessage("auth.businessRole.notFound"));

        Optional<BusinessRole> businessRoleOptional = businessRoleRepository.findById(user.getRole());
        if (businessRoleOptional.isPresent()) {
            BusinessRole businessRole = businessRoleOptional.get();
            List<ApplicationRole> userRoles = businessRole.getApplicationRoles().stream()
                    .filter(Objects::nonNull)
                    .toList();

            List<GrantedAuthority> authorities = userRoles.stream()
                    .filter(Objects::nonNull)
                    .map(r -> new SimpleGrantedAuthority(r.getValue()))
                    .collect(Collectors.toList());
            return new User(
                    user.getEmail(),
                    user.getPassword() != null ? user.getPassword() : "",
                    user.isActive(),
                    true,
                    true,
                    true,
                    authorities);
        } else
            throw new BusinessRoleException(localizationUtil.buildMessage("auth.businessRole.notFound"));
    }
}
