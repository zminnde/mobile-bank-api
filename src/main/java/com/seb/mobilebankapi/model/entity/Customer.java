package com.seb.mobilebankapi.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Customer extends PrimaryEntity {

    private String userName;
    @JsonProperty(access = WRITE_ONLY)
    private String password;
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<Account> accounts;
}