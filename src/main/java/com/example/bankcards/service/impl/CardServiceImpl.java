package com.example.bankcards.service.impl;

import com.example.bankcards.dto.request.CreateCardRequest;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import com.example.bankcards.entity.invariants.CardStatus;
import com.example.bankcards.exception.CardAlredyExistsException;
import com.example.bankcards.exception.NotFoundException;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.service.CardService;
import com.example.bankcards.service.UserService;
import com.example.bankcards.util.CardEncryptionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final UserService userService;

    @Override
    @Transactional
    public Card createCard(CreateCardRequest request) {
        String encryptedNumber = CardEncryptionUtil.encrypt(request.getCardNumber());
        if (cardRepository.existsByCardNumberEncrypted(encryptedNumber)) {
            throw new CardAlredyExistsException();
        }

        User user = userService.getUserById(request.getUserId());
        String lastFourNumber = request.getCardNumber().substring(request.getCardNumber().length(), 4);

        Card card = Card.builder()
                .cardNumberEncrypted(encryptedNumber)
                .lastFourDigits(lastFourNumber)
                .user(user)
                .expirationDate(request.getExpirationDate())
                .status(CardStatus.NOT_ACTIVE)
                .balance(BigDecimal.ZERO)
                .build();

        return cardRepository.save(card);
    }

    @Override
    @Transactional
    public void blockCard(Long cardId) {
        Card card = getCardById(cardId);
        card.setStatus(CardStatus.BLOCKED);
        cardRepository.save(card);
    }

    @Override
    @Transactional
    public void activateCard(Long cardId) {
        Card card = getCardById(cardId);
        card.setStatus(CardStatus.ACTIVE);
        cardRepository.save(card);
    }

    @Override
    @Transactional(readOnly = true)
    public Card getCardById(Long id) {
        return cardRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Card not found"));
    }

    @Override
    public List<Card> getCardsForUser(Long id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return cardRepository.findByUserId(id, pageable);
    }
}
