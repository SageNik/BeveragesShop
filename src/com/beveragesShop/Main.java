package com.beveragesShop;

import com.beveragesShop.beverages.AlcoholDrink;
import com.beveragesShop.beverages.Drink;
import com.beveragesShop.beverages.SoftDrink;
import com.beveragesShop.constants.Constants;

import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Main implements Constants{

    private static List<Drink> productRange;
    private static BigDecimal profit;
    private static BigDecimal purchaseExpenses;

    public static void main(String[] args) {

        productRange = initRange();

        if(productRange.isEmpty()){
            System.out.println("Sorry, there are no goods in the shop. We can`t open it.");
        }else{
            System.out.println("Hello! The beverages shop is open!");
            System.out.println("We always open from 8 am to 9 pm!");

            emulation();

            FileWriter writer = null;
            try{
                writer = new FileWriter(GOODS_FILE_ADDRESS);

                for(Drink drink: productRange){
                    String description = "";
                    if(drink instanceof AlcoholDrink ){
                        description = ((AlcoholDrink)drink).getBeverageStrength().toString();
                    }else{
                        description = ((SoftDrink)drink).getComposition();
                    }
                    writer.write("\""+ drink.getName()+ "\"; "+ drink.getPurchasePrice()+"; \""+drink.getGroup()+"\"; "+drink.getCapacity()+
                    "; \""+description+ "\"; "+drink.getStockAmount()+"\n");
                }

                writer.flush();
                writer.close();

            }catch(FileNotFoundException e){
                System.out.println("Error. File:" + GOODS_FILE_ADDRESS+ " is not found");
                e.printStackTrace();
            }catch(IOException ex){
                ex.printStackTrace();
            }finally {
                if(writer != null){
                    try{
                        writer.close();
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static List<Drink> initRange(){
       List<Drink> drinks = new ArrayList<>();
        String line = "";
        String csvSplitBy = ";+\\s?";
        BufferedReader reader = null;

        try{
            reader = new BufferedReader(new FileReader(GOODS_FILE_ADDRESS));
            while ((line = reader.readLine())!= null) {

                String[] goods = line.split(csvSplitBy);
                goods[0] = goods[0].replace("\"","");
                goods[2] = goods[2].replace("\"","");
                goods[4] = goods[4].replace("\"","");

                if (goods.length == 6 && (goods[2].equals("соки") || goods[2].equals("минеральные воды")
                        || goods[2].equals("прочие напитки"))) {
                    SoftDrink softDrink = new SoftDrink(goods[0], BigDecimal.valueOf(Double.parseDouble(goods[1])), goods[2], Float.parseFloat(goods[3]),
                            Integer.parseInt(goods[5]), goods[4]);
                    drinks.add(softDrink);
                } else if (goods.length == 6 && (goods[2].equals("пиво") || goods[2].equals("вино")
                        || goods[2].equals("ликеры") || goods[2].equals("крепкий алкоголь"))) {

                    AlcoholDrink alcoholDrink = new AlcoholDrink(goods[0], BigDecimal.valueOf(Double.parseDouble(goods[1])), goods[2], Float.parseFloat(goods[3]),
                            Integer.parseInt(goods[5]), Float.parseFloat(goods[4].replace("%","")));
                    drinks.add(alcoholDrink);
                }
            }
        }catch(FileNotFoundException e){
            System.out.println("Error. File:" + GOODS_FILE_ADDRESS+ " is not found");
            e.printStackTrace();
        }catch(IOException ex){
            ex.printStackTrace();
        }finally {
            if(reader != null){
                try{
                    reader.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
        return drinks;
    }

    private static void emulation(){

        for(int day = 1;day<31; day++){

            System.out.println("DAY: "+day);
            emulationOfSell(day);
            emulationOfPurchase();
        }

        buildMonthReport();
        System.out.println("<<END OF MONTH>>");
    }

    private static void buildMonthReport() {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);
        for(Drink tDrink : productRange){
            profit = BigDecimal.valueOf(0);
            purchaseExpenses = BigDecimal.valueOf(0);
            profit = profit.add(tDrink.getProfit());
            purchaseExpenses = purchaseExpenses.add(tDrink.getPurchaseExpenses());
        }
        FileWriter writer = null;
        try{
             writer = new FileWriter(REPORT_FILE_ADDRESS);
             writer.write("The Month Report by Beverages Shop \n");
             writer.write("\n");
             writer.write("Список товаров \n");
            writer.write("\n");
             for(Drink drink: productRange){
                 writer.write("Товар: "+ drink.getName()+ "   Объем: "+ drink.getCapacity()+ "    Продано: "+ drink.getSoldAmount().toString()
                         +"шт.    Дозакуплено: "+drink.getBoughtAmount().toString()+"шт.\n");
             }
            writer.write("\n");
            writer.write("\n");
            writer.write("\t Прибыль магазина от продаж: "+df.format(profit)+"\n");
            writer.write("\t Затраченные средства на дозакупку товара: "+df.format(purchaseExpenses)+"\n");
            writer.flush();
            writer.close();

        }catch(FileNotFoundException e){
            System.out.println("Error. File:" + REPORT_FILE_ADDRESS+ " is not found \n");
            e.printStackTrace();
        }catch(IOException ex){
            ex.printStackTrace();
        }finally {
            if(writer != null){
                try{
                    writer.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    private static void emulationOfPurchase() {
        for(Drink tempDrink : productRange){
            if(tempDrink.getStockAmount() < 10){
                tempDrink.purchase();
            }
        }
    }

    private static void emulationOfSell(int day) {
        for(int hour = 0;hour<24; hour++){

            System.out.println("HOUR: "+hour);
            if(8 <= hour && hour <= 21){
                int clientAmount = (int)(Math.random()*11);
                    for(int j = 0; j <clientAmount; j++){
                        System.out.println("NEW CLIENT");
                        sellingRandomDrinks(day, hour);
                    }
            }
        }
    }

    private static void sellingRandomDrinks(int day, int hour) {
        int goodsAmount = (int)(Math.random()*11);
        while(goodsAmount < 11){
            int randomIndex = (int)(Math.random()*(productRange.size()));
            Drink ramdomDrink = productRange.get(randomIndex);
            ramdomDrink.sell(goodsAmount, day, hour);

            goodsAmount += (int)(Math.random()*11);
        }
    }
}
