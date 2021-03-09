package com.dhcho.ac.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Embeddable
@Getter
@RequiredArgsConstructor
public class Birth {

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private BirthType type;
}
