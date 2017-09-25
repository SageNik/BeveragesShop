package com.beveragesShop.beverages;

import com.beveragesShop.constants.Constants;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by Ник on 21.09.2017.
 */
public abstract class Drink implements Constants {

    private String name;
    private BigDecimal purchasePrice;
    private String group;
    private Float capacity;
    private Integer stockAmount;
    private Integer soldAmount;
    private Integer boughtAmount;
    private BigDecimal profit;
    private BigDecimal purchaseExpenses;
    private DecimalFormat df;

    public Drink(String name, BigDecimal purchasePrice, String group, Float capacity, Integer stockAmount) {
        this.name = name;
        this.purchasePrice = purchasePrice;
        this.group = group;
        this.capacity = capacity;
        this.stockAmount = stockAmount;
        this.soldAmount = 0;
        this.boughtAmount = 0;
        this.profit = BigDecimal.valueOf(0);
        this.purchaseExpenses = BigDecimal.valueOf(0);
    }

    public void sell(int amount, int day, int hour) {

        if (stockAmount == 0) {
            System.out.println("Sorry, we have not any " + name + " in the store");
        } else {
            if (stockAmount > 0 && stockAmount < amount) {
                amount = stockAmount;
            }
            BigDecimal markup = setMarkup(day, hour);
            setChangesBySell(amount, markup);
        }
    }

    private void setChangesBySell(int amount, BigDecimal markup) {
        stockAmount -= amount;
        soldAmount += amount;

        for (int i = amount; i > 0; i--) {

            BigDecimal price = BigDecimal.valueOf(0);
            price = price.setScale(2, BigDecimal.ROUND_HALF_UP);

            price = printSoldToConsole(markup, i);
            profit = profit.add(price.subtract(purchasePrice));
        }
    }

    private BigDecimal printSoldToConsole(BigDecimal markup, int i) {

        df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);

        BigDecimal price;
        if (i > 2) {
            price = purchasePrice.multiply(PLURAL_MARKUP);
            System.out.println("Sold :" + name + " by price:" + df.format(price) + " with markup: " + (PLURAL_MARKUP.multiply(BigDecimal.valueOf(100))).subtract((BigDecimal.valueOf(100))) + " %");
        } else {
            price = purchasePrice.multiply(markup);
            System.out.println("Sold :" + name + " by price:" + df.format(price) + " with markup: " + (markup.multiply(BigDecimal.valueOf(100))).subtract((BigDecimal.valueOf(100))) + " %");
        }
        return price;
    }

    public void purchase() {

        stockAmount += PURCHASE_AMOUNT;
        boughtAmount += PURCHASE_AMOUNT;
        purchaseExpenses = purchaseExpenses.add(purchasePrice.multiply(BigDecimal.valueOf(PURCHASE_AMOUNT.longValue())));
        System.out.println("Purchased :" + name + " by price:" + purchasePrice + " and amount: " + PURCHASE_AMOUNT + " pieces");
    }

    private BigDecimal setMarkup(int day, int hour) {

        if (hour == 18 || hour == 19) {
            return EVENING_MARKUP;
        } else {
            if (day % 6 == 0 || day % 7 == 0) {
                return HOLIDAY_MARKUP;
            } else {
                return STANDART_MARKUP;
            }
        }
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public String getGroup() {
        return group;
    }

    public Float getCapacity() {
        return capacity;
    }

    public Integer getStockAmount() {
        return stockAmount;
    }

    public Integer getSoldAmount() {
        return soldAmount;
    }

    public Integer getBoughtAmount() {
        return boughtAmount;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public BigDecimal getPurchaseExpenses() {
        return purchaseExpenses;
    }
}