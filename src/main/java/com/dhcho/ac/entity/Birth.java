package com.dhcho.ac.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Birth {

    @Column(name = "BIRTH_DATE")
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(name = "BIRTH_TYPE")
    private BirthType type;
}
