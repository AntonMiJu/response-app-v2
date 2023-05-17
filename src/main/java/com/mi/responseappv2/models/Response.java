package com.mi.responseappv2.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Response {
    private String name;
    private String phone;
    private String response;
    @JsonIgnore
    private GasStation gasStation;

    public Response(String name, String phone, String response) {
        this.name = name;
        this.phone = phone;
        this.response = response;
    }
}
