package gmail.bssushant2003.JourneyCraft.Controller;


import gmail.bssushant2003.JourneyCraft.Entity.LatLng;
import gmail.bssushant2003.JourneyCraft.Service.LocationExtractorService;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/location")
public class LocationExtractorController {

    private final LocationExtractorService locationExtractorService;

    public LocationExtractorController(LocationExtractorService locationExtractorService) {
        this.locationExtractorService = locationExtractorService;
    }

    @GetMapping("/extract")
    public Pair<Long, LatLng> extractLatLng(@RequestParam String url, Long id) {
        return locationExtractorService.extractLatLng(id,url);
    }
}
