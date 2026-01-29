package com.example.bankcards.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CreateCardRequest {

    @NotBlank
    @Pattern(regexp = "\\d{16}")
    private String cardNumber;

    @NotNull
    private Long UserId;

    @NotNull
    private LocalDate expirationDate;
}
