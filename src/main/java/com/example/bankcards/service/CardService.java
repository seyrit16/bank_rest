package com.example.bankcards.service;

import com.example.bankcards.dto.request.CreateCardRequest;
import com.example.bankcards.entity.Card;

import java.util.List;

public interface CardService {
    Card createCard(CreateCardRequest request);
    void blockCard(Long cardId);
    void activateCard(Long cardId);
    Card getCardById(Long id);
    List<Card> getCardsForUser(Long id,int page, int size);
}
