package br.com.fiap.wavemail.domain.enums;
import java.util.Random;

public enum EmailType {
    PROMOTION,
    SPAM,
    WORK,
    PERSONAL;


    public static final Random RANDOM = new Random();

    public static EmailType getRandomType() {
        EmailType[] types = values();
        return types[RANDOM.nextInt(types.length)];
    }
}
