package com.example.bankcards.dto.response;

import com.example.bankcards.entity.User;
import com.example.bankcards.entity.invariants.CardStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CardResponse {
    private Long id;
    private String lastFourDigits;
    private LocalDate expirationDate;
    private CardStatus status;
    private BigDecimal balance;
}
