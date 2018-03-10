package com.purchases.my.utils;


import com.purchases.my.db.entity.CategoryEntity;
import com.purchases.my.db.entity.PurchaseEntity;
import com.purchases.my.db.service.CategoryService;
import com.purchases.my.db.service.PurchaseService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class ExcelReader {
    private Log log = LogFactory.getLog(ExcelReader.class);

    private static final String DEFAULT_FILE_PATH = "/home/viktor/REPOSITORY/Costs_repo/src/main/resources/test.xls";
    private static List<Purchase> purchases;

//    public void addPurchases(PurchaseService purchaseService, CategoryService categoryService) {
//        try {
//            purchases = getPurchasesFromFile();
//        } catch (IOException e) {
//            log.error("IOException while read file =(");
//        }
//
//        Set<String> categories = new HashSet<>();
//        log.info("Create");
//        if (null != purchases) {
//            categories = purchases.stream().map(Purchase::getCategory).collect(Collectors.toSet());
//        }
//
//        categories.forEach(category -> {
//            if (null == categoryService.getCategory(category)) {
//                CategoryEntity categoryEntity = new CategoryEntity();
//                categoryEntity.setName(category);
//                log.info("Add new category: " + category);
//                categoryService.addCategory(categoryEntity);
//            }
//        });
//
//        purchases.forEach(purchase -> {
//            PurchaseEntity purchaseEntity = new PurchaseEntity();
//            purchaseEntity.setPrice(purchase.getPrice());
//            purchaseEntity.setDate(purchase.getDate());
//            if (!purchase.getComment().isEmpty()) {
//                purchaseEntity.setComment(purchase.getComment());
//            }
//            purchaseEntity.setCategoryByCategoryId(categoryService.getCategory(purchase.getCategory()));
//            purchaseService.addPurchase(purchaseEntity);
//        });
//    }

    public List<Purchase> getPurchasesFromFile() throws IOException {
        log.info("Read file");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");

        // Finds the workbook instance for XLSX file

        InputStream ExcelFileToRead = new FileInputStream(DEFAULT_FILE_PATH);
        HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow row;
        HSSFCell cell;

        Iterator rows = sheet.rowIterator();
        List<Purchase> purchases = new ArrayList<>();
        log.info("Read rows and cells");
        while (rows.hasNext()) {
            row = (HSSFRow) rows.next();
            Iterator cells = row.cellIterator();
            Purchase purchase = new Purchase();

            while (cells.hasNext()) {

                Date date;

                cell = (HSSFCell) cells.next();
                switch (cell.getColumnIndex()) {
                    case 0: {
                        //ignore index
                        break;
                    }
                    case 1: {
                        try {
                            date = dateFormat.parse(cell.toString());
                            purchase.setDate(date);
                        } catch (ParseException e) {
                            System.out.println("unparsable date [" + cell.toString() + "]");
                        }
                        break;
                    }
                    case 2: {
                        //ignore time
                        /*try {
                         *//*System.out.println(timeFormat.parse(cell.toString()));*//*
                        } catch (ParseException e) {
                            System.out.println("unparsable time [" + cell.toString() +"]");
                        }*/
                        break;
                    }
                    case 3: {
                        //ignore money category
                        break;
                    }
                    case 4: {
                        purchase.setCategory(cell.toString());
                        break;
                    }
                    case 5: {
                        //ignore add money
                        break;
                    }
                    case 6: {
                        try {
                            purchase.setPrice(Double.parseDouble(cell.toString()));
                        } catch (NumberFormatException e) {
                            System.out.println("unparsable price [" + cell.toString() + "]");
                        }
                        break;
                    }
                    case 7: {
                        purchase.setComment(cell.toString());
                        break;
                    }
                }

                /*System.out.println("column index: " + cell.getColumnIndex() + " : " + cell);*/
            }
            if (null != purchase.getDate())
                purchases.add(purchase);

            /*System.out.println();*/
        }
        return purchases;
    }

    public class Purchase {
        private Date date;
        private String category;
        private double price;
        private String comment;

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        @Override
        public String toString() {
            return "Purchase{" +
                    "date=" + date +
                    ", category='" + category + '\'' +
                    ", price=" + price +
                    ", comment='" + comment + '\'' +
                    '}';
        }
    }
}