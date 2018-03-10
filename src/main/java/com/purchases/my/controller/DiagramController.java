package com.purchases.my.controller;

import com.googlecode.charts4j.*;
import com.purchases.my.db.entity.PurchaseEntity;
import com.purchases.my.db.service.CategoryService;
import com.purchases.my.db.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.googlecode.charts4j.Color.*;

@Controller
public class DiagramController {
    private PurchaseService purchaseService;

    @Autowired
    @Qualifier(value = "purchaseService")
    public void setPurchaseService(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @RequestMapping(value = "/diagram", method = RequestMethod.GET)
    public String drawPieChart(ModelMap model) {
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.set(Calendar.DAY_OF_MONTH, calendarStart.getActualMinimum(Calendar.DAY_OF_MONTH));
        Date startDate = calendarStart.getTime();
        Date endDate = new Date();
        List<PurchaseEntity> purchases = purchaseService.getPurchases(startDate, endDate);
        System.out.println("****");
        purchases.forEach(System.out::println);
        System.out.println("****");
        List<Double> prices = new ArrayList<>();
        prices.add(0.0);
        prices.addAll(purchases.stream()
                .map(purchase -> (double) purchase.getPrice()).collect(Collectors.toList()));

        System.out.println("****");
        prices.forEach(System.out::println);
        System.out.println("****");

        final Plot plot = Plots.newPlot(Data.newData(prices));
        final LineChart chart = GCharts.newLineChart(plot);
        chart.setTitle("Line chart");

//        model.addAttribute("lineGraph", chart.toString());
        return "diagram";
    }

    public String drawPieDiagram() {
        Slice s1 = Slice.newSlice(15, Color.newColor("CACACA"), "Mac", "Mac");
        Slice s2 = Slice.newSlice(50, Color.newColor("DF7417"), "Window",
                "Window");
        Slice s3 = Slice.newSlice(25, Color.newColor("951800"), "Linux",
                "Linux");
        Slice s4 = Slice.newSlice(10, Color.newColor("01A1DB"), "Others",
                "Others");

        PieChart pieChart = GCharts.newPieChart(s1, s2, s3, s4);
        pieChart.setTitle("Google Pie Chart", BLACK, 15);
        pieChart.setSize(360, 180);
        pieChart.setThreeD(true);
        return pieChart.toString();
    }

    public String drawLineGraph1() {
        // Your really great chart.
        final Plot plot = Plots.newPlot(Data.newData(50, 10.6, 20.5, 80.20, 50.50, 95.5, 92.00));
        final LineChart chart = GCharts.newLineChart(plot);
        chart.setTitle("Growth of Alibata System Inc. (Estimated Plot)");
        chart.setSize(360, 180);
        return chart.toURLString();
    }


    public String drawLineGraph() {
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.set(Calendar.DAY_OF_MONTH, calendarStart.getActualMinimum(Calendar.DAY_OF_MONTH));
        Date startDate = calendarStart.getTime();
        Date endDate = new Date();
        List<PurchaseEntity> purchases = purchaseService.getPurchases(startDate, endDate);
        System.out.println("****");
        purchases.forEach(System.out::println);
        System.out.println("****");
        List<Double> prices = new ArrayList<>();
        prices.add(0.0);
        prices.addAll(purchases.stream()
                .map(purchase -> (double) purchase.getPrice()).collect(Collectors.toList()));

        System.out.println("****");
        prices.forEach(System.out::println);
        System.out.println("****");

        final Plot plot = Plots.newPlot(Data.newData(prices));
        final LineChart chart = GCharts.newLineChart(plot);
        chart.setTitle("Line chart");
//        chart.setSize(360, 180);

        // Your really great chart.
//        final Plot plot = Plots.newPlot(Data.newData(0, 10.6, 20.5, 80.20, 50.50, 95.5, 92.00));
//        final LineChart chart = GCharts.newLineChart(plot);
//        chart.setTitle("Growth of Alibata System Inc. (Estimated Plot)");
//        chart.setSize(720, 360);
        return chart.toURLString();
    }
}
