package application;

public enum Products {
	AGRICULTURE("農業","農",30),
	FISHERY("漁業","漁",30),
	FORESTORY("林業","林",50),
	STOCK_RAISING("畜産業","畜",40),
	FOOD_PROCESSING("食品加工","食",40),
	APPAREL("服飾","服",100),
	CHEMICAL("化学","化",100),
	ELECTRONICS("電化製品","電",250),
	METAL("金属","金",100);

	String japanese;
	String jp;
	int price;

	Products(String japanese,String jp,int price)	{
		this.japanese = japanese;
		this.jp = jp;
		this.price = price;
	}
}
