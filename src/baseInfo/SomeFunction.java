package baseInfo;

public class SomeFunction {
	public SomeFunction(){
	}
	public int[] tagIt(int[] cardid,int m,int[] cardidScope,int r){	//rΪʵ�ʷ�Χ��mΪ�յ�������
		int[] tag=new int[r];		//�������
		for(int i=0;i<r;i++)		//�����������
			tag[i]=0;
		for(int i=0;i<m;i++){		//������������������յ����ŷ�Χ�ڵ�����ָ������
			for(int n=0;n<r;n++){
				if(cardid[i]==cardidScope[n])
					tag[n]=1;		//�����
			}
		}
		return tag;
	}
	public int[] getRangeM(int r1,int r2){//��ʽ2¼�뷶Χ����
		int m=0;
		if(r1>r2)		//�����ж�
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
	public String getString2(int[] cardidScope){	//��ʽ2ƴ�ӷ�Χ�ַ���
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
	public String getMapTable(String time){		//�������ƴ�Ӻ���,��ʱΪ��ͼģ��ר��
		String[] s1=new String[2];
		String[] s2=new String[3];
		s1=time.split(" ");		//�õ�������
		String s=s1[0].toString();
		s2=s.split("-"); 		//���ڵ�3���ֶ�
		s="ZTEST_"+s2[1]+s2[2];		//ƴ�ӳ�sql����ʽ
		return s;
	}

	 public String[] getTableTrim(String[] array){		//���ƴ�ӱ�������������һ����������,����Ϊ��������
		 String[] temp=new String[3];
		 String[] result=new String[array.length];
		 for(int i=0;i<array.length;i++){
			 temp=array[i].split("-");		//�ַ����ָ�
			 result[i]="ZTEST_"+temp[1]+temp[2];
		 }
		 return result;
	}

	public String[] getDateArray(String time)throws ArrayIndexOutOfBoundsException{		//�õ�һ�������ֶ����飬����Ϊ�����ַ���
		String[] s1=new String[2];
		String[] s2=new String[3];
		s1=time.split(" ");		//�õ�������
		String s=s1[0].toString();
		s2=s.split("-"); 		//���ڵ�3���ֶ�
		return s2;
	}

	public String[] dateRange(String[] begintime,String[] endtime)throws ArrayIndexOutOfBoundsException{		//���ʱ��,�����������顣��ʽYYYY-MM-DD
		int[] ia=new int[begintime.length];		//begintimeת������
		for(int i=0;i<begintime.length;i++){
		   ia[i]=Integer.parseInt(begintime[i]);
		}
		int[] ib=new int[endtime.length];		//endtimeת������
		for(int i=0;i<endtime.length;i++){
		   ib[i]=Integer.parseInt(endtime[i]);
		}
		int n=0;		//����ѯ�������鷶Χ
		if(ia[2]<ib[2])
			n=ib[2]-ia[2]+1;
		else
			n=ia[2]-ib[2]+1;
		String[] s=new String[n];		//Ҫ���ص�����
		s[0]=ia[0]+"-"+ia[1]+"-"+ia[2];
		int m=ia[2]+1;		//�ۼ���
		for(int i=1;i<n-1;i++){		//ƴ��
			s[i]=ia[0]+"-"+ia[1]+"-"+m;
			m++;
			}
		s[n-1]=ia[0]+"-"+ia[1]+"-"+ib[2];
		return s;
	}
	//SQL���ƴ��
	public String sqlPlus1(String[] dateA,String[] dateT,String number,String range,String begintime,String endtime){	//sqlƴ�Ӻ���
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
	public String sqlPlus2(String[] dateA,String[] dateT,String number,String range,String begintime,String endtime){	//sqlƴ�Ӻ���
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
	public int[] getBaseNumber(String numbers){		//���Եõ���վ�����飬���ڻ�վͳ��
		String[] temp=numbers.split(",");
		int[] result=new int[temp.length];
		for(int i=0;i<result.length;i++)
			result[i]=Integer.parseInt(temp[i]);
		return result;
	}
	 public String[] getTableTrimS(String[] array)throws ArrayIndexOutOfBoundsException{		//���ƴ�ӱ�������������һ����������,����Ϊ��������,���ڻ�վͳ��
		 String[] temp=new String[3];
		 String[] result=new String[array.length];
		 for(int i=0;i<array.length;i++){
			 temp=array[i].split("-");		//�ַ����ָ�
			 result[i]="READERFLOW_"+temp[1]+temp[2];
		 }
		 return result;
	}
	public String[] getStaSql(int baseNumber,String[] table,String begintime,String endtime,String dateA[]){	//��վͳ��ƴ��SQL���
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
	public String unitAdd(double m){		//��λ���㺯��
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
	//map��������Ŀ��ͣ��ʱ�亯��
	public mapMessage mapStay(int[] hour,int[] min,int[] sec,double[] xarray,double[] yarray){
		int m=0;	//������
 		int i=0;	//������
		int p=0;	//�ڱ�
		int sum=0;	//ʱ����ͣ�����Ϊ��λ
		//ArrayList list=new ArrayList();
		//��������
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
		//����������ˮ����ˮ�󱣴���getX[],getY[]��
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
		//����ȴ�ʱ��
		int[] mapstay=new int[m];
		m=0;		//����
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
				sum=0;		//�ۼӺ�������
				m++;
				p=i+1;		//���¸�ֵ�ڱ�
				i++;
			}
		}
		/*��������
		for(i=0;i<getX.length;i++){
			System.out.println(mapstay[i]);
			//System.out.println(getX[i]);
			//System.out.println(getY[i]);
		}
		*/
		mapMessage mm=new mapMessage(mapstay,getX,getY);	//����õ�ͼ��Ϣ��
		m=0;	
 		i=1;	
		p=0;	
		sum=0;	
		return mm;
	}
	//��ʾ����������ʱ��
	public double[] mapShowTime(int[] mapstay){		//��ʾʱ�䡣�е�λ��Ҫ��int���ͱ��double���־��ȣ�
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
		int m=0;	//������
 		int i=0;	//������
		//��������
		while(i<=basenumber.length-2){
			if(basenumber[i]==basenumber[i+1]){
				i++;
			}else{
				m+=1;
				i++;
			}
		}
		long[] readerid=new long[m+1];		//��ˮ��Ļ�վ����
		String[] timeresult=new String[m+1];	//ʱ����
		i=0;		//����
		m=0;
		while(i<=basenumber.length-2){
			if(basenumber[i]==basenumber[i+1]){
				if(i==0){						//ͷֵ����
					readerid[0]=basenumber[0];
					timeresult[0]=time[0];
					m=1;
				}
				i++;							//��ͷֵ����ѯ
				}else{
					if(i==0){					//ͷֵ����
						readerid[0]=basenumber[0];
						timeresult[0]=time[0];
						m=1;
					}
					i++;						//��ͷֵ����
					readerid[m]=basenumber[i];
					timeresult[m]=time[i];
					m++;
					}
		}
		mapBaseMessage mb=new mapBaseMessage(readerid,timeresult);
		return mb;
	}
	
}



