package views;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import controllers.CandidatoController;
import models.CandidatoDao;

public class PieChart {
	private ChartPanel chartPanel;
	private CandidatoController candidatoController;

	public PieChart(CandidatoController candidatoCtr) {
		this.candidatoController = candidatoCtr;

		chartPanel = createDemoPanel();
	}

	public ChartPanel getChartPanel() {
		return chartPanel;
	}

	public PieDataset createDataset() {
		DefaultPieDataset dataset = new DefaultPieDataset();

		CandidatoDao[] candidatos = candidatoController.getAllCandidatos();
		for (CandidatoDao candidato : candidatos) {
			dataset.setValue(candidato.getNombre(), new Double(candidato.getNumVotos()));
		}

		return dataset;
	}

	private JFreeChart createChart(PieDataset dataset) {
		JFreeChart chart = ChartFactory.createPieChart(
				"Votos Percentage", // chart title
				dataset, // data
				true, // include legend
				true,
				false);

		return chart;
	}

	public ChartPanel createDemoPanel() {
		JFreeChart chart = createChart(createDataset());
		return new ChartPanel(chart);
	}

}