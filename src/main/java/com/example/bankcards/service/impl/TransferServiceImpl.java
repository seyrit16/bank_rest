package com.example.bankcards.service.impl;

import com.example.bankcards.dto.request.TransferRequest;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.invariants.CardStatus;
import com.example.bankcards.exception.CardBlockedException;
import com.example.bankcards.exception.CardNotFoundException;
import com.example.bankcards.exception.InsufficientBalanceException;
import com.example.bankcards.exception.InvalidTransferException;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.service.TransferService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class TransferServiceImpl implements TransferService {

    CardRepository cardRepository;

    @Override
    public void transferBetweenCards(Long userId, TransferRequest request) {
        if (request.getFromCardId().equals(request.getToCardId())) {
            throw new InvalidTransferException("Cannot transfer to the same card");
        }

        Card from = cardRepository.findByIdLockPessimisticWrite(request.getFromCardId())
                .orElseThrow(() -> new CardNotFoundException("Card not found"));

        Card to = cardRepository.findByIdLockPessimisticWrite(request.getToCardId())
                .orElseThrow(() -> new CardNotFoundException("Card not found"));

        //проверка владельца
        if (!from.getId().equals(userId)
                || !to.getId().equals(userId)) {
            throw new AccessDeniedException("Cards must belong to the same user");
        }

        //проверка статуса
        if (from.getStatus() != CardStatus.ACTIVE
                || to.getStatus() != CardStatus.ACTIVE) {
            throw new CardBlockedException("Card is blocked");
        }

        //проверка баланса
        if(from.getBalance().compareTo(request.getAmount()) < 0) {
            throw new InsufficientBalanceException("Insufficient balance");
        }

        //перевод
        from.setBalance(from.getBalance().subtract(request.getAmount()));
        to.setBalance(to.getBalance().add(request.getAmount()));

        cardRepository.save(from);
        cardRepository.save(to);
    }
}
