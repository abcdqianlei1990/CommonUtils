package com.channey.utils

import android.text.TextUtils
import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.net.URLEncoder
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Created by channey on 2017/8/1.
 */
object StringUtils {
    /**
     * 131 6281 9768 ==> 131 **** 9768
     * @param number
     * *
     * @return
     */
    fun phoneNumberParseWithStars(number: String): String {
        val sb = StringBuilder()
        if (!TextUtils.isEmpty(number) && number.length > 6) {
            for (i in 0..number.length - 1) {
                val c = number[i]
                if (i >= 3 && i <= 6) {
                    sb.append('*')
                } else {
                    sb.append(c)
                }
            }
        }
        return sb.toString()
    }

    /**
     * 隐藏身份证号码，只显示首位和末位
     * @param number
     * *
     * @return
     */
    fun cardNumberParseWithStars(number: String): String {
        val sb = StringBuilder()
        val len = number.length
        for (i in 0..len - 1) {
            if (i > 0 && i < len - 1) {
                sb.append('*')
            } else {
                sb.append(number[i])
            }
        }
        return sb.toString()
    }

    fun phoneNumberValidate(number: String, rule: String): Boolean {
        val p = Pattern.compile(rule)
        val m = p.matcher(number)
        return m.matches()
    }

    /**
     * 1.6-15位
     * 2.数字、字母、符号中的至少两种
     * @param pwd
     * *
     * @return
     */
    fun passwordValidate(pwd: String): Boolean {
        val len = pwd.length
        var number = 0
        var character = 0
        var symbol = 0
        var count = 0
        if (len < 6 || len > 15) {
            return false
        }
        val array = pwd.toCharArray()
        for (ch in array) {
            if (ch.toInt() >= 48 && ch.toInt() <= 57) {
                number++
            } else if (ch.toInt() >= 65 && ch.toInt() <= 90) {
                character++
            } else if (ch.toInt() >= 97 && ch.toInt() <= 122) {
                character++
            } else {
                symbol++
            }
        }
        if (number == 0) {
            count++
        }
        if (character == 0) {
            count++
        }
        if (symbol == 0) {
            count++
        }
        if (count >= 2) {
            return false
        }
        return true
    }

    fun deleteSpaces(s: String): String {
        return s.replace(" ", "")
    }


    fun getValueEncoded(value: String?): String {
        if (value == null) return "null"
        val newValue = value.replace("\n", "")
        var i = 0
        val length = newValue.length
        while (i < length) {
            val c = newValue[i]
            if (c <= '\u001f' || c >= '\u007f') {
                try {
                    return URLEncoder.encode(newValue, "UTF-8")
                } catch (e: UnsupportedEncodingException) {
                    e.printStackTrace()
                }

            }
            i++
        }
        return newValue
    }

    fun getValueDecoded(value: String): String {
        try {
            return URLDecoder.decode(value, "UTF-8")
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }

        return ""
    }

    /**
     * 保留小数点后几位,默认保留2位,最多保留8位
     * @param value 待处理的值
     * *
     * @param count 想要保留的小数点后面的位数
     * *
     * @return
     */
    fun doubleFormat(value: Double, count: Int): String {
        val sb = StringBuilder("###.")
        for(i in 1..count){
            sb.append("#")
        }
        val df = DecimalFormat(sb.toString())
        var s = df.format(value)
        if (s.startsWith(".")) {  //0.35 format后字符串为".35"，为了处理该种情况
            s = "0" + s
        }
        return s
    }

    fun isEmpty(str: String?): Boolean {
        if (str == null) {
            return true
        }
        val temp = str.replace(" ", "")
        if (temp.length == 0 || "null" == temp || "NULL" == temp) {
            return true
        } else {
            return false
        }
    }

    /**
     * 将银行卡号后12位以空格隔开，并且除了最后4位，都以*号代替
     * @param s
     * *
     * @return
     */
    fun cardNumberFormat(s: String): String {
        //反转字符串
        val newStr = StringBuilder(s).reverse().toString()
        val sb = StringBuilder()
        val array = newStr.toCharArray()
        var count = 0//空格数量
        for (i in array.indices) {
            if (i > 3) {
                sb.append("*")
            } else {
                sb.append(array[i])
            }
            if (i > 0 && i % 4 == 0 && i < 16) {
                sb.insert(i + count, " ")
                count++
            }
        }
        return sb.reverse().toString()
    }


    private val cityCode = arrayOf("11", "12", "13", "14", "15", "21", "22", "23", "31", "32", "33", "34", "35", "36", "37", "41", "42", "43", "44", "45", "46", "50", "51", "52", "53", "54", "61", "62", "63", "64", "65", "71", "81", "82", "91")

    // 每位加权因子
    private val power = intArrayOf(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2)

    // 第18位校检码
    private val verifyCode = arrayOf("1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2")

    /**
     * 验证15位身份证的合法性,该方法验证不准确，最好是将15转为18位后再判断，该类中已提供。
     */
    fun isValidate15Idcard(idcard: String): Boolean {
        // 非15位为假
        if (idcard.length != 15) {
            return false
        }

        // 是否全都为数字
        if (isDigital(idcard)) {
            val provinceid = idcard.substring(0, 2)
            val birthday = idcard.substring(6, 12)
            val year = Integer.parseInt(idcard.substring(6, 8))
            val month = Integer.parseInt(idcard.substring(8, 10))
            val day = Integer.parseInt(idcard.substring(10, 12))

            // 判断是否为合法的省份
            var flag = false
            for (id in cityCode) {
                if (id == provinceid) {
                    flag = true
                    break
                }
            }
            if (!flag) {
                return false
            }
            // 该身份证生出日期在当前日期之后时为假
            var birthdate: Date? = null
            try {
                birthdate = SimpleDateFormat("yyMMdd").parse(birthday)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            if (birthdate == null || Date().before(birthdate)) {
                return false
            }

            // 判断是否为合法的年份
            val curDay = GregorianCalendar()
            val curYear = curDay.get(Calendar.YEAR)
            val year2bit = Integer.parseInt(curYear.toString()
                    .substring(2))

            // 判断该年份的两位表示法，小于50的和大于当前年份的，为假
            if (year < 50 && year > year2bit) {
                return false
            }

            // 判断是否为合法的月份
            if (month < 1 || month > 12) {
                return false
            }

            // 判断是否为合法的日期
            var mflag = false
            curDay.time = birthdate // 将该身份证的出生日期赋于对象curDay
            when (month) {
                1, 3, 5, 7, 8, 10, 12 -> mflag = day >= 1 && day <= 31
                2 // 公历的2月非闰年有28天,闰年的2月是29天。
                -> if (curDay.isLeapYear(curDay.get(Calendar.YEAR))) {
                    mflag = day >= 1 && day <= 29
                } else {
                    mflag = day >= 1 && day <= 28
                }
                4, 6, 9, 11 -> mflag = day >= 1 && day <= 30
            }
            if (!mflag) {
                return false
            }
        } else {
            return false
        }
        return true
    }

    /**
     * 15位和18位身份证号码的基本数字和位数验校
     */
    fun isIdcard(idcard: String?): Boolean {
        return if (idcard == null || "" == idcard)
            false
        else
            Pattern.matches(
                    "(^\\d{15}$)|(\\d{17}(?:\\d|x|X)$)", idcard)
    }

    /**
     * 15位身份证号码的基本数字和位数验校
     */
    fun is15Idcard(idcard: String?): Boolean {
        return if (idcard == null || "" == idcard)
            false
        else
            Pattern.matches(
                    "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$",
                    idcard)
    }

    /**
     * 18位身份证号码的基本数字和位数验校
     */
    fun is18Idcard(idcard: String): Boolean {
        return Pattern
                .matches(
                        "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([\\d|x|X]{1})$",
                        idcard)
    }

    /**
     * 数字验证
     */
    fun isDigital(str: String?): Boolean {
        return if (str == null || "" == str) false else str.matches("^[0-9]*$".toRegex())
    }

    /**
     * 将身份证的每位和对应位的加权因子相乘之后，再得到和值
     */
    fun getPowerSum(bit: IntArray): Int {

        var sum = 0

        if (power.size != bit.size) {
            return sum
        }

        for (i in bit.indices) {
            for (j in power.indices) {
                if (i == j) {
                    sum = sum + bit[i] * power[j]
                }
            }
        }
        return sum
    }


    /**
     * 将字符数组转为整型数组
     */
    @Throws(NumberFormatException::class)
    fun converCharToInt(c: CharArray): IntArray {
        val a = IntArray(c.size)
        var k = 0
        for (temp in c) {
            a[k++] = Integer.parseInt(temp.toString())
        }
        return a
    }


    // 省份
    private val province: String? = null
    // 城市
    private val city: String? = null
    // 区县
    private val region: String? = null
    // 年份
    private val year: Int = 0
    // 月份
    private val month: Int = 0
    // 日期
    private val day: Int = 0
    // 性别
    private val gender: String? = null
    // 出生日期
    private val birthday: Date? = null

    private val cityCodeMap = object : HashMap<String, String>() {
        init {
            this.put("11", "北京")
            this.put("12", "天津")
            this.put("13", "河北")
            this.put("14", "山西")
            this.put("15", "内蒙古")
            this.put("21", "辽宁")
            this.put("22", "吉林")
            this.put("23", "黑龙江")
            this.put("31", "上海")
            this.put("32", "江苏")
            this.put("33", "浙江")
            this.put("34", "安徽")
            this.put("35", "福建")
            this.put("36", "江西")
            this.put("37", "山东")
            this.put("41", "河南")
            this.put("42", "湖北")
            this.put("43", "湖南")
            this.put("44", "广东")
            this.put("45", "广西")
            this.put("46", "海南")
            this.put("50", "重庆")
            this.put("51", "四川")
            this.put("52", "贵州")
            this.put("53", "云南")
            this.put("54", "西藏")
            this.put("61", "陕西")
            this.put("62", "甘肃")
            this.put("63", "青海")
            this.put("64", "宁夏")
            this.put("65", "新疆")
            this.put("71", "台湾")
            this.put("81", "香港")
            this.put("82", "澳门")
            this.put("91", "国外")
        }
    }

    /**
     * 验证邮箱格式
     * @param mail
     * *
     * @return
     */
    fun emailValidate(mail: String): Boolean {
        //        Pattern pattern = Pattern.compile(Constants.ValidationRules.email);
        //        Matcher matcher = pattern.matcher(mail);
        if (mail.contains("@") && mail.contains(".")) {
            return true
        }
        return false
    }

    fun getNumbers(str: String): String {
        val sb = StringBuilder()
        val chars = str.toCharArray()
        for (i in chars.indices) {
            if (chars[i].toInt() > 47 && chars[i].toInt() < 58) {
                sb.append(chars[i])
            } else {
                continue
            }
        }
        val s = sb.toString()
        if (TextUtils.isEmpty(s)) {
            return str
        }
        return s
    }

    fun urlDecode(string: String): String {
        var result = ""
        try {
            result = URLDecoder.decode(string, "UTF-8")
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }

        return result
    }

    /**
     * 固定电话校验规则：
     * 1.区号3／4位
     * 2.号码7/8位
     * 3.号码不能有连续的6位数字
     * @param areaCode
     * *
     * @param number
     * *
     * @return
     */
    fun telephoneNumberValidate(areaCode: String, number: String): Boolean {
        if (areaCode.length < 3) {
            //            showToast("区号不得少于3位");
            return false
        }
        if (number.length < 7) {
            //            showToast("号码不得少于7位");
            return false
        }
        //        if((areaCode.length() == 3 && number.length() != 8) ||  (areaCode.length() == 4 && number.length() != 7)){
        ////            showToast("固定电话格式不正确");
        //            return false;
        //        }
        //        if(!areaCode.startsWith("0")){
        //            return false;
        //        }

        val temp = deleteSpaces(number)
        var p: Pattern? = null
        var m: Matcher? = null
        p = Pattern.compile("(?:0(?=1)|1(?=2)|2(?=3)|3(?=4)|4(?=5)|5(?=6)|6(?=7)|7(?=8)|8(?=9)|9(?=0))+\\d")
        m = p!!.matcher(temp)
        var tmp = false    //如果匹配到连续6位数字，则位true，否则false
        //判断匹配的字符串长度
        while (m!!.find()) {
            if (m.group().length >= 6) {
                tmp = true
                break
            } else {
                continue
            }
        }
        if (tmp) {
            return false
        }
        return true
    }

    /**
     * MD5加密字符串
     * @param string
     * *
     * @return
     */
    fun getMD5(string: String): String {
        var md5: MessageDigest? = null
        var m: ByteArray? = null
        try {
            md5 = MessageDigest.getInstance("MD5")
            m = md5!!.digest()//加密
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        return getString(m)
    }

    private fun getString(b: ByteArray?): String {
        if (b == null) {
            return ""
        }
        val sb = StringBuffer()
        for (i in b.indices) {
            sb.append(b[i].toInt())
        }
        return sb.toString()
    }


    /**
     * 生成随机字符串
     * @param length 生成字符串的位数
     * *
     * @return
     */
    fun getRandomString(length: Int): String {
        val base = "abcdefghijklmnopqrstuvwxyz0123456789"
        val random = Random()
        val sb = StringBuffer()
        for (i in 0..length - 1) {
            val number = random.nextInt(base.length)
            sb.append(base[number])
        }
        return sb.toString()

    }

    /**
     * 后端返回数据如：************1234  ，  ***********1234 （始终显示最后4位，前面显示*号）
     * 处理后的数据为：**** **** **** 1234 ，*** **** **** 1234
     */
    fun formatBankCardNumber(string: String):String{
        //将传入的字符串除了后4位都转为*
        var sb1 = StringBuilder()
        var str = string.substring(string.length-4,string.length)
        for (i in 0..(string.length-4-1)){
            sb1.append("*")
        }
        sb1.append(str)

        //增加空格 （每四位增加一个空格）
        var number = StringBuilder(sb1.toString()).reverse().toString()
        var temp = number.length / 4
        var sb = StringBuffer()
        var tempSb = StringBuffer() //存储结果数据
        if (number.length % 4 == 0){
            for (i in 0..temp-1){
                var s = number.substring(i * 4, sb.length + 4)
                sb.append(s)
                tempSb.append(s)
                if (i != temp-1){
                    tempSb.append(" ")
                }
            }
        }else{
            for (i in 0..temp){
                var s = ""
                if (i == temp){
                    s = number.substring(sb.length, number.length)
                    sb.append(s)
                    tempSb.append(s)
                }else{
                    s = number.substring(i * 4, sb.length + 4)
                    sb.append(s)
                    tempSb.append(s)
                }
                if (i != temp){
                    tempSb.append(" ")
                }
            }
        }
        return tempSb.reverse().toString()
    }
}