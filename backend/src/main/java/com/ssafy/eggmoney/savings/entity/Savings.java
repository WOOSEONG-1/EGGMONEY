package com.ssafy.eggmoney.savings.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "savings")
@NoArgsConstructor(access = PROTECTED)
public class Savings {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
}