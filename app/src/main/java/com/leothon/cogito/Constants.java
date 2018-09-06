package com.leothon.cogito;

/*
* created by leothon on 2018.7.22
* */
public class Constants {

    /*
    * 此类存储全局使用常量及变量等*/

    public static long onlineTime = 0;//在线时长统计
    public static int loginStatus = 0;//登录状态，0表示未登录，1表示已登录

    //设置是否刷新首页五个fragment，若有相关数据产生，需要刷新显示，则设置为true
    public static boolean isRefreshtruehomeFragment = false;
    public static boolean isRefreshtruetypeFragment = false;
    public static boolean isRefreshtrueaskFragment = false;
    public static boolean isRefreshtruebagFragment = false;
    public static boolean isRefreshtrueaboutFragment = false;

    public static final String iconurl = "http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Fpic.58pic.com%2F58pic%2F13%2F28%2F36%2F62f58PICRBq_1024.jpg&thumburl=http%3A%2F%2Fimg1.imgtn.bdimg.com%2Fit%2Fu%3D651319201%2C4278459580%26fm%3D26%26gp%3D0.jpg";

    public static String[] teacherNameList = {"晁浩建","金铁霖","蒋大为","李谷一","王秀芬","孟玲","杨阳","黄华丽","王宏伟","腾格尔","查看更多"};
    public static int[] teacherIconList = {R.drawable.chaohaojian,R.drawable.jintielin,R.drawable.jiangdawei,R.drawable.liguyi,R.drawable.wangxiufen,R.drawable.mengling,R.drawable.yangyang,R.drawable.huanghuali,R.drawable.wanghongwei,R.drawable.tenger,R.drawable.baseline_keyboard_arrow_right_black_48};//本地的各位大佬的照片

    public static String[] teacherDescription = {"旅居阿根廷男高音歌唱家晁浩建先生，现任中国人民解放军艺术学院特聘教授。曾主演法国歌剧《浮士德》中男主角--浮士德，得到了专家及同行们的高度评价。在新疆多次举办独唱音乐会，并在全国、全军声乐大赛中获奖。",
            "金铁霖，1940年出生于哈尔滨，满族。中国著名歌唱家，声乐教育家。 历任中国音乐家协会副主席、中国音乐学院院长、教授、硕士生导师，中国民族声乐学会副会长。 在教学中培养出许多优秀的歌唱家和声乐演员。其学生在全国性的声乐比赛中获奖70余人次，获省市级比赛奖者若干。现任中国传媒大学音乐与录音艺术学院名誉院长，推出了金氏唱法。",
            "蒋大为，1947年1月22日生于天津市和平区，中国男高音歌唱家。国家一级演员，国务院特殊津贴获得者，中国音乐家协会会员。1978年，蒋大为去军队慰问演出，演唱了自创的《骏马奔驰保边疆》。1980年，蒋大为凭一曲《红牡丹》成名。 1986年，春节联欢晚会演唱《在那桃花盛开的地方》，被观众熟视 。1989年，获得第一届中国金唱片奖，2009年，“2009中非工业合作发展论坛”上获得中非艺术家称号，被授与民委突出贡献专家。入选人民网评选的“人民喜爱的60位艺术家”，由他作曲并演唱的《说中国》 《和谐家园》两首歌入选中宣部100首爱国歌曲大家唱曲目",
            "李谷一，1944年11月10日出生于云南省昆明市，祖籍湖南长沙，中国内地女歌手、国家一级演员，毕业于湖南师范大学。1961年，李谷一被选入湖南省花鼓戏剧院成为演员，从此开始了她的舞台生涯。1964年，主演的花鼓戏电影《补锅》.1974年，李谷一调入中央乐团成为独唱演员。1980年，凭借演唱歌曲《乡恋》受到更多关注。1982年，从中央乐团抽调出来，着手创建中国轻音乐团的筹备工作。1983年，携其歌曲《乡恋》亮相央视“春节联欢晚会”。1984年至1985年，参加了音乐舞蹈史诗《中国革命者之歌》的演出和电影拍摄工作，获表演一等奖。1988年，李谷一被列入美国传记学会编撰的《世界杰出名人录》。1996年，调入东方歌舞团，担任党委书记、第一副团长 。1999年，获得”中国中央电视台与美国MTV电视台“终身成就奖。2005年，李谷一在美国纽约、波士顿、费城等地举办了巡回演唱会。2012年，在北京国家会议中心举办了“从艺50年“个人演唱会  。2014年，作为“特邀老师”参加了浙江卫视音乐励志类节目《我爱好声音》。2016年，李谷一与彭宇、马可合作推出歌曲《唠嗑歌，歌曲在上线后，两度成为微博热门话题。2018年，李谷一与霍尊合作推出歌曲《一念花开》",
            "王秀芬，1954年出生于河北石家庄，女高音歌唱家，毕业于中央音乐学院声歌系，文学硕士，首批国家一级演员，享受国务院颁发的政府特殊津贴。",
            "孟玲，著名声乐教育家，解放军艺术学院硕士研究生导师，教授。1959年考入中央音乐学院，师从著名声乐教育家郭淑珍老师，毕业后先后在西藏歌舞团、成都军区战旗歌舞团工作，后调入解放军艺术学院从事声乐教育工作，为国家，为部队培养了许多知名歌唱家----王宏伟、刘和刚、哈辉、汤灿等一批活跃在舞台上的青年歌唱家都是孟玲老师的学生。正面接触孟老师，发现她是一位非常Fashion的老师，个性开朗，胸怀广阔，坦坦荡荡，没有世人认为的那种音乐界的“陋习”，和孟老师谈话，是一件非常舒心，非常享受的事情。",
            "杨阳，首都师范大学声乐教授、硕士生导师、中央歌剧院特聘艺术家，入选中央电视台“中国十大男高音”。",
            "黄丽华，复旦大学教授，博士生导师。女，1965年出生于浙江省海盐县。现任复旦大学管理学院总支书记，信息管理与信息系统教授，复旦大学电子商务研究中心主任，复旦大学妇女委员会副主任，上海市信息化专家委员会成员，国家教育部学风建设委员会委员，兼任香港大学校外兼职教授、上海市信息化委、外经贸委、建委等顾问，以及多家大型企业的专业顾问，国际信息系统协会华语分会（AIS,Chinese-Speaking Chapter）理事会主任以及AIS中国分会副主席。先后荣获教育部“跨世纪人才”、上海市巾帼建工个人标兵、“863计划”自动化领域CIMS先进工作者、上海市三八红旗手、上海市女职工标兵、上海市教学成果二、三等奖、上海市“三八红旗手标兵”、全国“三八红旗手”。",
            "王宏伟，1971年9月26日出生于中国新疆新疆博尔塔拉蒙古自治州温泉县，中国内地男高音歌唱演员，国家一级演员，享受国务院“特殊政府津贴”。第九届、第十届全国青联常委，中国文艺志愿者协会理事，新疆生产建设兵团青联副主席   。 1994年发行首张个人专辑《黄河遥遥》；1996年参加中央电视台\"第七届全国青年歌手电视大奖赛\"，获专业组民族唱法优秀奖；2000年3月参加全国广播电视新歌手评选，获得金奖及全国电视金鹰奖；2001年首次参加中央电视台\"春节联欢晚会\"独唱《西部放歌》；同年，歌曲《西部放歌》、《家乡的明月边关的雪》获第八届“五个一工程奖”   ；2002年在新疆人民会堂举办首场个人独唱音乐会；2003年凭借专辑《西部三部曲》获得第四届中国金唱片奖专辑奖和男演员奖 ；2004年获第六届《CCTV—MTV音乐盛典》内地最佳民歌男歌手奖  ；2012年登上国家大剧院的舞台，在原创民族歌剧《运河谣》里饰演男一号秦啸生；2013年他第九次登上央视春晚的舞台  ；2016年出演国家大剧院原创歌剧《长征》，饰演“红军战士平伢子”。 2014年被北京文化艺术活动中心、北京市文化志愿者服务中心聘为首批“首都群众文化专家志愿者”   ；2015年由中国文艺志愿者协会推荐，中央金融团工委、全国金融青联和中国青少年发展基金会联合授予他“积分圆梦”公益行动爱心大使",
            "腾格尔，1960年生于内蒙古鄂托克旗。中国国家一级演员 。中国音乐家协会会员。集歌唱、影视和作曲的三栖艺术家 。1989年夺得流行歌曲优秀歌手选拔赛冠军，1986年为歌曲《蒙古人》谱曲并演唱，一举成名  。同年推出第一张个人专辑《你和太阳一同升起》。1988年起自己创作歌曲 。1989年在由文化部主办的全国流行歌曲优秀歌手选拔赛上获十佳第一名 。1992年应邀赴台北举行个人演唱会，在海峡两岸引起轰动，成为建国以来内地到台湾举行个人演唱会的第一位歌手 。1993年3月组建苍狼乐队，任队长兼主唱 。1994年在电影《黑骏马》担任该片的全部音乐创作和主唱，并获第19届蒙特利尔国际电影节获最佳音乐艺术奖  。2001年6月，由中华人民共和国国务院颁发给政府特殊津贴并获得证书。2002年4月，被聘为首批“首都大学生绿色形象大使”及“爱心大使”并颁发证书  。2004年腾格尔荣获“五一劳动奖章”。 2017年7月6日，蒙古国总统额勒贝格道尔吉签署命令，授予腾格尔“北极星”勋章   。"};

    public static boolean isMobilenet = false;

    public static String rechargeCount = "0";
}
