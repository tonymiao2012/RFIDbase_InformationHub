package baseInfo;
import java.sql.*;


public class database {
	private String driver;
	private String url;
	private String username;
	private String password;
	//构造函数要声明为public。否则不可见
	public database(String driver,String url,String username,String password){		//构造函数
		this.url=url;
		this.username=username;
		this.password=password;
		this.driver=driver;
	}
	public Connection getConnection(){		//数据库连接函数。返回stm
	
		Connection con=null;
		try{
			Class.forName(driver);	//加载JDBC
			con=DriverManager.getConnection(url, username, password);
			//System.out.println("con连接成功");
		}catch(Exception e){
		    e.printStackTrace(); 
		    System.out.println("con连接异常");
		} 
		return con;
	}
	public Statement getStatement(Connection con){		//取得连接
		Statement stm=null;
		try {
			//ResultSet.TYPE_SCROLL_INSENSITIVE  双向滚动，但不及时更新，就是如果数据库里的数据修改过，并不在ResultSet中反应出来
			//ResultSet.CONCUR_READ_ONLY  只读取ResultSet
			stm=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			//stm=con.createStatement();
			//System.out.println("stm连接成功");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("stm连接异常");
		}
		return stm;
	}
	public int processSql(String sql,Statement stm) throws SQLException{		//返回一个m保存收到卡号数
		int m=0;
		ResultSet res=null;
		res = stm.executeQuery(sql);
		while(res.next()){
			m++;
		}
		res.close();
		return m;
	}
	public int[] process(String sql,Statement stm,int r){		//r保存的是实际范围大小,函数返回收到卡号数组
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
	public void closeConnS(Statement stm){		//关闭结果集stm
		try {
			stm.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("关闭stm异常");
		}
	}

	public void closeConnC(Connection con){		//关闭结果集con
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("关闭con异常");
		}
	}
	public int processSqlS(String sql,Statement stm)throws SQLException{		//得到SUM值的结果集
		int result=0;
		ResultSet res=null;
			res = stm.executeQuery(sql);
			if(res.next())
				result=res.getInt(1);

		return result;
	}
	public int[] getBaseNumAll(String dateA[],Statement stm,String bt,String et,String[] table) throws SQLException{
		String sql="";
		int m=0;		//基站数计数		
		ResultSet res=null;
		if(table.length==1){		//得到这个时间段内的所有基站号SQL语句
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
			while(res.next()){		//得到实际数目
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
	public String[] dataTableName(Statement stm) throws SQLException{		//得到总表函数
		ResultSet res=null;
		int m=0;
		int i=0;
		String sql="select table_name from user_tables where table_name like 'READERFLOW_%'";
		res = stm.executeQuery(sql);
		while(res.next()){		//得到实际数目
			m++;
		}
		String[] result=new String[m];		//结果数组
		res = stm.executeQuery(sql);
		while(res.next()){
			result[i]=res.getString("TABLE_NAME");
			i++;
		}
		res.close();
		return result;
	}
	public String[] dataTableName1(Statement stm) throws SQLException{		//基站查询器的总表函数
		ResultSet res=null;
		int m=0;
		int i=0;
		String sql="select table_name from user_tables where table_name like 'ZTEST_%'";
		res=stm.executeQuery(sql);
		while(res.next()){		//得到实际数目
			m++;
		}
		String[] result=new String[m];		//结果数组
		res = stm.executeQuery(sql);
		while(res.next()){
			result[i]=res.getString("TABLE_NAME");
			i++;
		}
		res.close();
		return result;
	}
	//根据表名，开始时间，结束时间，查询相应经纬度坐标
	public double[] mapGetxArray(Statement stm,String begintime,String endtime,String tablename,long mapcardid) throws SQLException{	//返回纬度数组latitude
		int m=0;
		int i=0;
		//SQL语句。取出结果集中所有X坐标
		String sql="select readercoord_test.readerx from "+tablename+",readercoord_test where "+tablename+".cardid="+mapcardid+" and "+tablename+".readerid=readercoord_test.readerid and  "+tablename+".time between to_date('"+begintime+"','yyyy-mm-dd hh24:mi:ss') AND to_date('"+endtime+"','yyyy-mm-dd hh24:mi:ss') order by "+tablename+".time";
		//测试部分
		//System.out.println(sql);
		ResultSet res=stm.executeQuery(sql);
		while(res.next()){
			m++;
		}
		double[] result=new double[m];	//结果数组
		res=stm.executeQuery(sql);
		while(res.next()){
			result[i]=res.getDouble(1);
			i++;
		}
		m=0;
		i=0;	//清零
		res.close();
		return result;
	}
	public double[] mapGetyArray(Statement stm,String begintime,String endtime,String tablename,long mapcardid) throws SQLException{
		int m=0;
		int i=0;
		//取出结果集中所有Y坐标
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
		m=0;	//清零
		i=0;
		res.close();
		return result;
	} 
	public int[] mapGetHourArray(Statement stm,String begintime,String endtime,String tablename,long mapcardid) throws SQLException{		
		//取出所有的小时，组成数组
		int i=0;
		int m=0;
		String sql="select to_char("+tablename+".time,'hh24') from "+tablename+",readercoord_test where "+tablename+".cardid="+mapcardid+" and "+tablename+".readerid=readercoord_test.readerid and  "+tablename+".time between to_date('"+begintime+"','yyyy-mm-dd hh24:mi:ss') AND to_date('"+endtime+"','yyyy-mm-dd hh24:mi:ss') order by "+tablename+".time";
		ResultSet res=stm.executeQuery(sql);
		/*
		res.last();		//先到最后一行
		m=res.getRow();		//得到行数
		res.beforeFirst();//光标回滚，回到首位
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
		//取出分钟数组
		int i=0;
		int m=0;
		String sql="select to_char("+tablename+".time,'mi') from "+tablename+",readercoord_test where "+tablename+".cardid="+mapcardid+" and "+tablename+".readerid=readercoord_test.readerid and  "+tablename+".time between to_date('"+begintime+"','yyyy-mm-dd hh24:mi:ss') AND to_date('"+endtime+"','yyyy-mm-dd hh24:mi:ss') order by "+tablename+".time";
		//System.out.println(sql);	//发现错误。表示分钟要用mi,而不是mm
		ResultSet res=stm.executeQuery(sql);
		/*
		res.last();
		m=res.getRow();
		res.beforeFirst();//光标回滚
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
		//取出秒钟数组
		int i=0;
		int m=0;
		String sql="select to_char("+tablename+".time,'ss') from "+tablename+",readercoord_test where "+tablename+".cardid="+mapcardid+" and "+tablename+".readerid=readercoord_test.readerid and  "+tablename+".time between to_date('"+begintime+"','yyyy-mm-dd hh24:mi:ss') AND to_date('"+endtime+"','yyyy-mm-dd hh24:mi:ss') order by "+tablename+".time";
		//System.out.println(sql);
		ResultSet res=stm.executeQuery(sql);
		/*
		res.last();	
		m=res.getRow();
		res.beforeFirst();//光标回滚
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
			cardidAll[i]=res.getLong(1);		//取出该表卡号集合
			i++;
		}
		
		for(i=0;i<m;i++){
			if(mapcardid==cardidAll[i]){		//验证是否有不在集合内情况
				mark=true;
				break;
			}else if(mapcardid!=cardidAll[i]&&mapcardid<cardidAll[i]){		//大于它的值就不用研究了
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

	
	
	
	
	
	
	
	
	
	
	