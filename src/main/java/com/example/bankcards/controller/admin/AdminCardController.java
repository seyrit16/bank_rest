package com.example.bankcards.controller.admin;

import com.example.bankcards.dto.request.CreateCardRequest;
import com.example.bankcards.dto.response.CardResponse;
import com.example.bankcards.entity.Card;
import com.example.bankcards.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/card")
@RequiredArgsConstructor
public class AdminCardController {
    private final CardService cardService;

    @GetMapping("/get-all-by-user")
    public ResponseEntity<List<CardResponse>> getAllByUser(@RequestParam Long userId, @RequestParam Integer page, @RequestParam Integer size) {
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

    @PostMapping("/create-card")
    public ResponseEntity<Void> createCard(@RequestBody CreateCardRequest request){
        cardService.createCard(request);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/activate-card")
    public ResponseEntity<Void> activateCard(@RequestParam Long cardId){
        cardService.activateCard(cardId);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/block-card")
    public ResponseEntity<Void> blockCard(@RequestParam Long cardId){
        cardService.blockCard(cardId);

        return ResponseEntity.ok().build();
    }
}
