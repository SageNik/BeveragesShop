package com.beveragesShop.constants;

import java.math.BigDecimal;

/**
 * Created by Ник on 21.09.2017.
 */
public interface Constants {

     BigDecimal STANDART_MARKUP = BigDecimal.valueOf(1.1);
     BigDecimal HOLIDAY_MARKUP = BigDecimal.valueOf(1.15);
     BigDecimal EVENING_MARKUP = BigDecimal.valueOf(1.08);
     BigDecimal PLURAL_MARKUP = BigDecimal.valueOf(1.07);
     String GOODS_FILE_ADDRESS = "src/com/beveragesShop/range/defoult_goods.csv";
     Integer PURCHASE_AMOUNT = 150;
     String REPORT_FILE_ADDRESS = "src/com/beveragesShop/reports/saved/month_report.txt";
}
