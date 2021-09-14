package application;

import javafx.scene.chart.Axis;
import javafx.scene.chart.StackedBarChart;

public class DemandBarChart<X,Y> extends StackedBarChart<X, Y>{

	public DemandBarChart(Axis<X> xAxis, Axis<Y> yAxis) {
		super(xAxis, yAxis);
		xAxis.setTickLabelsVisible(false);
		yAxis.setTickLabelsVisible(false);
		this.setLegendVisible(false);
		this.setAnimated(false);
	}

}
