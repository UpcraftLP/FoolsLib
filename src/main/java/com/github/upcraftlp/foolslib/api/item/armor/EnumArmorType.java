package com.github.upcraftlp.foolslib.api.item.armor;

public enum EnumArmorType {
    HELMET(3),
    CHESTPLATE(2),
    LEGGINGS(1),
    BOOTS(0);

    private final int armorSlot;

    EnumArmorType(int armorSlot) {
        this.armorSlot = armorSlot;
    }

    public int getArmorSlot() {
        return armorSlot;
    }
}
