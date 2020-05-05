package pl.potoczak.myexam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.potoczak.myexam.model.User;
import pl.potoczak.myexam.repository.UserRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserDetails user = userRepository.findUserByUsername(s);
        if (user == null) throw new UsernameNotFoundException("Can't find user: " + s);
        UserDetails userDetails = (UserDetails) new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), getAuthorities(user)
//                getAuthorities(user)
        );
        return userDetails;
    }

    private Collection<? extends GrantedAuthority> getAuthorities(UserDetails user) {
//        return Collections.singleton(new SimpleGrantedAuthority(user.getRole().getName()));
        return user.getAuthorities();
    }

//    private static Collection<? extends GrantedAuthority> getAuthorities(User user) {
//        return new HashSet<GrantedAuthority>();
//    }

//    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
//        return getGrantedAuthorities(getPrivileges(roles));
//    }
//
//    private List<String> getPrivileges(Collection<Role> roles) {
//        List<String> privileges = new ArrayList<>();
//        List<Privilege> collection = new ArrayList<>();
//        for (Role role : roles) {
//            collection.addAll(role.getPrivileges());
//        }
//        for (Privilege item : collection) {
//            privileges.add(item.getName());
//        }
//        return privileges;
//    }
//
//    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        for (String privilege : privileges) {
//            authorities.add(new SimpleGrantedAuthority(privilege));
//        }
//        return authorities;
//    }
}