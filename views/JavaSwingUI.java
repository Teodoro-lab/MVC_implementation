package views;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.jfree.chart.ChartPanel;

import abominableFramework.View;
import controllers.CandidatoController;
import models.CandidatoDao;

public class JavaSwingUI extends JFrame implements View {
    private CandidatoController candidatoController;
    private ChartPanel barChartPanel;
    private ChartPanel pieChartPanel;

    public JavaSwingUI(String title, CandidatoController candidatoCtr) {
        super(title);

        this.candidatoController = candidatoCtr;

        createCharts();
        this.setVisible(true);

    }

    public void sendNewVoteToCandidate(String candidate) {
        candidatoController.increaseVotosCanditado(candidate);
    }

    public void createCharts() {

        CandidatoDao[] candidatos = candidatoController.getAllCandidatos();
        for (int i = 0; i < candidatos.length; i++) {
            CandidatoDao candidato = candidatos[i];

            JButton b = new JButton("Vota por " + candidato.getNombre());
            b.setBounds(130 + 280 * i, 150, 200, 40);// x axis, y axis, width, height

            b.addActionListener(e -> sendNewVoteToCandidate(candidato.getNombre()));

            this.add(b);
        }

        this.setSize(1000, 700);// width and height
        this.setLayout(null);// using no layout managers

        BarChart chart = new BarChart("Votes bar chart", candidatoController);
        barChartPanel = chart.getChartPanel();

        PieChart demo = new PieChart(candidatoController);
        pieChartPanel = demo.getChartPanel();

        barChartPanel.setBounds(140, 230, 360, 167);
        pieChartPanel.setBounds(550, 230, 360, 167);

        this.add(barChartPanel);
        this.add(pieChartPanel);
    }

    @Override
    public void render() {

    }

    @Override
    public void update() {
        System.out.println("updating UI");

        this.remove(barChartPanel);
        this.remove(pieChartPanel);
        this.revalidate();

        createCharts();
        this.revalidate();

        this.repaint();
    }
}
