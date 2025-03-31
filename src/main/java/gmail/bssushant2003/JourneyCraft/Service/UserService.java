package gmail.bssushant2003.JourneyCraft.Service;

import gmail.bssushant2003.JourneyCraft.Entity.User;
import gmail.bssushant2003.JourneyCraft.Entity.UserDetailsDTO;
import gmail.bssushant2003.JourneyCraft.Repository.GuideRepository;
import gmail.bssushant2003.JourneyCraft.Repository.RestaurantRepository;
import gmail.bssushant2003.JourneyCraft.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GuideRepository guideRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    public List<UserDetailsDTO> gerUserByRole(User.Role role){
        List<User> users = userRepository.findByRole(role);
        List<UserDetailsDTO> userDetailsList = new ArrayList<>();

        for(User user : users){
            if(role == User.Role.GUIDE){
                guideRepository.findByUser(user).ifPresent(guide ->
                        userDetailsList.add(new UserDetailsDTO(
                                user.getId(), user.getEmail(), guide.getName(), guide.getPhoneNo(),
                                "Experience: " + guide.getExperience() + ", License: " + guide.getLicenseNumber()
                        ))
                );
            } else if (role == User.Role.RESTAURANT) {
                restaurantRepository.findByUser(user).ifPresent(restaurant ->
                        userDetailsList.add(new UserDetailsDTO(
                                user.getId(), user.getEmail(), restaurant.getName(), restaurant.getPhoneNo(),
                                "Rating: " + restaurant.getRating() + ", Food Type: " + restaurant.getFoodType()
                        ))
                );
            }
        }
    return userDetailsList;
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

}
