package baseInfo;

public class mapBaseMessage {
	private long[] basenumber;
	private String[] time;
	public mapBaseMessage(long[] basenumber,String[] time){
		this.basenumber=basenumber;
		this.time=time;
	}
	public long[] getBaseArray(){
		return this.basenumber;
	}
	public String[] getTimeArray(){
		return this.time;
	}
}
