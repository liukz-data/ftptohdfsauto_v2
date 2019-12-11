package cn.hbwy.ftptohdfs.ParseXml;


import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 解析XML
 *
 * @author 刘科志 2018年09月27日
 */

public class ParseXml {
    private static String partition_time;

    /**
     * @return XML字符串
     * @throws Exception
     */
    public static String readXml(File xmlFile) throws Exception {

        //File f = new File(inPath);
        File f = xmlFile;
        FileInputStream in = new FileInputStream(f);
        byte[] bt = new byte[(int) f.length()];
        String xmlStr = new String();
        while ((in.read(bt)) != -1) {
            xmlStr = xmlStr + new String(bt);
            //System.out.print(xmlStr);
        }
        in.close();
        return xmlStr;
    }

    /**
     * @throws Exception
     */
    public static String pasrseXml(String xmlStr) throws Exception {
        ArrayList arrays = new ArrayList<String>();
        Document document = DocumentHelper.parseText(xmlStr);
        //获取根节点元素
        Element xml = document.getRootElement();
        System.out.println("-----------:" + xml.getName());
        //获取xml子节点元素，得到FILE_STRUCTURE节点
        Element file_structure = xml.element("FILE_STRUCTURE");
        //获取FILE_STRUCTURE子节点元素，得到所有Field节点，循环遍历，得到数据
        List<Element> fields = file_structure.elements();
        StringBuffer strBuf = new StringBuffer();
        //for(Element field:fields){
        for (int i = 0; i < fields.size(); i++) {
            //String fieldNo = field.elementText("FieldNo");
            String fieldName = fields.get(i).elementText("FieldName");
            String fieldType = fields.get(i).elementText("FieldType");
            //String fieldNameOther = field.elementText("FieldNameOther");
            //String versionname=field.elementText("VersionName");
            //因为字段类型前有SQL_前缀
            fieldType = fieldType.substring(4);
            if (fieldType.equals("INTEGER") || fieldType.equals("SERIAL") || fieldType.equals("BIGINT")) {
                fieldType = "int";
            } else if (fieldType.equals("VARCHAR(255)") || fieldType.equals("TIMESTAMP(0)")) {
                fieldType = "string";
            } else if (fieldType.equals("FLOAT")) {
                fieldType = "float";
            }
            if (i == fields.size() - 1) {
                strBuf.append(fieldName.toLowerCase() + " " + fieldType);
                return strBuf.toString();
            }
            strBuf.append(fieldName.toLowerCase() + " " + fieldType + ",");
        }
        return strBuf.toString();
    }

    public static String getPartition_time() {
        return partition_time;
    }

    public static void setPartition_time(String partition_time) {
        ParseXml.partition_time = partition_time;
    }

}
