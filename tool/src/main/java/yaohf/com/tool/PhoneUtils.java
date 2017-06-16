package yaohf.com.tool;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneUtils {
	 /** 座机电话格式验证 **/  
    private static final String PHONE_CALL_PATTERN = "^(\\(\\d{3,4}\\)|\\d{3,4}-)?\\d{7,8}(-\\d{1,4})?$";  
  
    /** 
     * 中国电信号码格式验证 手机段： 133,153,180,181,189,177,1700,173 
     * **/  
    private static final String CHINA_TELECOM_PATTERN = "(^1(33|53|7[37]|8[019])\\d{8}$)|(^1700\\d{7}$)";  
  
    /** 
     * 中国联通号码格式验证 手机段：130,131,132,155,156,185,186,145,176,1707,1708,1709,175 
     * **/  
    private static final String CHINA_UNICOM_PATTERN = "(^1(3[0-2]|4[5]|5[56]|7[56]|8[56])\\d{8}$)|(^170[7-9]\\d{7}$)";  
  
    /** 
     * 中国移动号码格式验证 
     * 手机段：134,135,136,137,138,139,150,151,152,157,158,159,182,183,184 
     * ,187,188,147,178,1705 
     *  
    **/  
    private static final String CHINA_MOBILE_PATTERN = "(^1(3[4-9]|4[7]|5[0-27-9]|7[8]|8[2-478])\\d{8}$)|(^1705\\d{7}$)";  
      
    /** 
     * 仅手机号格式校验 
     */  
    private static final String PHONE_PATTERN=new StringBuilder(300).append(CHINA_MOBILE_PATTERN)  
                                                                    .append("|")  
                                                                    .append(CHINA_TELECOM_PATTERN)  
                                                                    .append("|")  
                                                                    .append(CHINA_UNICOM_PATTERN)  
                                                                    .toString();  
      
    /** 
     * 手机和座机号格式校验 
     */  
    private static final String PHONE_TEL_PATTERN=new StringBuilder(350).append(PHONE_PATTERN)  
                                                                        .append("|")  
                                                                        .append("(")  
                                                                        .append(PHONE_CALL_PATTERN)  
                                                                        .append(")")  
                                                                        .toString();  
      
    /** 
     * 匹配多个号码以,、或空格隔开的格式，如 
     * 17750581369 13306061248、(596)3370653,17750581369,13306061248 (0596)3370653 
     */  
    private static final String MULTI_PHONE_TEL_PATTERN="^(?:(?:(?:(?:(?:(?:13[0-9])|(?:14[57])|(?:15[0-35-9])|(?:17[35-8])|(?:18[0-9]))\\d{8})|(?:170[057-9]\\d{7})|(?:\\(\\d{3,4}\\)|\\d{3,4}-)?\\d{7,8}(?:-\\d{1,4})?)[,\\s、])+)?(?:(?:(?:(?:13[0-9])|(?:14[57])|(?:15[0-35-9])|(?:17[35-8])|(?:18[0-9]))\\d{8})|(?:170[057-9]\\d{7})|(?:\\(\\d{3,4}\\)|\\d{3,4}-)?\\d{7,8}(?:-\\d{1,4})?)$";  
      
      
    /** 
     * 匹配多个号码以,、或空格隔开的格式，如 
     * 17750581369 13306061248、(596)3370653,17750581369,13306061248 (0596)3370653 
     * @param input 
     * @return 
     */  
    public static boolean checkMultiPhone(String input){  
        return match(MULTI_PHONE_TEL_PATTERN, input);  
    }  
      
    /** 
     * 仅手机号码校验 
     * @param input 
     * @return 
     */  
    public static boolean isPhone(String input){  
        return match(PHONE_PATTERN, input);  
    }  
      
    /** 
     * 手机号或座机号校验 
     * @param input 
     * @return 
     */  
    public static boolean isPhoneOrTel(String input){  
        System.out.println(PHONE_TEL_PATTERN);  
        return match(PHONE_TEL_PATTERN, input);  
    }  
      
    /** 
     * 验证电话号码的格式 
     *  
     * @author LinBilin 
     * @param str 
     *            校验电话字符串 
     * @return 返回true,否则为false 
     */  
    public static boolean isPhoneCallNum(String str) {  
        return match(PHONE_CALL_PATTERN, str);  
    }  
  
    /** 
     * 验证【电信】手机号码的格式 
     *  
     * @author LinBilin 
     * @param str 
     *            校验手机字符串 
     * @return 返回true,否则为false 
     */  
    public static boolean isChinaTelecomPhoneNum(String str) {  
        return match(CHINA_TELECOM_PATTERN, str);  
    }  
  
    /** 
     * 验证【联通】手机号码的格式 
     *  
     * @author LinBilin 
     * @param str 
     *            校验手机字符串 
     * @return 返回true,否则为false 
     */  
    public static boolean isChinaUnicomPhoneNum(String str) {  
        return  match(CHINA_UNICOM_PATTERN, str);  
    }  
  
    /** 
     * 验证【移动】手机号码的格式 
     *  
     * @author LinBilin 
     * @param str 
     *            校验手机字符串 
     * @return 返回true,否则为false 
     */  
    public static boolean isChinaMobilePhoneNum(String str) {  
        return  match(CHINA_MOBILE_PATTERN,str);  
    }  
      
  
      
      
    /** 
     * 匹配函数 
     * @param regex 
     * @param input 
     * @return 
     */  
    private static boolean match(String regex, String input) {  
        return Pattern.matches(regex, input);  
    }  
    
    /**
     * 获得电话号码的正则表达式：包括固定电话和移动电话
     * 符合规则的号码：
     *     1》、移动电话
     *         86+‘-’+11位电话号码
     *         86+11位正常的电话号码
     *         11位正常电话号码a
     *         (+86) + 11位电话号码
     *         (86) + 11位电话号码
     *     2》、固定电话
     *         区号 + ‘-’ + 固定电话  + ‘-’ + 分机号
     *         区号 + ‘-’ + 固定电话 
     *         区号 + 固定电话
     * @return    电话号码的正则表达式
     */
    public static String isPhoneRegexp()
    {
        String regexp = "";
        //能满足最长匹配，但无法完成国家区域号和电话号码之间有空格的情况
        String mobilePhoneRegexp = "(?:(\\(\\+?86\\))((13[0-9]{1})|(15[0-9]{1})|(18[0,5-9]{1}))+\\d{8})|" +     
                "(?:86-?((13[0-9]{1})|(15[0-9]{1})|(18[0,5-9]{1}))+\\d{8})|" +
                "(?:((13[0-9]{1})|(15[0-9]{1})|(18[0,5-9]{1}))+\\d{8})";
        
        //固定电话正则表达式
        String landlinePhoneRegexp = "(?:(\\(\\+?86\\))(0[0-9]{2,3}\\-?)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?)|" +
                "(?:(86-?)?(0[0-9]{2,3}\\-?)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?)";    

        regexp += "(?:" + mobilePhoneRegexp + "|" + landlinePhoneRegexp +")"; 
        return regexp;
    }
    
    
    /**
     * 从dataStr中获取出所有的电话号码（固话和移动电话），将其放入Set
     * @param dataStr    待查找的字符串
     * @param phoneSet    dataStr中的电话号码
     */
    public static void getPhoneNumFromStrIntoSet(String dataStr,Set<String> phoneSet)
    {
        //获得固定电话和移动电话的正则表达式
        String regexp = isPhoneRegexp();
        
        Pattern pattern = Pattern.compile(regexp); 
        Matcher matcher = pattern.matcher(dataStr); 

        //找与该模式匹配的输入序列的下一个子序列
        while (matcher.find()) 
        { 
            //获取到之前查找到的字符串，并将其添加入set中
            phoneSet.add(matcher.group());
        } 
    }

}
