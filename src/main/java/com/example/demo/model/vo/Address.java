package com.example.demo.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {

    private String laneAddress;
    private String pinCode;
    private String district;
    private String postOffice;
    private String state;
    private String country;
}
