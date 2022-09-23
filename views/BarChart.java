package views;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import controllers.CandidatoController;
import models.CandidatoDao;

public class BarChart {
    private ChartPanel chartPanel;

    private CandidatoController candidatoController;

    public BarChart(String chartTitle, CandidatoController candidatoCtr) {
        this.candidatoController = candidatoCtr;
        JFreeChart barChart = ChartFactory.createBarChart(
                chartTitle,
                "Category",
                "Score",
                createDataset(),
                PlotOrientation.VERTICAL,
                true, true, false);

        chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
    }

    public CategoryDataset createDataset() {

        final String votos = "Votos";

        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        CandidatoDao[] candidatos = candidatoController.getAllCandidatos();
        for (CandidatoDao candidato : candidatos) {
            dataset.addValue(candidato.getNumVotos(), candidato.getNombre(), votos);
        }

        return dataset;
    }

    public ChartPanel getChartPanel() {
        return chartPanel;
    }
}