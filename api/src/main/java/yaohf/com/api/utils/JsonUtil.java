package yaohf.com.api.utils;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by viqgd on 2017/1/14.
 */

public class JsonUtil {

    public enum JSON_TYPE{
        /**JSONObject*/
        JSON_TYPE_OBJECT,
        /**JSONArray*/
        JSON_TYPE_ARRAY,
        /**不是JSON格式的字符串*/
        JSON_TYPE_ERROR
    }

    /**
     * 判断是否为json类型
     * @param json
     * @return
     */
    public static boolean isJsonType(String json)
    {
        if(TextUtils.isEmpty(json)){
            return false;
        }

        final char[] strChar = json.substring(0, 1).toCharArray();
        final char firstChar = strChar[0];

        L.v("getJSONType firstChar = "+firstChar);

        if(firstChar == '{'){
            return true;
        }else if(firstChar == '['){
            return true;
        }else{
            return false;
        }
    }
    /***
     *
     * 获取JSON类型
     *         判断规则
     *             判断第一个字母是否为{或[ 如果都不是则不是一个JSON格式的文本
     *
     * @param str
     * @return
     */
    public static JSON_TYPE getJSONType(String str){
        if(TextUtils.isEmpty(str)){
            return JSON_TYPE.JSON_TYPE_ERROR;
        }

        final char[] strChar = str.substring(0, 1).toCharArray();
        final char firstChar = strChar[0];

        L.v("getJSONType firstChar = "+firstChar);

        if(firstChar == '{'){
            return JSON_TYPE.JSON_TYPE_OBJECT;
        }else if(firstChar == '['){
            return JSON_TYPE.JSON_TYPE_ARRAY;
        }else{
            return JSON_TYPE.JSON_TYPE_ERROR;
        }
    }

    /**
     * map 数据拼接json 字符
     * @param maps
     * @return
     */
    public static String getJsonStrs(Map<String,String> maps)
    {
        if(maps.size() == 0)
            return null;
       Iterator<Map.Entry<String,String>> iter =  maps.entrySet().iterator();
        JSONObject obj = new JSONObject();

        while(iter.hasNext())
        {
            Map.Entry<String,String> entry = iter.next();
            try {
                obj.put(entry.getKey(),entry.getValue());
                obj.put(entry.getKey(),entry.getValue());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return obj.toString();
    }
}
