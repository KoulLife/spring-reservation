package ToMist.reservation.service;

import ToMist.reservation.model.User;
import ToMist.reservation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
  private final UserRepository userRepository;
//  private final PasswordEncoder passwordEncoder;
//  private final RoleRepository roleRepository;

  @Override
  public User registerUser(User user) {
    return null;
  }

  @Override
  public List<User> getUsers() {
    return userRepository.findAll();
  }

  @Override
  public void deleteUser(String email) {
    User theUser = getUser(email);
    if(theUser != null){
      userRepository.deleteByEmail(email);
    }
  }

  @Override
  public User getUser(String email) {
    return userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }
}
