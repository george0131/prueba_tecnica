package com.alianza.demo.rest.domain;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ClientDTO {

    Long id;
    String sharedKey;
    String businessId;
    String email;
    Long phone;
    LocalDate dataAdded;
}
