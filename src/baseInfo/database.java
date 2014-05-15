package baseInfo;
import java.sql.*;


public class database {
	private String driver;
	private String url;
	private String username;
	private String password;
	//���캯��Ҫ����Ϊpublic�����򲻿ɼ�
	public database(String driver,String url,String username,String password){		//���캯��
		this.url=url;
		this.username=username;
		this.password=password;
		this.driver=driver;
	}
	public Connection getConnection(){		//���ݿ����Ӻ���������stm
	
		Connection con=null;
		try{
			Class.forName(driver);	//����JDBC
			con=DriverManager.getConnection(url, username, password);
			//System.out.println("con���ӳɹ�");
		}catch(Exception e){
		    e.printStackTrace(); 
		    System.out.println("con�����쳣");
		} 
		return con;
	}
	public Statement getStatement(Connection con){		//ȡ������
		Statement stm=null;
		try {
			//ResultSet.TYPE_SCROLL_INSENSITIVE  ˫�������������ʱ���£�����������ݿ���������޸Ĺ���������ResultSet�з�Ӧ����
			//ResultSet.CONCUR_READ_ONLY  ֻ��ȡResultSet
			stm=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			//stm=con.createStatement();
			//System.out.println("stm���ӳɹ�");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("stm�����쳣");
		}
		return stm;
	}
	public int processSql(String sql,Statement stm) throws SQLException{		//����һ��m�����յ�������
		int m=0;
		ResultSet res=null;
		res = stm.executeQuery(sql);
		while(res.next()){
			m++;
		}
		res.close();
		return m;
	}
	public int[] process(String sql,Statement stm,int r){		//r�������ʵ�ʷ�Χ��С,���������յ���������
		ResultSet res=null;
		int i=0;
		int[] cardid=new int[r];
		try {
			res = stm.executeQuery(sql);
			while(res.next()){
				cardid[i]=res.getInt("cardid");
				i++;
			}
			res.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println(e1);
		}
		return cardid;
	}
	public void closeConnS(Statement stm){		//�رս����stm
		try {
			stm.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("�ر�stm�쳣");
		}
	}

	public void closeConnC(Connection con){		//�رս����con
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("�ر�con�쳣");
		}
	}
	public int processSqlS(String sql,Statement stm)throws SQLException{		//�õ�SUMֵ�Ľ����
		int result=0;
		ResultSet res=null;
			res = stm.executeQuery(sql);
			if(res.next())
				result=res.getInt(1);

		return result;
	}
	public int[] getBaseNumAll(String dateA[],Statement stm,String bt,String et,String[] table) throws SQLException{
		String sql="";
		int m=0;		//��վ������		
		ResultSet res=null;
		if(table.length==1){		//�õ����ʱ����ڵ����л�վ��SQL���
			sql="select distinct readerid from "+table[0]+" where time between to_date('"+bt+"','yyyy-mm-dd hh24:mi:ss') AND to_date('"+et+"','yyyy-mm-dd hh24:mi:ss')";
		}else if(table.length==2){
			String temp=dateA[0]+" 23:59:59";
			sql="select distinct readerid from "+table[0]+" where time between to_date('"+bt+"','yyyy-mm-dd hh24:mi:ss') AND to_date('"+temp+"','yyyy-mm-dd hh24:mi:ss')";
			sql+=" "+"union"+" ";
			String temp1=dateA[1]+" 00:00:01";
			sql+="select distinct readerid from "+table[1]+" where time between to_date('"+temp1+"','yyyy-mm-dd hh24:mi:ss') AND to_date('"+et+"','yyyy-mm-dd hh24:mi:ss')";
		}else{
			String temp=dateA[0]+" 23:59:59";
			sql="select distinct readerid from "+table[0]+" where time between to_date('"+bt+"','yyyy-mm-dd hh24:mi:ss') AND to_date('"+temp+"','yyyy-mm-dd hh24:mi:ss')";
			sql+=" "+"union"+" ";
			String temp1=dateA[1]+" 00:00:01";
			String temp2=dateA[1]+" 23:59:59";
			sql+="select distinct readerid from "+table[1]+" where time between to_date('"+temp1+"','yyyy-mm-dd hh24:mi:ss') AND to_date('"+temp2+"','yyyy-mm-dd hh24:mi:ss')";
			String temp3=dateA[2]+" 00:00:01";
			sql+=" "+"union"+" ";
			sql+="select distinct readerid from "+table[2]+" where time between to_date('"+temp3+"','yyyy-mm-dd hh24:mi:ss') AND to_date('"+et+"','yyyy-mm-dd hh24:mi:ss')";
		}
			res = stm.executeQuery(sql);
			while(res.next()){		//�õ�ʵ����Ŀ
				m++;
			}
			int[] result=new int[m];
			int i=0;
			res = stm.executeQuery(sql);
			while(res.next()){
				result[i]=res.getInt("readerid");
				i++;
			}
			res.close();
			return result;
	}
	public String[] dataTableName(Statement stm) throws SQLException{		//�õ��ܱ���
		ResultSet res=null;
		int m=0;
		int i=0;
		String sql="select table_name from user_tables where table_name like 'READERFLOW_%'";
		res = stm.executeQuery(sql);
		while(res.next()){		//�õ�ʵ����Ŀ
			m++;
		}
		String[] result=new String[m];		//�������
		res = stm.executeQuery(sql);
		while(res.next()){
			result[i]=res.getString("TABLE_NAME");
			i++;
		}
		res.close();
		return result;
	}
	public String[] dataTableName1(Statement stm) throws SQLException{		//��վ��ѯ�����ܱ���
		ResultSet res=null;
		int m=0;
		int i=0;
		String sql="select table_name from user_tables where table_name like 'ZTEST_%'";
		res=stm.executeQuery(sql);
		while(res.next()){		//�õ�ʵ����Ŀ
			m++;
		}
		String[] result=new String[m];		//�������
		res = stm.executeQuery(sql);
		while(res.next()){
			result[i]=res.getString("TABLE_NAME");
			i++;
		}
		res.close();
		return result;
	}
	//���ݱ�������ʼʱ�䣬����ʱ�䣬��ѯ��Ӧ��γ������
	public double[] mapGetxArray(Statement stm,String begintime,String endtime,String tablename,long mapcardid) throws SQLException{	//����γ������latitude
		int m=0;
		int i=0;
		//SQL��䡣ȡ�������������X����
		String sql="select readercoord_test.readerx from "+tablename+",readercoord_test where "+tablename+".cardid="+mapcardid+" and "+tablename+".readerid=readercoord_test.readerid and  "+tablename+".time between to_date('"+begintime+"','yyyy-mm-dd hh24:mi:ss') AND to_date('"+endtime+"','yyyy-mm-dd hh24:mi:ss') order by "+tablename+".time";
		//���Բ���
		//System.out.println(sql);
		ResultSet res=stm.executeQuery(sql);
		while(res.next()){
			m++;
		}
		double[] result=new double[m];	//�������
		res=stm.executeQuery(sql);
		while(res.next()){
			result[i]=res.getDouble(1);
			i++;
		}
		m=0;
		i=0;	//����
		res.close();
		return result;
	}
	public double[] mapGetyArray(Statement stm,String begintime,String endtime,String tablename,long mapcardid) throws SQLException{
		int m=0;
		int i=0;
		//ȡ�������������Y����
		String sql="select readercoord_test.readery from "+tablename+",readercoord_test where "+tablename+".cardid="+mapcardid+" and "+tablename+".readerid=readercoord_test.readerid and  "+tablename+".time between to_date('"+begintime+"','yyyy-mm-dd hh24:mi:ss') AND to_date('"+endtime+"','yyyy-mm-dd hh24:mi:ss') order by "+tablename+".time";
		ResultSet res=stm.executeQuery(sql);
		while(res.next()){
			m++;
		}
		double[] result=new double[m];
		res=stm.executeQuery(sql);
		while(res.next()){
			result[i]=res.getDouble(1);
			i++;
		}
		m=0;	//����
		i=0;
		res.close();
		return result;
	} 
	public int[] mapGetHourArray(Statement stm,String begintime,String endtime,String tablename,long mapcardid) throws SQLException{		
		//ȡ�����е�Сʱ���������
		int i=0;
		int m=0;
		String sql="select to_char("+tablename+".time,'hh24') from "+tablename+",readercoord_test where "+tablename+".cardid="+mapcardid+" and "+tablename+".readerid=readercoord_test.readerid and  "+tablename+".time between to_date('"+begintime+"','yyyy-mm-dd hh24:mi:ss') AND to_date('"+endtime+"','yyyy-mm-dd hh24:mi:ss') order by "+tablename+".time";
		ResultSet res=stm.executeQuery(sql);
		/*
		res.last();		//�ȵ����һ��
		m=res.getRow();		//�õ�����
		res.beforeFirst();//���ع����ص���λ
		*/
		while(res.next()){
			m++;
		}
		int[] result=new int[m];
		res=stm.executeQuery(sql);
		while(res.next()){
			result[i]=res.getInt(1);
			i++;
		}
		i=0;
		res.close();
		return result;	
	}
	public int[] mapGetMinArray(Statement stm,String begintime,String endtime,String tablename,long mapcardid) throws SQLException{
		//ȡ����������
		int i=0;
		int m=0;
		String sql="select to_char("+tablename+".time,'mi') from "+tablename+",readercoord_test where "+tablename+".cardid="+mapcardid+" and "+tablename+".readerid=readercoord_test.readerid and  "+tablename+".time between to_date('"+begintime+"','yyyy-mm-dd hh24:mi:ss') AND to_date('"+endtime+"','yyyy-mm-dd hh24:mi:ss') order by "+tablename+".time";
		//System.out.println(sql);	//���ִ��󡣱�ʾ����Ҫ��mi,������mm
		ResultSet res=stm.executeQuery(sql);
		/*
		res.last();
		m=res.getRow();
		res.beforeFirst();//���ع�
		*/
		while(res.next()){
			m++;
		}
		int[] result=new int[m];
		res=stm.executeQuery(sql);
		while(res.next()){
			result[i]=res.getInt(1);
			i++;
		}
		i=0;
		res.close();
		return result;
	}
	public int[] mapGetSecArray(Statement stm,String begintime,String endtime,String tablename,long mapcardid) throws SQLException{
		//ȡ����������
		int i=0;
		int m=0;
		String sql="select to_char("+tablename+".time,'ss') from "+tablename+",readercoord_test where "+tablename+".cardid="+mapcardid+" and "+tablename+".readerid=readercoord_test.readerid and  "+tablename+".time between to_date('"+begintime+"','yyyy-mm-dd hh24:mi:ss') AND to_date('"+endtime+"','yyyy-mm-dd hh24:mi:ss') order by "+tablename+".time";
		//System.out.println(sql);
		ResultSet res=stm.executeQuery(sql);
		/*
		res.last();	
		m=res.getRow();
		res.beforeFirst();//���ع�
		*/
		while(res.next()){
			m++;
		}
		int[] result=new int[m];
		res=stm.executeQuery(sql);
		while(res.next()){
			result[i]=res.getInt(1);
			i++;
		}
		i=0;
		res.close();
		return result;
	}
	public boolean judgeCardid(long mapcardid,Statement stm,String tablename) throws SQLException{
		int m=0;
		int i=0;
		boolean mark=false;
		//boolean mark=true;
		
		String sql="select distinct cardid from "+tablename+" order by cardid";
		ResultSet res=stm.executeQuery(sql);
		while(res.next()){
			m++;
		}
		/*
		res.last();
		m=res.getRow();
		res.beforeFirst();
		*/
		long cardidAll[]=new long[m];
		res=stm.executeQuery(sql);
		while(res.next()){
			cardidAll[i]=res.getLong(1);		//ȡ���ñ��ż���
			i++;
		}
		
		for(i=0;i<m;i++){
			if(mapcardid==cardidAll[i]){		//��֤�Ƿ��в��ڼ��������
				mark=true;
				break;
			}else if(mapcardid!=cardidAll[i]&&mapcardid<cardidAll[i]){		//��������ֵ�Ͳ����о���
				mark=false;
				break;
			}
				
		}
		
		return mark;
	}
	public mapBaseMessage mapGetBaseNum(Statement stm,String begintime,String endtime,String tablename,long mapcardid) throws SQLException{
		
		int m=0;
		int i=0;
		String sql="select "+tablename+".readerid,to_char("+tablename+".time,'hh24:mi:ss') from "+tablename+",readercoord_test where "+tablename+".cardid="+mapcardid+" and "+tablename+".readerid=readercoord_test.readerid and  "+tablename+".time between to_date('"+begintime+"','yyyy-mm-dd hh24:mi:ss') AND to_date('"+endtime+"','yyyy-mm-dd hh24:mi:ss') order by "+tablename+".time";
		
		ResultSet res=stm.executeQuery(sql);
		res.last();
		m=res.getRow();
		res.beforeFirst();
		long[] readerid=new long[m];
		String[] time=new String[m];
		while(res.next()){
			readerid[i]=res.getLong(1);
			time[i]=res.getString(2);
			i++;
		}
		mapBaseMessage mb=new mapBaseMessage(readerid,time);
		return mb;
	}

}

	
	
	
	
	
	
	
	
	
	
	