package ru.nsu.fit.directors.businessservice.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Notification> notifications;
}
