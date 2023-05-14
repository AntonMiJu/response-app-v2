package com.mi.responseappv2.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class GasStation {
    @Id
    private String id;
    private String address;
    private Long chatId;
}
