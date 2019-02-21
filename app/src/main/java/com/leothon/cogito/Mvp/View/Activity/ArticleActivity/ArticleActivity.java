package com.leothon.cogito.Mvp.View.Activity.ArticleActivity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spanned;
import android.widget.TextView;

import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.BaseModel;
import com.leothon.cogito.Mvp.BasePresenter;
import com.leothon.cogito.R;
import com.leothon.cogito.View.RichEditTextView;
import com.leothon.cogito.handle.CustomHtml;
import com.leothon.cogito.handle.RichEditImageGetter;

import butterknife.BindView;

public class ArticleActivity extends BaseActivity {


    @BindView(R.id.article_content_show)
    RichEditTextView articleContent;

    @Override
    public int initLayout() {
        return R.layout.activity_article;
    }

    @Override
    public void initView() {

        setToolbarSubTitle("");
        setToolbarTitle("");
        String html = "<html data-dpr=\"1\" style=\"font-size: 50px;\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta http-equiv=\"Cache-Control\" content=\"no-transform\">\n" +
                "    <meta http-equiv=\"Cache-Control\" content=\"no-siteapp\">\n" +
                "    <meta name=\"applicable-device\" content=\"mobile\">\n" +
                "    <meta name=\"theme-color\" content=\"#ff3333\">\n" +
                "    <meta name=\"cms_id\" content=\"0040article_ssr_beta\">\n" +
                "    <title>四川一婴儿注射疫苗后次日死亡 官方:可申请赔偿_手机网易网</title>\n" +
                "    <meta name=\"viewport\" content=\"initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no\">\n" +
                "    <meta name=\"keywords\" content=\"疫苗,四川,婴儿,鉴定所,医调委\">\n" +
                "    <meta name=\"description\" content=\"新京报讯四川平昌一婴儿在注射乙脑疫苗后于次日凌晨死亡的消息引发关注。据家属提供的由重庆法医验伤所出具的死亡鉴定意见书，结论为孩子系急性间质性肺炎致急性呼吸、循环衰竭死亡。\">\n" +
                "    <meta property=\"og:title\" content=\"四川一婴儿注射疫苗后次日死亡 官方:可申请赔偿_手机网易网\">\n" +
                "    <meta property=\"og:type\" content=\"news\">\n" +
                "    <meta property=\"article:author\" content=\"新京报\">\n" +
                "    <meta property=\"article:published_time\" content=\"2019-02-21T15:41:26+08:00\">\n" +
                "    <meta property=\"article:tag\" content=\"疫苗,四川,婴儿,鉴定所,医调委\">\n" +
                "    <meta property=\"og:description\" content=\"新京报讯四川平昌一婴儿在注射乙脑疫苗后于次日凌晨死亡的消息引发关注。据家属提供的由重庆法医验伤所出具的死亡鉴定意见书，结论为孩子系急性间质性肺炎致急性呼吸、循环衰竭死亡。\">\n" +
                "    <meta property=\"og:image\" content=\"http://cms-bucket.ws.126.net/2019/02/21/cb78735a5b6c49ea853401b792e1060c.jpg\">\n" +
                "    <!-- 360智能摘要-->\n" +
                "    <meta property=\"og:release_date\" content=\"2019-02-21T15:41:26+08:00\">\n" +
                "    <meta name=\"robots\" content=\"index,follow\">\n" +
                "    <meta name=\"googlebot\" content=\"index,follow\">\n" +
                "<main>\n" +
                "    <!--文章内容-->\n" +
                "    <article id=\"article-E8I4DE6Q0001875P\">\n" +
                "        <div class=\"head\">\n" +
                "            <h1 class=\"title\">四川一婴儿注射疫苗后次日死亡 官方:可申请赔偿</h1>\n" +
                "            <div class=\"info\">\n" +
                "                <span class=\"time js-time\">2019-02-21 15:41:26</span>\n" +
                "                <span class=\"source js-source\">新京报</span>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "        <div class=\"content\">\n" +
                "            <div class=\"page js-page on\">\n" +
                "                <p>新京报讯 四川平昌一婴儿在注射乙脑疫苗后于次日凌晨死亡的消息引发关注。据家属提供的由重庆法医验伤所出具的死亡鉴定意见书，结论为孩子系急性间质性肺炎致急性呼吸、循环衰竭死亡。后四川博宇司法鉴定所再次进行鉴定，结论为不排除孩子过敏反应致急性呼吸、循环衰竭死亡。今日（2月21日）平昌县医疗纠纷人民调解委员会（以下简称“平昌医调委”）工作人员告诉新京报记者，此事对于家属有两种解决方案，一是根据四川省出台的相关补偿办法申请赔偿，二是向法院提起诉讼。</p><div class=\"photo\">\n" +
                "                  <a href=\"http://cms-bucket.ws.126.net/2019/02/21/cb78735a5b6c49ea853401b792e1060c.jpg\">\n" +
                "                      <img src=\"http://cms-bucket.ws.126.net/2019/02/21/cb78735a5b6c49ea853401b792e1060c.jpg\">\n" +
                "                      <span>谢明果生前照片。受访者供图</span>\n" +
                "                  </a>\n" +
                "              </div><p><strong>孩子注射乙脑疫苗 次日凌晨死亡</strong></p><p>谢明果出生于2017年8月19日，其姑妈谢菲告诉新京报记者，谢明果此前身体健康。谢菲提供的接种记录显示，谢明果此前曾多次在元山镇卫生院接种疫苗，未发生过问题。</p><p>2018年5月3日上午，谢明果的父母带着谢明果再次到元山镇卫生院，接种乙脑疫苗。据家属提供的由重庆法医验伤所出具的死亡鉴定意见书，元山镇卫生院副院长陈江表示，谢明果于5月3日上午8时20分入院就诊，9时许接种疫苗。测量体温正常，为36.7℃，注射疫苗后一切正常，之后离院。</p><p>谢明果的父亲谢海林介绍，给孩子注射完疫苗后，当天晚上10时左右家人发现谢明果鼻腔内出血，双眼无神，遂拨打“120”，送至平昌县人民医院抢救，抢救无效，孩子于5月4日凌晨死亡。</p><div class=\"photo\">\n" +
                "                  <a href=\"http://cms-bucket.ws.126.net/2019/02/21/582add7e52f240209c009a8c6de6fbf7.jpeg\">\n" +
                "                      <img src=\"http://cms-bucket.ws.126.net/2019/02/21/582add7e52f240209c009a8c6de6fbf7.jpeg\">\n" +
                "                      <span>四川博宇司法鉴定所的鉴定结果为不排除谢明果过敏反应致急性呼吸、循环衰竭死亡。受访者供图</span>\n" +
                "                  </a>\n" +
                "              </div><p><strong>两次鉴定 有意见称不排除过敏反应</strong></p><p>为明确谢明果死因，平昌县卫计局于2018年5月4日委托重庆法医验伤所做尸体解剖。重庆法医验伤所于2018年7月18日出具了司法鉴定意见书，该意见书显示，谢明果系急性间质性肺炎致急性呼吸、循环衰竭死亡。</p><p>当事家属表示，对上述鉴定结果不予认可。谢菲告诉新京报记者，他们事后去重庆找过儿科专家，对方告知，“几个月的孩子我们不会有这样的诊断结果，急性间质性肺炎更是没有这一说法，至少在我们这个儿童呼吸专科来说没有这样的诊断。”家属又联系到四川博宇司法鉴定所，由平昌医调委出面，于2018年9月28日委托四川博宇司法鉴定所再次鉴定。</p></div><div class=\"page js-page\"><p>四川博宇司法鉴定所在2018年12月24日作出法医学鉴定意见书，该意见书载明，不排除谢明果过敏反应致急性呼吸、循环衰竭死亡。四川博宇司法鉴定所参与鉴定的张雨（化名）告诉新京报记者，“不排除过敏反应”就是指有可能是过敏反应，同时也无法确定过敏源是什么。</p><p>张雨说，他们的鉴定主要根据上一次的鉴定资料以及病理切片，“我们把病理切片放在显微镜下再观察，作出我们的结论”。对于两次鉴定结果不一致，张雨表示“不能说别人就错，我们只是作出我们的判断”。</p><p><strong>平昌医调委：可走司法途径解决</strong></p><p>孩子突然身亡，家属认为与注射疫苗有关。平昌医调委工作人员告诉新京报记者，谢明果所注射的同批次疫苗共有90支，谢明果是第74人注射，此前注射的73人没出现问题。在谢明果身亡之后，他们立即封存剩下的疫苗，并送到相关机构检测，均检测无异常，之后再给其他孩子继续注射，也没出现问题。</p><p>2月1日，元山镇卫生院蒲姓负责人表示，他们对疫苗的管理和注射等都遵守国家相关规定，疫苗是从县疾控中心领取，运输和保管都按规定进行，比如冷藏、登记等。在注射疫苗时，会询问是否有过敏史、是否患病等，注射疫苗之后，也会留院观察30分钟，“所有这些都有记录的”。</p><p>谢明果身亡，出现两份不同的鉴定结果，死因不能明确。平昌医调委工作人员说，此事家属若欲获得赔偿，目前有两种解决方式，一是根据四川省出台的相关补偿办法申请相应补偿，不过过程较复杂，二是走司法途径，向法院提起诉讼。</p>\n" +
                "                <div class=\"otitle_editor\">\n" +
                "                    <p class='otitle'>\n" +
                "                        (原标题：四川一婴儿注射疫苗后次日凌晨死亡 官方：可申请赔偿)\n" +
                "                    </p>\n" +
                "                     \n" +
                "                    <p class='editor'>\n" +
                "                        (责任编辑：李琮_B11284)\n" +
                "                    </p>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </article>\n" +
                "</main>\n" +
                "<footer>\n" +
                "    <div class=\"copyright\">&copy;1997-2019网易公司版权所有</div>\n" +
                "</footer>\n" +
                "</body>\n" +
                "</html>";
        Spanned spanned = CustomHtml.fromHtml(html,CustomHtml.FROM_HTML_MODE_LEGACY,new RichEditImageGetter(this,articleContent),null);
        articleContent.setText(spanned);
        articleContent.setFocusable(false);
        articleContent.setFocusableInTouchMode(false);

    }

    @Override
    public void initData() {

    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public BaseModel initModel() {
        return null;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {

    }
}
