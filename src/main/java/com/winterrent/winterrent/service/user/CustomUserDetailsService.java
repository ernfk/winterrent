package com.winterrent.winterrent.service.user;

import com.winterrent.winterrent.dao.user.UserDAO;
import com.winterrent.winterrent.entity.Role;
import com.winterrent.winterrent.entity.RoleName;
import com.winterrent.winterrent.entity.User;
import com.winterrent.winterrent.entity.UserProfile;
import com.winterrent.winterrent.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = getUser(usernameOrEmail);

        return UserPrincipal.create(user);
    }

    public UserDetails loadUserById(Long id) {
        User user = userDAO
                .findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));

        return UserPrincipal.create(user);
    }

    public UserProfile getUserProfileByUsername(String username) {
        User user = getUser(username);
        Optional<UserProfile> opt = this.userDAO.getUserProfileByUser(user);

        return opt.orElse(null);
    }

    public UserProfile updateUserProfile(UserProfile userProfile, String username) {
        User user = getUser(username);
        userProfile.setUser(user);

        return userDAO.updateUserProfile(userProfile, username);
    }

    public boolean isUserAdmin(String username) {
        User user = getUser(username);
        Set<Role> userRoles = user.getRoles();

        return userRoles
                .stream()
                .anyMatch(role -> role.getName().equals(RoleName.ROLE_ADMIN));
    }

    private User getUser(String usernameOrEmail) {
        return userDAO
                .findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with user name or email: " + usernameOrEmail));
    }
}
