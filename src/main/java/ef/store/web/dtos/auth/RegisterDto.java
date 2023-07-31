package ef.store.web.dtos.auth;

import ef.store.web.domains.Role;
import ef.store.web.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {
    private String userName;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    private List<Role> listRole;
    private Status status;
    private Timestamp createdAt;
    private Integer createdBy;
    private Timestamp updateAt;
    private Integer updateBy;
}