package com.example.dawine.service.user;

import com.example.dawine.model.user.AppUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IAppUserService extends UserDetailsService {
    AppUser findByUsername(String userName);

    Boolean createNewAppUser(AppUser appUser, String roleAdmin);
}
