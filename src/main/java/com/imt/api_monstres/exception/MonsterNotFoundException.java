package com.imt.api_monstres.exception;

public class MonsterNotFoundException extends RuntimeException {

    public MonsterNotFoundException(String monsterId) {
        super("Monstre introuvable : " + monsterId);
    }
}
