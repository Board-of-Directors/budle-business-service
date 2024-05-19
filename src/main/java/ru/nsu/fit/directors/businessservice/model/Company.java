package ru.nsu.fit.directors.businessservice.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Entity
@Table(name = "company")
@Getter
@Setter
@Accessors(chain = true)
public class Company {
    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "business_user_id")
    private BusinessUser businessUser;

    @ManyToMany
    @JoinTable(name = "company_workers",
        joinColumns = @JoinColumn(name = "company_id"),
        inverseJoinColumns = @JoinColumn(name = "worker_id"))
    private List<BusinessUser> workers;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Notification> notifications;
}
