package com.purchases.my.controller;

import com.googlecode.charts4j.*;
import com.purchases.my.db.entity.CategoryEntity;
import com.purchases.my.db.entity.PurchaseEntity;
import com.purchases.my.db.service.CategoryService;
import com.purchases.my.db.service.IncomeService;
import com.purchases.my.db.service.PurchaseService;

import com.purchases.my.editors.CategoryEditor;
import com.purchases.my.utils.ExcelReader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.googlecode.charts4j.Color.*;


@Controller
public class PurchaseController {
    private static Log logger = LogFactory.getLog(PurchaseController.class);

    private PurchaseService purchaseService;
    private CategoryService categoryService;
    private IncomeService incomeService;

    @Autowired
    @Qualifier(value = "purchaseService")
    public void setPurchaseService(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @Autowired
    @Qualifier(value = "categoryService")
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Autowired
    @Qualifier(value = "incomeService")
    public void setIncomeService(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @RequestMapping(value = "/purchases-on-day", method = RequestMethod.GET)
    public String listPurchases(@RequestParam(value = "date") Date date, Model model) {
        model.addAttribute("listPurchasesOnDay", this.purchaseService.getPurchases(date));
        return "diagram";
    }

    @ModelAttribute(value = "categoryList")
    public List<CategoryEntity> getCategories() {
        return categoryService.getAllCategories();
    }

    @RequestMapping(value = "/purchases-today", method = RequestMethod.GET)
    public List<PurchaseEntity> listPurchases() {
        return this.purchaseService.getPurchases(new Date());
    }

    @RequestMapping(value = "/purchase/add-purchase", method = RequestMethod.POST)
    public String addPurchase(@ModelAttribute("purchase") PurchaseEntity purchaseEntity,
                              BindingResult result, final RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            result.getModel().forEach((key, value) -> logger.error(key + " : " + value));
            return "purchases";
        }
        if (purchaseEntity.getId() == 0) {
            purchaseEntity.setDate(new Date());
            this.purchaseService.addPurchase(purchaseEntity);
            logger.info("New purchase added");
            redirectAttributes.addFlashAttribute("successMSG", "New purchase added");
        } else {
            this.purchaseService.updatePurchase(purchaseEntity);
            logger.info("Purchase updated");
            redirectAttributes.addFlashAttribute("successMSG", "Purchase updated");
        }
        return "redirect:/purchases";
    }

    @RequestMapping(value = "/edit-purchase/{id}")
    public String editPurchase(@PathVariable("id") int id, Model model) {
        model.addAttribute("purchase", this.purchaseService.getPurchase(id));
        logger.info("Find purchase: " + this.purchaseService.getPurchase(id));
        List<PurchaseEntity> purchases = this.purchaseService.getPurchases(new Date());
        model.addAttribute("listPurchases", purchases);
        List<CategoryEntity> categoryList = categoryService.getAllCategories();
        model.addAttribute("categoryList", categoryList);
        return "/purchases";
    }

    @RequestMapping(value = "remove-purchase/{id}")
    public String removePurchase(@PathVariable("id") int id, final RedirectAttributes redirectAttributes) {
        this.purchaseService.removePurchase(id);
        redirectAttributes.addFlashAttribute("successMSG", "Purchase with id=" + id + " successfully removed");
        return "redirect:/purchases";
    }


    @RequestMapping(value = {"/purchases", "/purchases/search-purchase-between"}, method = RequestMethod.GET)
    public String purchases(@RequestParam(value = "startDate", required = false) String start,
                            @RequestParam(value = "endDate", required = false) String end,
                            Model model) {
        model.addAttribute("category", new CategoryEntity());
        model.addAttribute("purchase", new PurchaseEntity());

        List<PurchaseEntity> purchases = this.purchaseService.getPurchases(new Date());
        DoubleSummaryStatistics statistics = purchases.stream().collect(Collectors.summarizingDouble(PurchaseEntity::getPrice));

        Calendar calendarStart = Calendar.getInstance();
        calendarStart.set(Calendar.DAY_OF_MONTH, calendarStart.getActualMinimum(Calendar.DAY_OF_MONTH));
        Date startDate = calendarStart.getTime();
        Date endDate = new Date();
        long allIncome = this.incomeService.getFullIncome();
        double allPurchase = this.purchaseService.getFullPurchasePrice();
        double allMoneyDiff = allIncome - allPurchase;
        long monthIncome = this.incomeService.getIncomeQuantity(startDate, endDate);
        double montPurchase = this.purchaseService.getPurchasePrice(startDate, endDate);
        double monthMoneyDiff = monthIncome - montPurchase;

        List<CategoryEntity> categoryList = this.categoryService.getAllCategories();

        model.addAttribute("categoryList", categoryList);
        model.addAttribute("listPurchases", purchases);
        model.addAttribute("maxPurchase", statistics.getMax());
        model.addAttribute("minPurchase", statistics.getMin());
        model.addAttribute("averagePurchase", statistics.getAverage());
        model.addAttribute("allPurchases", statistics.getSum());
        model.addAttribute("lineGraph", drawLineGraph());
        model.addAttribute("categoryGraph", diagramByCategory());
        model.addAttribute("allIncome", allIncome);
        model.addAttribute("allPurchase", allPurchase);
        model.addAttribute("allMoneyDiff", allMoneyDiff);
        model.addAttribute("monthIncome", monthIncome);
        model.addAttribute("montPurchase", montPurchase);
        model.addAttribute("monthMoneyDiff", monthMoneyDiff);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyy");
        if (start != null || end != null) {
            try {
                Date startDateSearch = dateFormat.parse(start);
                Date endDateSearch = dateFormat.parse(end);
                model.addAttribute("purchase", new PurchaseEntity());
                model.addAttribute("startDate", start);
                model.addAttribute("endDate", end);
                model.addAttribute("listPurchasesBetween", this.purchaseService.getPurchases(startDateSearch, endDateSearch));
            } catch (ParseException e) {
                model.addAttribute("dataParseError", "Not correct date: " + start + " - " + end);
            }
        }
        return "purchases";
    }

    @RequestMapping(value = "/myDiagram", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<DrawData> getJsons() {
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.set(Calendar.DAY_OF_MONTH, calendarStart.getActualMinimum(Calendar.DAY_OF_MONTH));
        Date startDate = calendarStart.getTime();
        Date endDate = new Date();

        Calendar start = Calendar.getInstance();
        start.setTime(startDate);
        Calendar end = Calendar.getInstance();
        end.setTime(endDate);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
        Map<String, Double> map = new HashMap<>();
        List<DrawData> drawDataList = new ArrayList<>();
        while (start.before(end)) {
            Date currentIterationDate = start.getTime();
            drawDataList.add(new DrawData(dateFormat.format(currentIterationDate), purchaseService.getPurchasePriceByDay(currentIterationDate)));
            map.put(dateFormat.format(currentIterationDate), purchaseService.getPurchasePriceByDay(currentIterationDate));
            start.add(Calendar.DATE, 1);
        }
        return drawDataList;
    }

    public class DrawData {
        private String date;
        private double price;

        public DrawData(String date, double price) {
            this.date = date;
            this.price = price;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        @Override
        public String toString() {
            return "DrawData{" +
                    "date='" + date + '\'' +
                    ", price=" + price +
                    '}';
        }
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(CategoryEntity.class, new CategoryEditor(categoryService));
        binder.registerCustomEditor(Date.class,
                new CustomDateEditor(new SimpleDateFormat("dd.MM.yyyy"), true, 10));
    }

    private String drawLineGraph() {
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.set(Calendar.DAY_OF_MONTH, calendarStart.getActualMinimum(Calendar.DAY_OF_MONTH));
        Date startDate = calendarStart.getTime();
        Date endDate = new Date();

        Calendar start = Calendar.getInstance();
        start.setTime(startDate);
        Calendar end = Calendar.getInstance();
        end.setTime(endDate);

        List<String> dates = new ArrayList<>();
        List<Double> prices = new ArrayList<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd");

        while (start.before(end)) {
            Date currentIterationDate = start.getTime();
            prices.add(purchaseService.getPurchasePriceByDay(currentIterationDate));
            dates.add(dateFormat.format(currentIterationDate));
            start.add(Calendar.DATE, 1);
        }

        double maxValue = prices.stream().max(Double::compareTo).orElse(0.0) + 10;

        Line line1 = Plots.newLine(DataUtil.scaleWithinRange(0, maxValue, prices), BLUE, "Purchase (UAH)");

        final LineChart chart = GCharts.newLineChart(line1);
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd.MM.yyyy");
        chart.setTitle("Purchases for period " + dateFormat2.format(startDate) + " - " + dateFormat2.format(endDate));

        chart.setSize(400, 300);
        AxisLabels yAxis = AxisLabelsFactory.newNumericRangeAxisLabels(0, maxValue);
        AxisLabels yAxis2 = AxisLabelsFactory.newAxisLabels("Costs", 50.0);
        AxisLabels xAxis = AxisLabelsFactory.newAxisLabels(dates);
        AxisLabels xAxis2 = AxisLabelsFactory.newAxisLabels("Day", 50.0);
        chart.addXAxisLabels(xAxis);
        chart.addXAxisLabels(xAxis2);
        chart.addYAxisLabels(yAxis);
        chart.addYAxisLabels(yAxis2);
        return chart.toURLString();
    }

    private String diagramByCategory() {
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.set(Calendar.DAY_OF_MONTH, calendarStart.getActualMinimum(Calendar.DAY_OF_MONTH));
        Date startDate = calendarStart.getTime();
        Date endDate = new Date();

        Calendar start = Calendar.getInstance();
        start.setTime(startDate);
        Calendar end = Calendar.getInstance();
        end.setTime(endDate);

        List<PurchaseEntity> purchases = purchaseService.getPurchases(startDate, endDate);
        Map<String, Double> map = new HashMap<>();
        for (PurchaseEntity purchase : purchases) {
            String category = purchase.getCategoryByCategoryId().getName();
            if (map.containsKey(category)) {
                double price = map.get(category) + purchase.getPrice();
                map.put(category, price);
            } else {
                map.put(category, purchase.getPrice());
            }
        }

        List<String> categories = new ArrayList<>(map.keySet());
        List<Double> prices = map.values().stream().map(l -> (double) l).collect(Collectors.toList());
        double allPrice = prices.stream().mapToDouble(l -> l).sum();

        List<Color> colors = new ArrayList<>();
        colors.add(Color.ALICEBLUE);
        colors.add(Color.BLUE);
        colors.add(Color.BLUEVIOLET);
        colors.add(Color.CADETBLUE);
        colors.add(Color.CORNFLOWERBLUE);
        colors.add(Color.DARKBLUE);
        colors.add(Color.DARKSLATEBLUE);
        colors.add(Color.DEEPSKYBLUE);
        colors.add(Color.DODGERBLUE);

        List<Slice> slices = new ArrayList<>();
        for (int i = 0; i < categories.size(); i++) {
            Slice slice = Slice.newSlice((int) ((prices.get(i) * 100) / allPrice), colors.get(i), categories.get(i),
                    categories.get(i) + " - " + prices.get(i));
            slices.add(slice);
        }

        PieChart pieChart = GCharts.newPieChart(slices);
        pieChart.setTitle("By category", BLACK, 15);
        pieChart.setSize(400, 300);
        pieChart.setLegendPosition(LegendPosition.BOTTOM);
        return pieChart.toURLString();
    }

    @RequestMapping(value = "/synchronize-from-file", method = RequestMethod.GET)
    public String synchronizeFromFile(final RedirectAttributes redirectAttributes) {
        logger.info("Start synchronizing from excel file");
        List<ExcelReader.Purchase> purchases = null;
        ExcelReader excelReader = new ExcelReader();
        try {
            purchases = excelReader.getPurchasesFromFile();
        } catch (IOException e) {
            logger.error("IOException while read file =(");
            redirectAttributes.addFlashAttribute("ioException" , "Occur error while read file!");
            return "redirect:/purchases";
        }

        Set<String> categories = new HashSet<>();
        logger.info("Search categories");
        if (null != purchases) {
            categories = purchases.stream().map(ExcelReader.Purchase::getCategory).collect(Collectors.toSet());
        }

        categories.forEach(category -> {
            if (null == categoryService.getCategory(category)) {
                CategoryEntity categoryEntity = new CategoryEntity();
                categoryEntity.setName(category);
                logger.info("Add new category: " + category);
                categoryService.addCategory(categoryEntity);
            }
        });

        purchases.forEach(purchase -> {
            PurchaseEntity purchaseEntity = new PurchaseEntity();
            purchaseEntity.setPrice(purchase.getPrice());
            purchaseEntity.setDate(purchase.getDate());
            if (!purchase.getComment().isEmpty()) {
                purchaseEntity.setComment(purchase.getComment());
            }
            purchaseEntity.setCategoryByCategoryId(categoryService.getCategory(purchase.getCategory()));
            purchaseService.addPurchase(purchaseEntity);
        });
        redirectAttributes.addFlashAttribute("newPurchasesCount", "Added " + purchases.size() + " new purchases!");

        return "redirect:/purchases";
    }
}
