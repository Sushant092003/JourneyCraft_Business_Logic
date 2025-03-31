package gmail.bssushant2003.JourneyCraft.Controller;

import gmail.bssushant2003.JourneyCraft.Entity.Guide;
import gmail.bssushant2003.JourneyCraft.Entity.GuideDTO;
import gmail.bssushant2003.JourneyCraft.Entity.User;
import gmail.bssushant2003.JourneyCraft.Repository.UserRepository;
import gmail.bssushant2003.JourneyCraft.Service.GuideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/guides")
public class GuideController {

    @Autowired
    private GuideService guideService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register/guide/{userId}")
    public ResponseEntity<Guide> registerGuide(@RequestBody GuideDTO guideDTO, @PathVariable Long userId){
        User user = userRepository.findById(userId).orElse(null);
        if(user == null){
            throw new RuntimeException("User with UserID " + userId + "not found");
        }


        Guide guide = new Guide();
        guide.setUser(user);
        guide.setName(guideDTO.getName());
        guide.setExperience(guideDTO.getExperience());
        guide.setLanguage(guideDTO.getLanguage());
        guide.setBio(guideDTO.getBio());
        guide.setLatitude(guideDTO.getLatitude());
        guide.setLongitude(guideDTO.getLongitude());
        guide.setLicenseNumber(guideDTO.getLicenseNumber());
        guide.setIsAvailable(guideDTO.getIsAvailable());
        guide.setPhoneNo(guideDTO.getPhoneNo());

        Guide savedGuide = guideService.registerGuide(guide,userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedGuide);
    }

    @GetMapping("/guide/{id}")
    public ResponseEntity<Guide> getGuideById(@PathVariable Long id){
        return guideService.getGuideById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/all-guides")
    public ResponseEntity<List<Guide>> getAllGuides(){
        return ResponseEntity.ok(guideService.getAllGuides());
    }

}
