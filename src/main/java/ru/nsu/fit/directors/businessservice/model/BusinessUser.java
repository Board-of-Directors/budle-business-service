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

import java.util.List;

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
}
