package ru.nsu.fit.directors.businessservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.UuidGenerator;
import ru.nsu.fit.directors.businessservice.exceptions.WrongNameFormatException;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nonnull;

@Entity
@Table(name = "business_user")
@Setter
@Getter
@Accessors(chain = true)
public class BusinessUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phoneNumber;
    private String password;
    private String email;
    private String login;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "businessUser")
    private List<Company> companies;
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "workers")
    private List<Company> workerInCompanies;
    @UuidGenerator
    private UUID token;

    @Nonnull
    public BusinessUser setFullName(String fullName) {
        String[] names = fullName.split(" ");
        if (names.length != 3) {
            throw new WrongNameFormatException();
        }
        this.middleName = names[0];
        this.firstName = names[1];
        this.lastName = names[2];
        return this;
    }
}
