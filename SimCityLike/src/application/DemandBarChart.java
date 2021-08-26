package application;

import javafx.scene.chart.Axis;
import javafx.scene.chart.BarChart;

public class DemandBarChart<X,Y> extends BarChart<X, Y>{

	public DemandBarChart(Axis<X> xAxis, Axis<Y> yAxis) {
		super(xAxis, yAxis);
		xAxis.setTickLabelsVisible(false);
		yAxis.setTickLabelsVisible(false);
	}

}
