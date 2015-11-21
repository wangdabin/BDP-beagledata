package test;
  
public class GenEntityTool {  
    private String tablename = "petDiary";  
  
    private String[] colnames; // 列名数组  
  
    private String[] colTypes; // 列名类型数组  
  
    private int[] colSizes; // 列名大小数组  
  
    private boolean f_util = false; // 是否需要导入包java.util.*  
  
    private boolean f_sql = false; // 是否需要导入包java.sql.*  
  
//    public GenEntityTool() {  
//        Connection conn = DBSession.getConnection(); // 得到数据库连接  
//        String strsql = "select * from " + tablename;  
//        try {  
//            PreparedStatement pstmt = conn.prepareStatement(strsql);  
//            ResultSetMetaData rsmd = pstmt.getMetaData();  
//            int size = rsmd.getColumnCount(); // 共有多少列  
//            colnames = new String[size];  
//            colTypes = new String[size];  
//            colSizes = new int[size];  
//            for (int i = 0; i < rsmd.getColumnCount(); i++) {  
//                colnames[i] = rsmd.getColumnName(i + 1);  
//                colTypes[i] = rsmd.getColumnTypeName(i + 1);  
//                if (colTypes[i].equalsIgnoreCase("datetime")) {  
//                    f_util = true;  
//                }  
//                if (colTypes[i].equalsIgnoreCase("image")  
//                        || colTypes[i].equalsIgnoreCase("text")) {  
//                    f_sql = true;  
//                }  
//                colSizes[i] = rsmd.getColumnDisplaySize(i + 1);  
//            }  
//            String content = parse(colnames, colTypes, colSizes);  
//            try {  
//                FileWriter fw = new FileWriter(initcap(tablename) + ".java");  
//                PrintWriter pw = new PrintWriter(fw);  
//                pw.println(content);  
//                pw.flush();  
//                pw.close();  
//            } catch (IOException e) {  
//                e.printStackTrace();  
//            }  
//        } catch (SQLException e) {  
//            e.printStackTrace();  
//        } finally {  
//            DBSession.closeConnection(conn);  
//        }  
//    }  
  
    /** 
    * 解析处理(生成实体类主体代码) 
    */  
    private String parse(String[] colNames, String[] colTypes, int[] colSizes) {  
        StringBuffer sb = new StringBuffer();  
        if (f_util) {  
            sb.append("import java.util.Date;\r\n");  
        }  
        if (f_sql) {  
            sb.append("import java.sql.*;\r\n\r\n\r\n");  
        }  
        sb.append("public class " + initcap(tablename) + " {\r\n");  
        processAllAttrs(sb);  
        processAllMethod(sb);  
        sb.append("}\r\n");  
        System.out.println(sb.toString());  
        return sb.toString();  
  
    }  
  
    /** 
    * 生成所有的方法 
    *  
    * @param sb 
    */  
    private void processAllMethod(StringBuffer sb) {  
        for (int i = 0; i < colnames.length; i++) {  
            sb.append("\tpublic void set" + initcap(colnames[i]) + "("  
                    + sqlType2JavaType(colTypes[i]) + " " + colnames[i]  
                    + "){\r\n");  
            sb.append("\t\tthis." + colnames[i] + "=" + colnames[i] + ";\r\n");  
            sb.append("\t}\r\n");  
  
            sb.append("\tpublic " + sqlType2JavaType(colTypes[i]) + " get"  
                    + initcap(colnames[i]) + "(){\r\n");  
            sb.append("\t\treturn " + colnames[i] + ";\r\n");  
            sb.append("\t}\r\n");  
        }  
    }  
  
    /** 
    * 解析输出属性 
    *  
    * @return 
    */  
    private void processAllAttrs(StringBuffer sb) {  
        for (int i = 0; i < colnames.length; i++) {  
            sb.append("\tprivate " + sqlType2JavaType(colTypes[i]) + " "  
                    + colnames[i] + ";\r\n");  
  
        }  
    }  
  
    /** 
    * 把输入字符串的首字母改成大写 
    *  
    * @param str 
    * @return 
    */  
    private String initcap(String str) {  
        char[] ch = str.toCharArray();  
        if (ch[0] >= 'a' && ch[0] <= 'z') {  
            ch[0] = (char) (ch[0] - 32);  
        }  
        return new String(ch);  
    }  
  
    private String sqlType2JavaType(String sqlType) {  
        if (sqlType.equalsIgnoreCase("bit")) {  
            return "bool";  
        } else if (sqlType.equalsIgnoreCase("tinyint")) {  
            return "byte";  
        } else if (sqlType.equalsIgnoreCase("smallint")) {  
            return "short";  
        } else if (sqlType.equalsIgnoreCase("int")) {  
            return "int";  
        } else if (sqlType.equalsIgnoreCase("bigint")) {  
            return "long";  
        } else if (sqlType.equalsIgnoreCase("float")) {  
            return "float";  
        } else if (sqlType.equalsIgnoreCase("decimal")  
                || sqlType.equalsIgnoreCase("numeric")  
                || sqlType.equalsIgnoreCase("real")) {  
            return "double";  
        } else if (sqlType.equalsIgnoreCase("money")  
                || sqlType.equalsIgnoreCase("smallmoney")) {  
            return "double";  
        } else if (sqlType.equalsIgnoreCase("varchar")  
                || sqlType.equalsIgnoreCase("char")  
                || sqlType.equalsIgnoreCase("nvarchar")  
                || sqlType.equalsIgnoreCase("nchar")) {  
            return "String";  
        } else if (sqlType.equalsIgnoreCase("datetime")) {  
            return "Date";  
        }  
  
        else if (sqlType.equalsIgnoreCase("image")) {  
            return "Blob";  
        } else if (sqlType.equalsIgnoreCase("text")) {  
            return "Clob";  
        }  
        return null;  
    }  
  
    public static void main(String[] args) {  
        new GenEntityTool();  
    }  
}  