package baseInfo;

public class mapMessage {		//һ������Ҫ���Ƶ�ͼȫ����Ϣ����
	private int[] mapstay;
	private double[] xarray;
	private double[] yarray;
	public mapMessage(int[] mapStay,double[] xarray,double[] yarray){		//������
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
