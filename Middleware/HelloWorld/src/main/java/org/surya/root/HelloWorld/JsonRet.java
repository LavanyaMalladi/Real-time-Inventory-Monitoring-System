package org.surya.root.HelloWorld;

import java.io.BufferedReader;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Map;

@Path("/")
public class JsonRet {
	
	@GET
	@Path("get_instant_alert/")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getInstantAlert(){
		
		
		
		
		BufferedReader br = null;
		FileReader fr = null;
		String result="";
		try {
                        
			fr = new FileReader("C:\\Users\\supellak\\Desktop\\file.txt");
			br = new BufferedReader(fr);

			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {
		                         if(Integer.parseInt(sCurrentLine)==1){
		                     Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","inventory","root");
             Statement st=con.createStatement();
              ResultSet rs=st.executeQuery("select title from alerts where read_status = 1 ");
            
              
              
              while(rs.next()){  
                 result=result+rs.getString(1)+"\n";
              }
              FileOutputStream fos = new FileOutputStream("C:\\Users\\supellak\\Desktop\\file.txt");       
              
              String str="0";
         	 
  	        byte b[] = str.getBytes();
  	 
  	        for(int i=0;i<b.length;i++)
  	        {
  	            fos.write(b[i]);
  	        }
  	         
  	        fos.close();                      
  	       	      
     
  st.executeUpdate("update alerts set read_status = 0 where read_status = 1");
        	}
		                        
		                         
                        }
		}catch (Exception e) {

			e.printStackTrace();
			result=e.toString();

		}
		
		if(result.length()==0)
		{
			result="-1";
		}
		 return Response
	                .status(200)
	                .header("Access-Control-Allow-Origin", "*")
	                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
	                .header("Access-Control-Allow-Credentials", "true")
	                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
	                .header("Access-Control-Max-Age", "1209600")
	                .entity(result)
	                .build();
               		
		
		

	}
	

	
	
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("getDashboard/")
	public Response getDashboard(){
		
		
		
		
		JSONArray jArray2=new JSONArray();
		
try{		
		
			

	                        String result="";
	                        String result1="";
                          Class.forName("oracle.jdbc.driver.OracleDriver");
	                 Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","inventory","root");
	             Statement st=con.createStatement();
	        int n;
	        

	        
	        ResultSet rs1=st.executeQuery("select product_id,freshness_index from FRESHNESS f where time=(select max(time) from freshness f1 where f.product_id=f1.product_id) order by product_id");
            JSONArray jArray = new JSONArray();
           
             while (rs1.next()) {
  JSONObject json = new JSONObject();
  json.put("id",rs1.getInt(1));
  json.put("freshness_index",rs1.getString(2));
  jArray.add(json);
             }
            jArray2.add(jArray);
	        
	        
	              ResultSet rs=st.executeQuery("select ID,product_id,title,to_char(time,'hh24:mm dd-mon') from alerts where resolved = 0 order by rownum desc");
	           JSONArray jArray1 = new JSONArray();
	              while(rs.next()){
	               JSONObject json1 = new JSONObject();
	  
	                 result=rs.getString(3)+" in Mushrooms @"+rs.getString(4);
	                 json1.put("id",rs.getInt(1));
	                  json1.put("item",result);
	                    jArray1.add(json1);
	              }
	              
	              jArray2.add(jArray1);  
	              
	           
		              
	   
       ResultSet rs2=st.executeQuery("select product_id ,ideal_hum_max,ideal_hum_min,ideal_temp_max,ideal_temp_min from contents order by product_id");
       JSONArray jArray3 = new JSONArray();
      
        while (rs2.next()) {
JSONObject json5 = new JSONObject();
json5.put("id",rs2.getInt(1));
json5.put("max_hum",rs2.getInt(2));
json5.put("min_hum",rs2.getInt(3));
json5.put("max_temp",rs2.getInt(4));
json5.put("min_temp",rs2.getInt(5));
jArray3.add(json5);
	}

       jArray2.add(jArray3);
       	            
	        }
	        catch (Exception e) {

				e.printStackTrace();
				

			}

		
		
		
		return Response
                .status(200)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .header("Access-Control-Max-Age", "1209600")
                .entity(jArray2.toString())
                .build();
		
		
		
	}
	
	

	
	
	
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response sd(){
	
		
		
		return Response
                .status(200)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .header("Access-Control-Max-Age", "1209600")
                .entity("1")
                .build();
		
		
		
	}
	
	
	
	
	
	
	
	
	
	@GET
	@Path("push_freshness_index/{freshness}/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response pushFreshness(@PathParam("freshness") String fresh,@PathParam("id") String id){
		
		 try  {
	          
	            int title = Integer.valueOf(fresh);
	        Class.forName("oracle.jdbc.driver.OracleDriver");
	                 Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","inventory","root");
	             Statement st=con.createStatement();
	            int num=1;
	              ResultSet rs=st.executeQuery("insert into freshness(product_id,freshness_index)values("+id+",'"+title+"') ");
	          
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		
		return Response
                .status(200)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .header("Access-Control-Max-Age", "1209600")
                .entity("1")
                .build();
		
	}
	
	
	
	

	
	@GET
	@Path("get_ideal_values/{productId}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response get_ideal_values(@PathParam("productId") String productId){
		
		 JSONObject jo = new JSONObject();
       
		
		try {

			
			
			
			 Class.forName("oracle.jdbc.driver.OracleDriver");
             Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","inventory","root");
         Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("select product_id,ideal_temp_max,ideal_temp_min,ideal_hum_max,ideal_hum_min,expiry_date from contents where product_id="+productId);
          JSONArray Jarr=new JSONArray();
  
 while(rs.next())
             {
                 Jarr.add(rs.getInt(1));
                  Jarr.add(rs.getInt(2));
                   Jarr.add(rs.getInt(3));
                    Jarr.add(rs.getInt(4));
                     Jarr.add(rs.getInt(5));
                     Jarr.add(rs.getInt(6));
             }
            jo.put("arrayName",Jarr);
            
            
            
            
            
            ResultSet rs1=st.executeQuery("select * from (select freshness_index from freshness where product_id=1 order by time desc )where rownum=1");
            
            
            
             while (rs1.next()) {
 jo.put("freshness", rs1.getInt(1));
 
}
            
            
			
			
		    } catch (Exception ex) {
		    	
		    	
				
		    	
		    }
		
		
		return Response
                .status(200)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .header("Access-Control-Max-Age", "1209600")
                .entity(jo.toString())
                .build();
		
		
		
	}
	
	
	
	
	@GET
	@Path("resolve_alert/{alertId}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response resolveAlert(@PathParam("alertId") String alertId){
		
		
		String ret="";
		try {
            
            int alert_id = Integer.parseInt(alertId);
       
        Class.forName("oracle.jdbc.driver.OracleDriver");
                 Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","inventory","root");
             Statement st=con.createStatement();
          
		PreparedStatement preparedStatement = null;

		String updateTableSQL = "UPDATE ALERTS SET RESOLVED = 1 WHERE ID = ?";

			
			preparedStatement = con.prepareStatement(updateTableSQL);

			preparedStatement.setInt(1,alert_id);

			preparedStatement.executeUpdate();

			ret="1";
       
        } catch (Exception e) {
            e.printStackTrace();
            ret=e.toString();
        }

		
		
		return Response
                .status(200)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .header("Access-Control-Max-Age", "1209600")
                .entity(ret)
                .build();
		
		
		
	}
	
	
	 @GET
	 @Path("push_alerts/{alert}/{id}")
	    @Produces(MediaType.TEXT_PLAIN)
		public Response getUserById(@PathParam("alert") String projectId,@PathParam("id") String productId) {
		 
		 
		 String out="0";
		 
		 
		 String title = projectId;
	       
         try{
        	 
	        Class.forName("oracle.jdbc.driver.OracleDriver");
	        out="1";
	                 Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","inventory","root");
	    
	             Statement st=con.createStatement();
	            
	              ResultSet rs=st.executeQuery("insert into alerts(title,read_status,resolved,product_id,id)values('"+title+"',1,0,"+productId+",SEQ_ALERTS1.nextval) ");
	                FileOutputStream fos = new FileOutputStream("C:\\Users\\supellak\\Desktop\\file.txt");
	           
	                String str="1";
	 
	        byte b[] = str.getBytes();
	 
	        for(int i=0;i<b.length;i++)
	        {
	            fos.write(b[i]);
	        }
	         
	        fos.close();
	        
	        
	        
	        
	        
	        
	        
	        
		    FileOutputStream fos1 = new FileOutputStream("C:\\Users\\supellak\\Desktop\\message_text.txt");       
            
            String str1=projectId;
       	 
	        byte b1[] = str1.getBytes();
	 
	        for(int i=0;i<b1.length;i++)
	        {
	            fos1.write(b1[i]);
	        }
	         
	        fos1.close();                      
			

	        
	        
	        
	        
	        
	        
		 
         }
         catch(Exception e){
        	 
        	 out=e.toString();
        	 
         }
		 
		 
	        return Response
	                .status(200)
	                .header("Access-Control-Allow-Origin", "*")
	                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
	                .header("Access-Control-Allow-Credentials", "true")
	                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
	                .header("Access-Control-Max-Age", "1209600")
	                .entity(out)
	                .build();
	    }
	 
	 

	 
	 
}
