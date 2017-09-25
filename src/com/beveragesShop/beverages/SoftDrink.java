package com.beveragesShop.beverages;

import java.math.BigDecimal;

/**
 * Created by Ник on 21.09.2017.
 */
public class SoftDrink extends Drink {

    private String composition;

    public SoftDrink(String name, BigDecimal purchasePrice, String group, Float capacity, Integer stockAmount, String composition) {
        super(name, purchasePrice, group, capacity, stockAmount);
        this.composition = composition;
    }

    public String getComposition() {
        return composition;
    }

    public void setComposition(String composition) {
        this.composition = composition;
    }
}
