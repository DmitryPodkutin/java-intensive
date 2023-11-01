package output;

import lombok.extern.slf4j.Slf4j;
import model.CurrencyName;
import model.CurrencyRate;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.CombinedDomainCategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static constants.Constant.RATE_GRAPH_PNG;
import static constants.Constant.decimalFormat;
import static constants.Constant.outputDateFormat;

@Slf4j
public class TelegramOutputFormatterImpl implements TelegramOutputFormatter {

    @Override
    public String formatSingleExchangeRateMessage(CurrencyName currencyName, CurrencyRate currencyRate) {
        final String message = String.format("Курс %s\n%s - %s;", currencyName.name(), outputDateFormat.format(currencyRate.getDate()),
                decimalFormat.format(currencyRate.getCurs()));
        log.info("Сформировано сообщение о курсе валюты: {}", message);
        return message;
    }

    @Override
    public String formatExchangeRateListMessage(CurrencyName currencyName, List<CurrencyRate> currencyRates) {
        final String header = String.format("Курс %s\n", currencyName.name());
        final String ratesList = currencyRates.stream()
                .map(currencyRate -> String.format("%s - %s", outputDateFormat.format(currencyRate.getDate()),
                        decimalFormat.format(currencyRate.getCurs())))
                .collect(Collectors.joining("\n"));
        log.info("Сформировано сообщение со списком курсов валют: {}", ratesList);
        return header + ratesList;
    }

    public String formatExchangeRateAsGraph(Map<CurrencyName, List<CurrencyRate>> currencyRates) {
        final CombinedDomainCategoryPlot combinedPlot = new CombinedDomainCategoryPlot();
        for (Map.Entry<CurrencyName, List<CurrencyRate>> entry : currencyRates.entrySet()) {
            CurrencyName currencyName = entry.getKey();
            List<CurrencyRate> rates = entry.getValue();
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            for (CurrencyRate rate : rates) {
                dataset.addValue(rate.getCurs(), String.format("Курс %s", currencyName), rate.getDate());
            }
            final JFreeChart subChart = createLineChartForCurrency(currencyName, dataset);
            final CategoryPlot subPlot = subChart.getCategoryPlot();
            subPlot.setDomainGridlinesVisible(true);
            subPlot.setRangeGridlinesVisible(true);
            combinedPlot.add(subPlot);
        }
        final JFreeChart combinedChart = createCombinedChart(combinedPlot);
        try {
            saveChartAsPNG(combinedChart);
            log.info("График курса валют успешно сохранен в файл: {}", RATE_GRAPH_PNG);
            return RATE_GRAPH_PNG;
        } catch (IOException e) {
            log.error("Ошибка при сохранении графика курса валют", e);
        }
        return "";
    }

    private JFreeChart createLineChartForCurrency(CurrencyName currencyName, DefaultCategoryDataset dataset) {
        return ChartFactory.createLineChart(String.format("Курс %s", currencyName), "Дата",
                String.format("Курс %s", currencyName), dataset, PlotOrientation.VERTICAL, false, true,
                false
        );
    }

    private JFreeChart createCombinedChart(CombinedDomainCategoryPlot combinedPlot) {
        return new JFreeChart("График курса валют", JFreeChart.DEFAULT_TITLE_FONT, combinedPlot, false
        );
    }

    private void saveChartAsPNG(JFreeChart chart) throws IOException {
        ChartUtils.saveChartAsPNG(new File(constants.Constant.RATE_GRAPH_PNG), chart, 800, 600);
    }

}
