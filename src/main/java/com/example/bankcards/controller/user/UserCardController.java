package com.example.bankcards.controller.user;

import com.example.bankcards.dto.request.TransferRequest;
import com.example.bankcards.dto.response.CardResponse;
import com.example.bankcards.entity.Card;
import com.example.bankcards.security.components.BankUserDetails;
import com.example.bankcards.service.CardService;
import com.example.bankcards.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user/card")
@RequiredArgsConstructor
public class UserCardController {
    private final CardService cardService;
    private final TransferService transferService;

    @GetMapping("/all")
    public ResponseEntity<List<CardResponse>> getAllCards(@RequestParam Long userId, @RequestParam int page,
                                                          @RequestParam int size) {
        List<Card> cards = cardService.getCardsForUser(userId, page, size);
        List<CardResponse> cardResponses = cards.stream().map(card
                -> new CardResponse(
                card.getId(),
                card.getLastFourDigits(),
                card.getExpirationDate(),
                card.getStatus(),
                card.getBalance()
        )).toList();
        return ResponseEntity.ok(cardResponses);
    }

    @GetMapping("/get-by-id")
    public ResponseEntity<CardResponse> getCardById(@RequestParam Long cardId) {
        Card card = cardService.getCardById(cardId);
        CardResponse cardResponse = CardResponse.builder()
                .id(card.getId())
                .lastFourDigits(card.getLastFourDigits())
                .expirationDate(card.getExpirationDate())
                .status(card.getStatus())
                .balance(card.getBalance())
                .build();
        return ResponseEntity.ok(cardResponse);
    }

    @PostMapping("/transfer-balance")
    public ResponseEntity<Void> transferBalance(@RequestBody TransferRequest transferRequest) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        BankUserDetails user = (BankUserDetails) authentication.getPrincipal();
        Long userId = user.getId();
        transferService.transferBetweenCards(userId,transferRequest);

        return ResponseEntity.ok().build();
    }
}
