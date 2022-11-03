package com.alianza.demo.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientQuery {

    int page;
    int size;
    String sharedKey;
    String name;
    Long phone;
    String email;
    LocalDate startDate;
    LocalDate endDate;

}
