package baseInfo;

public class mapMessage {		//一个保存要绘制地图全部信息的类
	private int[] mapstay;
	private double[] xarray;
	private double[] yarray;
	public mapMessage(int[] mapStay,double[] xarray,double[] yarray){		//构造器
		this.mapstay=mapStay;
		this.xarray=xarray;
		this.yarray=yarray;
	}
	public int[] getMapStay(){
		return this.mapstay;
	}
	public double[] getXarray(){
		return this.xarray;
	}
	public double[] getYarray(){
		return this.yarray;
	}

}
