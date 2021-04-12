package org.example.helper;

import org.example.App;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ModelGenerator {

    public boolean generateAll(String packageName){

        List<Map<String,Object>> databaseTables = databaseTables();

        if(databaseTables!=null){


            for ( Map<String,Object> row :databaseTables ){

                Scanner myObj = new Scanner(System.in);

                String tableName = (String) row.get("TABLE_NAME");

                System.out.println("Write the model name for "+tableName+" : (write 0 if you want to skip)");
                String modelName = myObj.nextLine();  // Read user input

                if(modelName.compareTo("0")==0){
                    System.out.println("Skip");
                    continue;
                }

                generate(packageName,tableName,modelName);
                System.out.println(modelName+" model is created");

            }
        }

        return true;
    }

    public boolean generate(String packageName, String tableName, String modelName){

        String projetPath = System.getProperty("user.dir") + File.separator + "src"+ File.separator+"sample";
        String modelpath = projetPath + File.separator + packageName;
        String filePath = modelpath + File.separator + modelName + ".java";

        FileHelper fileHelper = new FileHelper();

        if(fileHelper.createFile(filePath,true)){

            String data = fileHelper.readAllFile(projetPath + File.separator + "helper" + File.separator + "ModelClass.txt");

            List<Map<String,Object>> tableColumns = tableColumns(tableName);

            data = data.replace("$_packageName_$",packageName);
            data = data.replace("$_modelName_$",modelName);
            data = data.replace("$_tableName_$",tableName);
            data = data.replace("$_fieldsDeclaration_$", tableFieldsDeclaration(tableColumns,tableName));
            data = data.replace("$_fieldsParams_$",tableFieldsParams(tableColumns,tableName));
            data = data.replace("$_fieldsInit_$",tableFieldsInit(tableColumns,tableName));
            data = data.replace("$_fieldsGettersAndSetters_$",tableFieldsGettersAndSetters(tableColumns,tableName));
            data = data.replace("$_fieldsFromRow_$",tableFieldsFromRow(tableColumns,tableFieldsFromRow(tableColumns,tableName)));
            data = data.replace("$_fieldsToRow_$",tableFieldsToRow(tableColumns,tableName));

            fileHelper.writeToFile(filePath,false,data);

            return true;
        }

        return false;

    }

    public List<Map<String, Object>> databaseTables(){

        List<Map<String,Object>> rows = App.db.executeQuery(
                    "SELECT table_name FROM information_schema.tables\n" +
                        "WHERE table_schema = '"+ App.db.getDatabaseName()+"';"
        );

        return rows;
    }

    public List<Map<String, Object>> tableColumns( String tableName){

        List<Map<String,Object>> rows = App.db.executeQuery(
                "SELECT `COLUMN_NAME`,DATA_TYPE \n" +
                    "FROM `INFORMATION_SCHEMA`.`COLUMNS` \n" +
                    "WHERE `TABLE_SCHEMA`='"+ App.db.getDatabaseName()+"' \n" +
                    "AND `TABLE_NAME`='"+tableName+"' AND COLUMN_NAME != 'id';"
        );

        return rows;
    }

    private String mysqlToJavaDataType(String mysqlDataType){
        String javaDataType = "";

        switch (mysqlDataType.toUpperCase()){
            case"CHAR": javaDataType =	"String"; break;
            case"VARCHAR": javaDataType =	"String"; break;
            case"LONGVARCHAR": javaDataType =	"String"; break;
            case"NUMERIC": javaDataType =	"java.math.BigDecimal"; break;
            case"DECIMAL": javaDataType =	"java.math.BigDecimal"; break;
            case"BIT": javaDataType =	"boolean"; break;
            case"TINYINT": javaDataType =	"byte"; break;
            case"SMALLINT": javaDataType =	"short"; break;
            case"INTEGER": javaDataType =	"int"; break;
            case"BIGINT": javaDataType =	"long"; break;
            case"REAL": javaDataType =	"float"; break;
            case"FLOAT": javaDataType =	"double"; break;
            case"DOUBLE": javaDataType =	"double"; break;
            case"BINARY": javaDataType =	"byte []"; break;
            case"VARBINARY": javaDataType =	"byte []"; break;
            case"LONGVARBINARY": javaDataType =	"byte []"; break;
            case"DATE": javaDataType =	"java.sql.Date"; break;
            case"TIME": javaDataType =	"java.sql.Time"; break;
            case"TIMESTAMP": javaDataType =	"java.sql.Tiimestamp"; break;
            case"INT": javaDataType =	"int"; break;
            case"DATETIME": javaDataType =	"java.sql.Date"; break;
        }

        String type = javaDataType==""?mysqlDataType:javaDataType;

        return type;
    }

    public String tableFieldsDeclaration(List<Map<String, Object>> tableColumns, String tableName){
        String out = "";

        ModelGenerator modelGenerator = new ModelGenerator();

        if(tableColumns!=null){
            for ( Map<String,Object> row :tableColumns ){
                String type = mysqlToJavaDataType((String) row.get("DATA_TYPE"));
                out += "\tprivate "+type+" "+row.get("COLUMN_NAME")+";\n";
            }
        }

        return out;
    }

    public String tableFieldsParams(List<Map<String, Object>> tableColumns, String tableName){
        String out = "";

        ModelGenerator modelGenerator = new ModelGenerator();

        if(tableColumns!=null){
            for ( Map<String,Object> row :tableColumns ){
                String type = mysqlToJavaDataType((String) row.get("DATA_TYPE"));

                out += type+" "+row.get("COLUMN_NAME")+",";

            }

            out = out.substring(0,out.length()-1);

        }

        return out;
    }

    private String tableFieldsInit(List<Map<String, Object>> tableColumns, String tableName) {

        String out = "";

        ModelGenerator modelGenerator = new ModelGenerator();

        if(tableColumns!=null){
            for ( Map<String,Object> row :tableColumns ){

                String type = mysqlToJavaDataType((String) row.get("DATA_TYPE"));

                out += "\t\t"+"this."+row.get("COLUMN_NAME")+" = "+row.get("COLUMN_NAME")+";\n";
            }
        }

        return out;

    }

    private String tableFieldsGettersAndSetters(List<Map<String, Object>> tableColumns, String tableName) {
        String out = "";

        ModelGenerator modelGenerator = new ModelGenerator();

        if(tableColumns!=null){
            for ( Map<String,Object> row :tableColumns ){

                String type = mysqlToJavaDataType((String) row.get("DATA_TYPE"));

                String col = (String) row.get("COLUMN_NAME");
                String col2 = col.substring(0, 1).toUpperCase() + col.substring(1);

                out +=  "\tpublic "+type+" get"+col2+"() {\n" +
                        "\t   return "+col+";\n" +
                        "\t}\n" +
                        "\tpublic void set"+col2+"("+type+" "+col+") {\n" +
                        "\t   this."+col+" = "+col+";\n" +
                        "\t}\n\n\n";

            }
        }

        return out;
    }

    private String tableFieldsFromRow(List<Map<String, Object>> tableColumns, String tableName) {

        String out = "";

        ModelGenerator modelGenerator = new ModelGenerator();

        if(tableColumns!=null){
            for ( Map<String,Object> row :tableColumns ){

                String type = mysqlToJavaDataType((String) row.get("DATA_TYPE"));

                out += "\t\tthis."+row.get("COLUMN_NAME")+" = ("+type+") row.get(\""+row.get("COLUMN_NAME")+"\");\n";
            }
        }

        return out;

    }

    private String tableFieldsToRow(List<Map<String, Object>> tableColumns, String tableName) {

        String out = "";

        ModelGenerator modelGenerator = new ModelGenerator();

        if(tableColumns!=null){
            for ( Map<String,Object> row :tableColumns ){

                String type = mysqlToJavaDataType((String) row.get("DATA_TYPE"));

                out += "\t\trow.put(\""+row.get("COLUMN_NAME")+"\","+row.get("COLUMN_NAME")+");\n";
            }
        }

        return out;

    }

}
