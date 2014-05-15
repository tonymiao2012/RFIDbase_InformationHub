package baseInfo;

public class SomeFunction {
	public SomeFunction(){
	}
	public int[] tagIt(int[] cardid,int m,int[] cardidScope,int r){	//r为实际范围，m为收到卡号数
		int[] tag=new int[r];		//标记数组
		for(int i=0;i<r;i++)		//标记数组清零
			tag[i]=0;
		for(int i=0;i<m;i++){		//输出函数。搜索不在收到卡号范围内的所有指定卡号
			for(int n=0;n<r;n++){
				if(cardid[i]==cardidScope[n])
					tag[n]=1;		//做标记
			}
		}
		return tag;
	}
	public int[] getRangeM(int r1,int r2){//方式2录入范围数组
		int m=0;
		if(r1>r2)		//进行判断
			m=r1-r2+1;
		else
			m=r2-r1+1;
		int[] cardidScope=new int[m];
		if(r1>r2){
			cardidScope[0]=r2;
			for(int i=1;i<m;i++)
				cardidScope[i]=cardidScope[i-1]+1;
		}else if(r1<r2){
			cardidScope[0]=r1;
			for(int i=1;i<m;i++)
				cardidScope[i]=cardidScope[i-1]+1;
		}else{
			cardidScope[0]=r1;
		}
		return cardidScope;
	}
	public String getString2(int[] cardidScope){	//方式2拼接范围字符串
		String s="";
		int m=cardidScope.length;
		//System.out.println(m);
		for(int i=0;i<m-1;i++){
			s+=cardidScope[i];
			s+=",";
		}
		s+=cardidScope[m-1];

		return s;	
	}
	public String getMapTable(String time){		//单表表名拼接函数,暂时为地图模块专用
		String[] s1=new String[2];
		String[] s2=new String[3];
		s1=time.split(" ");		//得到了日期
		String s=s1[0].toString();
		s2=s.split("-"); 		//日期的3个字段
		s="ZTEST_"+s2[1]+s2[2];		//拼接成sql表形式
		return s;
	}

	 public String[] getTableTrim(String[] array){		//多表拼接表名函数，返回一个表名数组,参数为日期数组
		 String[] temp=new String[3];
		 String[] result=new String[array.length];
		 for(int i=0;i<array.length;i++){
			 temp=array[i].split("-");		//字符串分割
			 result[i]="ZTEST_"+temp[1]+temp[2];
		 }
		 return result;
	}

	public String[] getDateArray(String time)throws ArrayIndexOutOfBoundsException{		//得到一个日期字段数组，参数为日期字符串
		String[] s1=new String[2];
		String[] s2=new String[3];
		s1=time.split(" ");		//得到了日期
		String s=s1[0].toString();
		s2=s.split("-"); 		//日期的3个字段
		return s2;
	}

	public String[] dateRange(String[] begintime,String[] endtime)throws ArrayIndexOutOfBoundsException{		//多表时用,返回日期数组。格式YYYY-MM-DD
		int[] ia=new int[begintime.length];		//begintime转换数组
		for(int i=0;i<begintime.length;i++){
		   ia[i]=Integer.parseInt(begintime[i]);
		}
		int[] ib=new int[endtime.length];		//endtime转换数组
		for(int i=0;i<endtime.length;i++){
		   ib[i]=Integer.parseInt(endtime[i]);
		}
		int n=0;		//多表查询日期数组范围
		if(ia[2]<ib[2])
			n=ib[2]-ia[2]+1;
		else
			n=ia[2]-ib[2]+1;
		String[] s=new String[n];		//要返回的数组
		s[0]=ia[0]+"-"+ia[1]+"-"+ia[2];
		int m=ia[2]+1;		//累加器
		for(int i=1;i<n-1;i++){		//拼接
			s[i]=ia[0]+"-"+ia[1]+"-"+m;
			m++;
			}
		s[n-1]=ia[0]+"-"+ia[1]+"-"+ib[2];
		return s;
	}
	//SQL语句拼接
	public String sqlPlus1(String[] dateA,String[] dateT,String number,String range,String begintime,String endtime){	//sql拼接函数
		String sql="";
		String temp="";
		if(dateA.length==1){
			sql="SELECT DISTINCT cardid FROM "+dateT[0]+" WHERE tianxian1>=0 and readerid="+number+" AND (time BETWEEN to_date('"+begintime+"','yyyy-mm-dd hh24:mi:ss') AND to_date('"+endtime+"','yyyy-mm-dd hh24:mi:ss')) AND cardid IN("+range+")";
		}
		else if(dateA.length==2){
			temp=dateA[0]+" "+"23:59:59";
			sql="SELECT DISTINCT cardid FROM "+dateT[0]+" WHERE tianxian1>=0 and readerid="+number+" AND (time BETWEEN to_date('"+begintime+"','yyyy-mm-dd hh24:mi:ss') AND to_date('"+temp+"','yyyy-mm-dd hh24:mi:ss')) AND cardid IN("+range+")";
			sql+=" "+"union"+" ";
			String temp1=dateA[1]+" "+"00:00:01";
			sql+="SELECT DISTINCT cardid FROM "+dateT[1]+" WHERE tianxian1>=0 and readerid="+number+" AND (time BETWEEN to_date('"+temp1+"','yyyy-mm-dd hh24:mi:ss') AND to_date('"+endtime+"','yyyy-mm-dd hh24:mi:ss')) AND cardid IN("+range+")";
		}
		else{
			temp=dateA[0]+" "+"23:59:59";
			sql="SELECT DISTINCT cardid FROM "+dateT[0]+" WHERE tianxian1>=0 and readerid="+number+" AND (time BETWEEN to_date('"+begintime+"','yyyy-mm-dd hh24:mi:ss') AND to_date('"+temp+"','yyyy-mm-dd hh24:mi:ss')) AND cardid IN("+range+")";
			sql+=" "+"union"+" ";
			String temp2=dateA[1]+" "+"23:59:59";
			String temp1=dateA[1]+" "+"00:00:01";
			sql+="SELECT DISTINCT cardid FROM "+dateT[1]+" WHERE tianxian1>=0 and readerid="+number+" AND (time BETWEEN to_date('"+temp1+"','yyyy-mm-dd hh24:mi:ss') AND to_date('"+temp2+"','yyyy-mm-dd hh24:mi:ss')) AND cardid IN("+range+")";
			sql+=" "+"union"+" ";
			String temp3=dateA[2]+" "+"00:00:01";
			sql+="SELECT DISTINCT cardid FROM "+dateT[2]+" WHERE tianxian1>=0 and readerid="+number+" AND (time BETWEEN to_date('"+temp3+"','yyyy-mm-dd hh24:mi:ss') AND to_date('"+endtime+"','yyyy-mm-dd hh24:mi:ss')) AND cardid IN("+range+")";
		}
		//System.out.println(sql);
		return sql;
	}
	public String sqlPlus2(String[] dateA,String[] dateT,String number,String range,String begintime,String endtime){	//sql拼接函数
		String sql="";
		String temp="";
		if(dateA.length==1){
			sql="SELECT DISTINCT cardid FROM "+dateT[0]+" WHERE tianxian2>=0 and readerid="+number+" AND (time BETWEEN to_date('"+begintime+"','yyyy-mm-dd hh24:mi:ss') AND to_date('"+endtime+"','yyyy-mm-dd hh24:mi:ss')) AND cardid IN("+range+")";
		}
		else if(dateA.length==2){
			temp=dateA[0]+" "+"23:59:59";
			sql="SELECT DISTINCT cardid FROM "+dateT[0]+" WHERE tianxian2>=0 and readerid="+number+" AND (time BETWEEN to_date('"+begintime+"','yyyy-mm-dd hh24:mi:ss') AND to_date('"+temp+"','yyyy-mm-dd hh24:mi:ss')) AND cardid IN("+range+")";
			sql+=" "+"union"+" ";
			String temp1=dateA[1]+" "+"00:00:01";
			sql+="SELECT DISTINCT cardid FROM "+dateT[1]+" WHERE tianxian2>=0 and readerid="+number+" AND (time BETWEEN to_date('"+temp1+"','yyyy-mm-dd hh24:mi:ss') AND to_date('"+endtime+"','yyyy-mm-dd hh24:mi:ss')) AND cardid IN("+range+")";
		}
		else{
			temp=dateA[0]+" "+"23:59:59";
			sql="SELECT DISTINCT cardid FROM "+dateT[0]+" WHERE tianxian2>=0 and readerid="+number+" AND (time BETWEEN to_date('"+begintime+"','yyyy-mm-dd hh24:mi:ss') AND to_date('"+temp+"','yyyy-mm-dd hh24:mi:ss')) AND cardid IN("+range+")";
			sql+=" "+"union"+" ";
			String temp2=dateA[1]+" "+"23:59:59";
			String temp1=dateA[1]+" "+"00:00:01";
			sql+="SELECT DISTINCT cardid FROM "+dateT[1]+" WHERE tianxian2>=0 and readerid="+number+" AND (time BETWEEN to_date('"+temp1+"','yyyy-mm-dd hh24:mi:ss') AND to_date('"+temp2+"','yyyy-mm-dd hh24:mi:ss')) AND cardid IN("+range+")";
			sql+=" "+"union"+" ";
			String temp3=dateA[2]+" "+"00:00:01";
			sql+="SELECT DISTINCT cardid FROM "+dateT[2]+" WHERE tianxian2>=0 and readerid="+number+" AND (time BETWEEN to_date('"+temp3+"','yyyy-mm-dd hh24:mi:ss') AND to_date('"+endtime+"','yyyy-mm-dd hh24:mi:ss')) AND cardid IN("+range+")";
		}
		//System.out.println(sql);
		return sql;
	}
	public String sqlPlusAll(String sql1,String sql2){
		String result=sql1;
		result+=" "+"union"+" ";
		result+=sql2;
		return result;
	}
	public int[] getBaseNumber(String numbers){		//可以得到基站号数组，用于基站统计
		String[] temp=numbers.split(",");
		int[] result=new int[temp.length];
		for(int i=0;i<result.length;i++)
			result[i]=Integer.parseInt(temp[i]);
		return result;
	}
	 public String[] getTableTrimS(String[] array)throws ArrayIndexOutOfBoundsException{		//多表拼接表名函数，返回一个表名数组,参数为日期数组,用于基站统计
		 String[] temp=new String[3];
		 String[] result=new String[array.length];
		 for(int i=0;i<array.length;i++){
			 temp=array[i].split("-");		//字符串分割
			 result[i]="READERFLOW_"+temp[1]+temp[2];
		 }
		 return result;
	}
	public String[] getStaSql(int baseNumber,String[] table,String begintime,String endtime,String dateA[]){	//基站统计拼接SQL语句
		String[] sql=new String[table.length];
		if(table.length==1){
			sql[0]="select sum(recvcount) from "+table[0]+" where readerid="+baseNumber+" and time between to_date('"+begintime+"','yyyy-mm-dd hh24:mi:ss') AND to_date('"+endtime+"','yyyy-mm-dd hh24:mi:ss')";
		}else if(table.length==2){
			String temp=dateA[0]+" 23:59:59";
			sql[0]="select sum(recvcount) from "+table[0]+" where readerid="+baseNumber+" and time between to_date('"+begintime+"','yyyy-mm-dd hh24:mi:ss') AND to_date('"+temp+"','yyyy-mm-dd hh24:mi:ss')";
			String temp1=dateA[1]+" 00:00:01";
			sql[1]="select sum(recvcount) from "+table[1]+" where readerid="+baseNumber+" and time between to_date('"+temp1+"','yyyy-mm-dd hh24:mi:ss') AND to_date('"+endtime+"','yyyy-mm-dd hh24:mi:ss')";
		}else{
			String temp=dateA[0]+" 23:59:59";
			sql[0]="select sum(recvcount) from "+table[0]+" where readerid="+baseNumber+" and time between to_date('"+begintime+"','yyyy-mm-dd hh24:mi:ss') AND to_date('"+temp+"','yyyy-mm-dd hh24:mi:ss')";
			String temp1=dateA[1]+" 00:00:00";
			String temp2=dateA[1]+" 23:59:59";
			sql[1]="select sum(recvcount) from "+table[1]+" where readerid="+baseNumber+" and time between to_date('"+temp1+"','yyyy-mm-dd hh24:mi:ss') AND to_date('"+temp2+"','yyyy-mm-dd hh24:mi:ss')";
			String temp3=dateA[2]+" 00:00:01";
			sql[2]="select sum(recvcount) from "+table[2]+" where readerid="+baseNumber+" and time between to_date('"+temp3+"','yyyy-mm-dd hh24:mi:ss') AND to_date('"+endtime+"','yyyy-mm-dd hh24:mi:ss')";
		}
		return sql;
	}
	public String unitAdd(double m){		//单位换算函数
		String s="";
		if(m>=1024){
			m=m/1024;
			if(m>=1024){
				m=m/1024;
				if(m>=1024){
					m=m/1024;
					if(m>=1024){
						s=m+" GB";
						return s;
					}else{
						s=m+" GB";
						return s;
					}
				}else{
					s=m+" MB";
					return s;
				}
			}else{
				s=m+" KB";
				return s;
			}
		}else{
			s=m+" B";
			return s;
			}
	}
	public int[] judgeTables(String[] tables,String[] tablename) throws NullPointerException{
		int[] temp=new int[tables.length];
		for(int m=0;m<tables.length;m++){
			for(int n=0;n<tablename.length;n++){
				if(tables[m].equals(tablename[n]))
					temp[m]=1;
			}
		}
		return temp;
	}
	//map函数计算目标停留时间函数
	public mapMessage mapStay(int[] hour,int[] min,int[] sec,double[] xarray,double[] yarray){
		int m=0;	//计数器
 		int i=0;	//遍历器
		int p=0;	//哨兵
		int sum=0;	//时间求和，以秒为单位
		//ArrayList list=new ArrayList();
		//遍历数组
		while(i<=xarray.length-2){
			if(xarray[i]==xarray[i+1]){
				i++;
			}else{
				m+=1;
				i++;
			}
		}
		double[] getX=new double[m+1];		
		double[] getY=new double[m+1];
		i=0;	
		m=0;
		//坐标数组脱水。脱水后保存在getX[],getY[]中
		while(i<=xarray.length-2){
			if(xarray[i]==xarray[i+1]){
				if(i==0){
					getX[0]=xarray[0];
					getY[0]=yarray[0];
					m=1;
				}
				i++;
				}else{
					if(i==0){
						getX[0]=xarray[0];
						getY[0]=yarray[0];
						m=1;
					}
					i++;
					getX[m]=xarray[i];
					getY[m]=yarray[i];
					m++;
					}
		}
		//处理等待时间
		int[] mapstay=new int[m];
		m=0;		//清零
		i=0;
		while(i<=xarray.length-2){
			if(xarray[i]==xarray[i+1]){
				i++;
				
				if(i==xarray.length-1){
					sum=(hour[i]-hour[p])*3600+(min[i]-min[p])*60+(sec[i]-sec[p]);
					mapstay[m]=sum;
					sum=0;
				}		
			}else{
				sum=(hour[i]-hour[p])*3600+(min[i]-min[p])*60+(sec[i]-sec[p]);
				mapstay[m]=sum;
				sum=0;		//累加函数清零
				m++;
				p=i+1;		//重新赋值哨兵
				i++;
			}
		}
		/*测试数组
		for(i=0;i<getX.length;i++){
			System.out.println(mapstay[i]);
			//System.out.println(getX[i]);
			//System.out.println(getY[i]);
		}
		*/
		mapMessage mm=new mapMessage(mapstay,getX,getY);	//构造好地图信息类
		m=0;	
 		i=1;	
		p=0;	
		sum=0;	
		return mm;
	}
	//显示函数。换算时间
	public double[] mapShowTime(int[] mapstay){		//显示时间。有单位。要将int类型编程double保持精度？
		double[] temp=new double[mapstay.length];
		for(int i=0;i<temp.length;i++){
			temp[i]=(double)(mapstay[i]/60);
		}
		return temp;
	}
	public boolean judgetablename(String maptablename,String[] tablename){
		boolean result=false;
		for(int i=0;i<tablename.length;i++){
			if(maptablename.equals(tablename[i])){
				result=true;
				break;
			}
		}
		return result;
	}
	public mapBaseMessage readeridAndTime(long[] basenumber,String[] time){
		int m=0;	//计数器
 		int i=0;	//遍历器
		//遍历数组
		while(i<=basenumber.length-2){
			if(basenumber[i]==basenumber[i+1]){
				i++;
			}else{
				m+=1;
				i++;
			}
		}
		long[] readerid=new long[m+1];		//脱水后的基站数组
		String[] timeresult=new String[m+1];	//时间结果
		i=0;		//清零
		m=0;
		while(i<=basenumber.length-2){
			if(basenumber[i]==basenumber[i+1]){
				if(i==0){						//头值处理
					readerid[0]=basenumber[0];
					timeresult[0]=time[0];
					m=1;
				}
				i++;							//非头值向后查询
				}else{
					if(i==0){					//头值处理
						readerid[0]=basenumber[0];
						timeresult[0]=time[0];
						m=1;
					}
					i++;						//非头值处理
					readerid[m]=basenumber[i];
					timeresult[m]=time[i];
					m++;
					}
		}
		mapBaseMessage mb=new mapBaseMessage(readerid,timeresult);
		return mb;
	}
	
}



