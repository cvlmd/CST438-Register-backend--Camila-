package  com.cst438.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cst438.domain.Assignment;
import com.cst438.domain.AssignmentRepository;



@Service
public class UserDetailsServiceImpl implements UserDetailsService  {
    @Autowired
    private AssignmentRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<Assignment> currentUser = repository.findByEmail(username); 

        UserBuilder builder = null;
        if (currentUser!=null) {
            builder = org.springframework.security.core.userdetails.User.withUsername(username);
            builder.password(((User)
            		currentUser).getPassword());
            builder.roles(((Object) currentUser).getRole());
        } else {
            throw new UsernameNotFoundException("User not found.");
        }

        return builder.build();     
    }
}