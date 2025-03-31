package gmail.bssushant2003.JourneyCraft.Controller;

import gmail.bssushant2003.JourneyCraft.Entity.User;
import gmail.bssushant2003.JourneyCraft.Entity.UserDetailsDTO;
import gmail.bssushant2003.JourneyCraft.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("all-users")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userService.getAll();
        if(users != null && !users.isEmpty()){
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/find")
    public ResponseEntity<?> findUserByEmail(@RequestParam String email){
        Optional<User> user = userService.findByEmail(email);
        return user.map(value-> new ResponseEntity<>(value,HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user){
        try{
            User savedUser = userService.saveUser(user);
            return new ResponseEntity<>(savedUser,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<UserDetailsDTO>> getUserByRole(@PathVariable User.Role role){
        return ResponseEntity.ok(userService.gerUserByRole(role));
    }

}
