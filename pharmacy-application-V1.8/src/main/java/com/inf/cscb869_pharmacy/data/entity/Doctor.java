package com.inf.cscb869_pharmacy.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Doctor extends BaseEntity {

    private String name;

    @OneToMany(mappedBy = "doctor")
    @JsonIgnore
    private Set<Recipe> recipes;
}
