package ef.store.web.domains;

import ef.store.web.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String id;

    private String userName;

    private String password;

    private String email;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String address;

    private Status status;

    private List<Role> listRole;

    private List<Token> listToken;

}
