package com.seb.mobilebankapi.model.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class PrimaryEntity {
    @Id
    @GeneratedValue
    private Long id;
}