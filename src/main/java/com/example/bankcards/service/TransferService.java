package com.example.bankcards.service;

import com.example.bankcards.entity.Card;

import java.math.BigDecimal;

public interface TransferService {
    void transferBetweenCards(Card srcCard, Card destCard, BigDecimal amount);
}
