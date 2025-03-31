package gmail.bssushant2003.JourneyCraft.Service;

import gmail.bssushant2003.JourneyCraft.Entity.Guide;
import gmail.bssushant2003.JourneyCraft.Entity.User;
import gmail.bssushant2003.JourneyCraft.Repository.GuideRepository;
import gmail.bssushant2003.JourneyCraft.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GuideService {


    @Autowired
    private GuideRepository guideRepository;

    @Autowired
    private UserRepository userRepository;

    public Guide registerGuide(Guide guide,Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Guide Not Found"));
        guide.setUser(user);
        return guideRepository.save(guide);
    }

    public Optional<Guide> getGuideById(Long id){
        return guideRepository.findById(id);
    }

    public List<Guide> getAllGuides(){return guideRepository.findAll();}


}
