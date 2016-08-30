package com.llc.android_coolview.kobe.bean;

import java.util.HashMap;

public class Constant {

    public static final String getStationNameDataSet = "getStationNameDataSet";
    public static final String getStationName = "getStationName";
    public static final String getStationAndTimeDataSetByTrainCode = "getStationAndTimeDataSetByTrainCode";
    public static final String getStationAndTimeByTrainCode = "getStationAndTimeByTrainCode";
    public static final String getStationAndTimeByStationName = "getStationAndTimeByStationName";
    public static final String getDetailInfoByTrainCode = "getDetailInfoByTrainCode";

    public static final String key = "1da9cfab1ab78c93ffac0d1229b77c57";


    public static HashMap<String, String> HOLIDAYS = new HashMap<String, String>();

    static {
        HOLIDAYS.put("2015-1-1", "元旦");
        HOLIDAYS.put("2015-1-30", "除夕");
        HOLIDAYS.put("2015-1-31", "春节");
        HOLIDAYS.put("2015-2-14", "元宵节");
        HOLIDAYS.put("2015-3-8", "妇女节");
        HOLIDAYS.put("2015-4-1", "愚人节");
        HOLIDAYS.put("2015-4-5", "清明节");
        HOLIDAYS.put("2015-5-1", "劳动节");
        HOLIDAYS.put("2015-6-15", "端午节");
        HOLIDAYS.put("2015-8-2", "七夕");
        HOLIDAYS.put("2015-9-10", "教师节");
        HOLIDAYS.put("2015-9-27", "中秋节");
        HOLIDAYS.put("2015-10-1", "国庆节");
        HOLIDAYS.put("2015-10-2", "重阳节");
        HOLIDAYS.put("2015-11-11", "光棍节");
        HOLIDAYS.put("2015-12-24", "平安夜");
        HOLIDAYS.put("2015-12-25", "圣诞节");
    }

    public static String[] hotCitys = new String[]{
            "北京", "上海", "天津",
            "重庆", "长沙", "长春",
            "成都", "福州", "广州",
            "贵阳", "呼和浩特", "哈尔滨",
            "合肥", "杭州", "海口",
            "济南", "昆明", "拉萨",
            "兰州", "南宁", "南京",
            "南昌", "沈阳", "石家庄",
            "太原", "武汉", "西安",
            "银川", "郑州", "正定机场"
    };

    public static String[] cityList = new String[]{
            "定位", "北京市", "天津市",
            "上海市", "重庆市", "沈阳市",
            "大连市", "长春市", "哈尔滨市",
            "郑州市", "武汉市", "长沙市",
            "广州", "深圳市", "南京市",
            "故宫博物院", "东方明珠塔", "光果树瀑布",
            "黄山风景区", "庐山风景区", "清明上河圆",
            "布达拉宫", "秦始皇陵", "云冈石窟",
            "镜泊湖", "桃花源", "黄鹤楼",
            "丽江古城", "乐山大佛", "南京夫子庙"
    };

    public static String[] scenicList = new String[]{
            "故宫博物院", "东方明珠塔", "光果树瀑布",
            "黄山风景区", "庐山风景区", "清明上河圆",
            "布达拉宫", "秦始皇陵", "云冈石窟",
            "镜泊湖", "桃花源", "黄鹤楼",
            "丽江古城", "乐山大佛", "南京夫子庙",
    };

    public static final String SINA_SPORTS_NEWS = "http://api.sina.cn/sinago/list.json?chwm=14010_0001&s=20&MAC=f52ec027d9ea51d171b1bcd7bfc4793b&wm=b207&platfrom_version=5.0.2&user_uid=2678025011&loading_ad_timestamp=0&from=6048295012&AndroidID=8e11aa312192702fd0528bf082d19feb&IMEI=a7bd9c5fdaf4f774ae0ce2ff5044b75d&v=1&connection_type=2&imei=863121025827452&p=1&uid=c71ce8926941fb89&channel=news_sports";
}
