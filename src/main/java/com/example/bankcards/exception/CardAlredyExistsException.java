package com.example.bankcards.exception;

public class CardAlredyExistsException extends RuntimeException {

    public CardAlredyExistsException(){
        super("Card with this number already exists");
    }

    public CardAlredyExistsException(String message) {
        super(message);
    }
}
