package application;

public enum Products {
	AGRICULTURE("農業","農"),
	FISHERY("漁業","漁"),
	FORESTORY("林業","林"),
	STOCK_RAISING("畜産業","畜"),
	FOOD_PROCESSING("食品加工","食"),
	APPAREL("服飾","服"),
	CHEMICAL("化学","化"),
	ELECTRONICS("電化製品","電"),
	METAL("金属","金");

	String japanese;
	String jp;

	Products(String japanese,String jp)	{
		this.japanese = japanese;
		this.jp = jp;
	}
}
