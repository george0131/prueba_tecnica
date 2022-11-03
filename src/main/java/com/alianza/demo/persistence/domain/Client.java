package com.alianza.demo.persistence.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Client implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "shared_key")
    String sharedKey;
    @Column(name = "business_id")
    String businessId;
    String email;
    Long phone;
    @Column(name = "data_added")
    LocalDate dataAdded;
}
