package org.example;

import java.sql.*;
import java.util.*;

public class Database {

    private String databaseName;
    // database URL
    private String DB_URL; //Connetion string

    //  Database credentials
    private String USER;
    private String PASS;

    private Connection conn;

    public Database(String DB_URL,String databaseName,String USER,String PASS){
        this.databaseName = databaseName;
        this.DB_URL = new String(DB_URL);
        this.PASS = new String(PASS);
        this.USER = new String(USER);
    }

    /*
    @return int
    if the function return
        0 : connction to mysql server is faild
        1 : connection to mysql server is success
        2 : connection to mysql server is success but the database is not exist
     */
    public int checkDatabaseConnection(String dbName){

        Connection con = null;
        ResultSet rs = null;

        try{
            con = DriverManager.getConnection(DB_URL,USER,PASS);

            if(con != null){

                //check if a database exists
                rs = con.getMetaData().getCatalogs();

                while(rs.next()){
                    String catalogs = rs.getString(1);
                    if(dbName.equals(catalogs)){
                        //the database is exists
                        return 1;
                    }
                }

                return 2;
            }

        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        finally{
            if( rs != null){
                try{
                    rs.close();
                }
                catch(SQLException ex){
                    ex.printStackTrace();
                }
            }
            if( con != null){
                try{
                    con.close();
                }
                catch(SQLException ex){
                    ex.printStackTrace();
                }
            }
        }

        return 0;
    }

    public int checkDatabaseConnection(){
        return checkDatabaseConnection(this.databaseName);
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void open() {

        if(conn!=null){
            try {
                if(!conn.isClosed())
                    conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        conn = null;

        try{

            //Open a connection
            conn = DriverManager.getConnection(DB_URL+"/"+databaseName,USER,PASS);

        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }

    public void close() {
        try{
            if(conn!=null && !conn.isClosed())
                conn.close();
        }catch(SQLException se){
            se.printStackTrace();
        }
    }

    public int executeUpdate(String sql,List<Object> parms){

        this.open();

        int row = 0;

        PreparedStatement pstmt = null;

        //STEP 4: Execute a query

        try {

            pstmt = conn.prepareStatement(sql);

            if(parms!=null){
                int p = 1;
                for (Object parm:parms){
                    pstmt.setObject(p++,parm);
                }
            }

            row = pstmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        this.close();

        return row;

    }

    public int executeUpdate(String sql){
        return this.executeUpdate(sql,null);
    }

    public List<Map<String, Object>> executeQuery(String sql,List<Object> parms){

        this.open();

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Map<String, Object>> rows = null;

        try {

            pstmt = conn.prepareStatement(sql);

            if(parms!=null){
                int p = 1;
                for (Object parm:parms){
                    pstmt.setObject(p++,parm);
                }
            }

            System.out.println(pstmt.toString());

            rows = resultSetToList(pstmt.executeQuery());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        this.close();

        return rows;
    }

    public List<Map<String, Object>> executeQuery(String sql){
        return executeQuery(sql,null);
    }

    public Object executeScalar(String sql,List<Object> parms){

        this.open();

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Object obj = null;

        try {

            pstmt = conn.prepareStatement(sql);

            if(parms!=null){
                int p = 1;
                for (Object parm:parms){
                    pstmt.setObject(p++,parm);
                }
            }

            rs = pstmt.executeQuery();
            while (rs.next()){
                obj = rs.getObject(1);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        this.close();

        return obj;
    }

    public Object executeScalar(String sql){
        return this.executeScalar(sql,null);
    }

    private List<Map<String, Object>> resultSetToList(ResultSet rs) throws SQLException {
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();
        List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
        while (rs.next()){
            Map<String, Object> row = new HashMap<String, Object>(columns);
            for(int i = 1; i <= columns; ++i){
                row.put(md.getColumnName(i), rs.getObject(i));
            }
            rows.add(row);
        }
        return rows;
    }

}