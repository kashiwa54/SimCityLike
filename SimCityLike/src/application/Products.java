package application;

public enum Products {
	AGRICULTURE("農業","農",5),
	FISHERY("漁業","漁",5),
	FORESTORY("林業","林",10),
	STOCK_RAISING("畜産業","畜",8),
	FOOD_PROCESSING("食品加工","食",8),
	APPAREL("服飾","服",20),
	CHEMICAL("化学","化",20),
	ELECTRONICS("電化製品","電",50),
	METAL("金属","金",20);

	String japanese;
	String jp;
	int price;

	Products(String japanese,String jp,int price)	{
		this.japanese = japanese;
		this.jp = jp;
		this.price = price;
	}
}
