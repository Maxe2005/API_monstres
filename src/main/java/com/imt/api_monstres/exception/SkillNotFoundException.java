package com.imt.api_monstres.exception;

public class SkillNotFoundException extends RuntimeException {

    public SkillNotFoundException(String skillId) {
        super("Skill introuvable : " + skillId);
    }
}
