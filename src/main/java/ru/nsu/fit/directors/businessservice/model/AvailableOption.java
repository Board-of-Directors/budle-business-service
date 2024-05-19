package ru.nsu.fit.directors.businessservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "available_option")
@Getter
@Setter
@Accessors(chain = true)
public class AvailableOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
    @ManyToOne
    @JoinColumn(name = "business_user_id")
    private BusinessUser businessUser;
    @Enumerated(value = EnumType.STRING)
    private Option option;
}
