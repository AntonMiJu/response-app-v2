package com.mi.responseappv2.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Director {
    @Id
    private Long chatId;
}