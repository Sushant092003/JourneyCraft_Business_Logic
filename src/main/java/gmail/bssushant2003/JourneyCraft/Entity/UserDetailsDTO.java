package gmail.bssushant2003.JourneyCraft.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserDetailsDTO {
    private Long id;
    private String email;
    private String name;
    private String phoneNo;
    private String extraInfo;

    public UserDetailsDTO(Long id, String email, String name, String phoneNo, String extraInfo) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.phoneNo = phoneNo;
        this.extraInfo = extraInfo;
    }
}
