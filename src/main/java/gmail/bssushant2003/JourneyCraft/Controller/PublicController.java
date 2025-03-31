package gmail.bssushant2003.JourneyCraft.Controller;

import gmail.bssushant2003.JourneyCraft.Entity.Guide;
import gmail.bssushant2003.JourneyCraft.Entity.LatLng;
import gmail.bssushant2003.JourneyCraft.Entity.Restaurant;
import gmail.bssushant2003.JourneyCraft.Repository.RestaurantRepository;
import gmail.bssushant2003.JourneyCraft.Service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/public")
public class PublicController {

    private static final Logger logger = LoggerFactory.getLogger(PublicController.class);

    List<Guide> guides = new ArrayList<>();
    List<Restaurant> restaurants = new ArrayList<>();
    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private GuideService guideService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private PublicService publicService;

    @Autowired
    private LocationExtractorService locationExtractorService;

    @GetMapping("/all-guides")
    public ResponseEntity<List<Guide>> getAllGuides(){
        return ResponseEntity.ok(guideService.getAllGuides());
    }

    @GetMapping("/all-restaurants")
    public ResponseEntity<List<Restaurant>> getAllRestaurants(){
        return ResponseEntity.ok(restaurantService.getAllRestaurants());
    }

    @PostMapping("/find-guides")
    public List<Guide> findGuidesNearby(@RequestBody List<LatLng> locations) {
        guides = publicService.fetchAllGuides();
        List<Guide> nearbyGuides = new ArrayList<>();

        for (LatLng userLocation : locations) {
            for (Guide guide : guides) {
                double distance = calculateDistance(
                        userLocation.getLat(), userLocation.getLng(),
                        guide.getLatitude(), guide.getLongitude());

                if (distance <= 10.0) { // 10 km radius
                    nearbyGuides.add(guide);
                }
            }
        }


        return nearbyGuides;
    }

    @PostMapping("/find-restaurants")
    public List<Optional<Restaurant>> findRestaurantNearby(@RequestBody List<LatLng> locations){
        restaurants = publicService.fetchAllRestaurants();
        List<Pair<Long,LatLng>> restaurantlocations = new ArrayList<>();

        for(Restaurant r : restaurants){
            restaurantlocations.add(locationExtractorService.extractLatLng(r.getId(),r.getLocationLink()));
        }

        List<Optional<Restaurant>> nearbyRestaurant = new ArrayList<>();

        for(LatLng userLocation : locations) {
            for(Pair<Long,LatLng> rest : restaurantlocations){
                double distance = calculateDistance(
                        userLocation.getLat(),userLocation.getLng(),
                        rest.getSecond().getLat(),rest.getSecond().getLng());

                if(distance <= 10.0){
                    nearbyRestaurant.add(restaurantService.getRestaurantById(rest.getFirst()));
                }
            }

        }

        return nearbyRestaurant;

    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Earth's radius in km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // Distance in km
    }
}
