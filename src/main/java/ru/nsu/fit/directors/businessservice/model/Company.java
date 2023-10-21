package ru.nsu.fit.directors.businessservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

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
}
