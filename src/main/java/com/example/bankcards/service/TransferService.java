package com.example.bankcards.service;

import com.example.bankcards.dto.TransferRequest;
import com.example.bankcards.entity.Card;

import java.math.BigDecimal;

public interface TransferService {
    void transferBetweenCards(Long userId, TransferRequest request);
}
