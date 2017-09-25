package com.beveragesShop.beverages;

import java.math.BigDecimal;

/**
 * Created by Ник on 21.09.2017.
 */
public class AlcoholDrink extends Drink {

    private Float beverageStrength;

    public AlcoholDrink(String name, BigDecimal purchasePrice, String group, Float capacity, Integer stockAmount, Float beverageStrength) {
        super(name, purchasePrice, group, capacity, stockAmount);
        this.beverageStrength = beverageStrength;
    }

    public Float getBeverageStrength() {
        return beverageStrength;
    }

    public void setBeverageStrength(Float beverageStrength) {
        this.beverageStrength = beverageStrength;
    }
}
