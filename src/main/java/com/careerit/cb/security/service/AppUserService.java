package com.careerit.cb.security.service;

import com.careerit.cb.security.domain.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AppUserService implements UserDetailsService {

  @Autowired
  private PasswordEncoder passwordEncoder;
  private List<AppUser> usersList;
  private Map<String, AppUser> map;

  public AppUserService() {

  }
  @PostConstruct
  public void init(){
    this.usersList = new ArrayList<>();
    this.usersList.add(new AppUser("krish", passwordEncoder.encode("krish@123")));
    this.usersList.add(new AppUser("manoj", passwordEncoder.encode("manoj@123")));
    this.usersList.add(new AppUser("charan", passwordEncoder.encode("charan@123")));
    map = usersList.stream().collect(Collectors.toMap(AppUser::getUsername, Function.identity()));
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         if(map.get(username)!=null){
           AppUser appUser = map.get(username);
           return User.builder().username(appUser.getUsername()).password(appUser.getPassword()).authorities(Collections.emptyList()).build();
         }
         throw new UsernameNotFoundException("User with given name not exists");
  }
}
