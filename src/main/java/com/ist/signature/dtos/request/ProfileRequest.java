package com.ist.signature.dtos.request;

import lombok.Data;

@Data
public class ProfileRequest {
    private String phoneNumber;
    private String firstName;
    private String lastName;
}
