package br.com.fiap.wavemail.domain.enums;

import java.util.Random;

public enum EmailPriority {
    HIGH,
    MEDIUM,
    LOW;


    public static final Random RANDOM = new Random();

    public static EmailPriority getRandomPriority() {
        EmailPriority[] priority = values();
        return priority[RANDOM.nextInt(priority.length)];
    }
}
