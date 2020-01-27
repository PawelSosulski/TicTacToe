package TicTacToe.controllers;

import TicTacToe.model.TTTGameResults;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.List;

public class StatisticSceneController {


    private Stage statisticScene;
    private String firstName;
    private String secondName;
    private List<TTTGameResults> scoreTable;
    private boolean isTransferDone;

    @FXML
    private LineChart<String, Integer> chart;


    @FXML
    private void initialize() {

        if (isTransferDone) {
            if (scoreTable.size() > 0) {
                XYChart.Series serie1 = new XYChart.Series<>();
                serie1.setName(firstName);
                XYChart.Series serie2 = new XYChart.Series<>();
                serie2.setName(secondName);
                XYChart.Series serie3 = new XYChart.Series<>();
                serie3.setName("Draw");

                for (int i = 1; i <= scoreTable.size(); i++) {
                    serie1.getData().add(new XYChart.Data(String.valueOf(i), scoreTable.get(i - 1).getFirstWin()));
                    serie2.getData().add(new XYChart.Data(String.valueOf(i), scoreTable.get(i - 1).getSecondWin()));
                    serie3.getData().add(new XYChart.Data(String.valueOf(i), scoreTable.get(i - 1).getDrawGame()));
                }

                chart.getData().addAll(serie1, serie2, serie3);

            }
        }
    }


    @FXML
    private void handleOkButton(ActionEvent event) {
        statisticScene = (Stage) ((Node) event.getSource()).getScene().getWindow();
        statisticScene.close();
    }

    public void transfer(List<TTTGameResults> scoreTable, String firstName, String secondName) {
        this.scoreTable = scoreTable;
        this.firstName = firstName;
        this.secondName = secondName;
        isTransferDone = true;
        initialize();
    }
}
