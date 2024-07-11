package org.example;

public class ActiveCards {
    String name;
    String cardDefenceAttack;
    String duration;
    String playerDamage;
    String cost;
    String upgradeCost;
    String upgradeLevel;
    public ActiveCards(String name, String cardDefenceAttack, String duration, String playerDamage, String cost, String upgradeCost, String upgradeLevel) {
        this.name = name;
        this.cardDefenceAttack = cardDefenceAttack;
        this.duration = duration;
        this.playerDamage = playerDamage;
        this.cost = cost;
        this.upgradeCost = upgradeCost;
        this.upgradeLevel = upgradeLevel;
    }
}
