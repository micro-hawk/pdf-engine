package com.example.demo.model.dto;

import com.example.demo.model.vo.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MasterHospitalCommonDto {

    private String hospitalName;
    private String hospitalCode;
    private String hospitalLogo;
    private String adminMail;
    private Long adminUserId;
    private Address address;
    private Boolean isActive;
    private String hospitalPanCard;
    private String hospitalGstNumber;
    private String hospitalMobileNumber;
}
