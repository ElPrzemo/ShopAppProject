package pl.sda.todoapp.model.enitiies;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;
import pl.sda.todoapp.model.enitiies.Address;
import pl.sda.todoapp.model.enums.AdminOrUser;
import pl.sda.todoapp.util.PasswordManager;

import java.util.UUID;

@Entity

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserAccount {

    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    private String email;

    @Column(name = "password_hash")
    private String passwordHash;

    @Embedded
    private Address address;

    private int phoneNumber;

    @Column(name = "avatar_path")
    private String avatarPath;

    @Enumerated(EnumType.STRING)
    private AdminOrUser adminOrUser;

    @Autowired
    PasswordManager passwordManager;

    public void setPassword(String password) {
        this.passwordHash = this.passwordManager.hashPassword(password);
    }

    public void verifyPassword(String password) {
        this.passwordManager.verifyPassword(password, passwordHash);
    }

    public void resetPassword() {
        String newPassword = UUID.randomUUID().toString();
        this.passwordHash = this.passwordManager.hashPassword(newPassword);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}