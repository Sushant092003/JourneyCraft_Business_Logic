package gmail.bssushant2003.JourneyCraft.Controller;

import gmail.bssushant2003.JourneyCraft.Entity.Restaurant;
import gmail.bssushant2003.JourneyCraft.Entity.RestaurantDTO;
import gmail.bssushant2003.JourneyCraft.Entity.User;
import gmail.bssushant2003.JourneyCraft.Repository.UserRepository;
import gmail.bssushant2003.JourneyCraft.Service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/restaurant")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register/restaurant/{userId}")
    public ResponseEntity<Restaurant> registerRestaurant(@RequestBody RestaurantDTO restaurantDTO, @PathVariable Long userId){
        User user = userRepository.findById(userId).orElse(null);
        if(user == null){
            throw new RuntimeException("User with userId " + userId + "not found");
        }

        Restaurant restaurant = new Restaurant();
        restaurant.setUser(user);
        restaurant.setName(restaurantDTO.getName());
        restaurant.setRating(restaurantDTO.getRating());
        restaurant.setLocationLink(restaurantDTO.getLocationLink());
        restaurant.setFssaiLicense(restaurantDTO.getFssaiLicense());
        restaurant.setOpenTime(restaurantDTO.getOpenTime());
        restaurant.setCloseTime(restaurantDTO.getOpenTime());
        restaurant.setDescription(restaurantDTO.getDescription());
        restaurant.setPhoneNo(restaurantDTO.getPhoneNo());
        restaurant.setAverageCost(restaurantDTO.getAverageCost());
        restaurant.setFoodType(restaurant.getFoodType());

        Restaurant saveRestaurant = restaurantService.registerRestaurant(restaurant,userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(saveRestaurant);
    }

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable Long id){
        return restaurantService.getRestaurantById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/all-restaurants")
    public ResponseEntity<List<Restaurant>> getAllRestaurants(){
        return ResponseEntity.ok(restaurantService.getAllRestaurants());
    }

}
