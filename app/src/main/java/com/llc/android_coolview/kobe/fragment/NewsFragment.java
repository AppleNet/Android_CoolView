package com.llc.android_coolview.kobe.fragment;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.llc.android_coolview.R;
import com.llc.android_coolview.animation.SwitchAnimationUtil;
import com.llc.android_coolview.kobe.activity.KobeActivity;
import com.llc.android_coolview.kobe.activity.KobeNewsDetailActivity;
import com.llc.android_coolview.kobe.activity.KobeNewsPicDetailActivity;
import com.llc.android_coolview.kobe.adapter.KobeNetEaseNewsRecyclerViewAdapter;
import com.llc.android_coolview.kobe.adapter.KobeNetEaseNewsRecyclerViewAdapter.MyItemClickListener;
import com.llc.android_coolview.kobe.bean.news.Ads;
import com.llc.android_coolview.kobe.bean.news.FirstNew;
import com.llc.android_coolview.kobe.bean.news.FourthNew;
import com.llc.android_coolview.kobe.bean.news.Imgextra;
import com.llc.android_coolview.kobe.bean.news.News;
import com.llc.android_coolview.kobe.bean.news.SecondNew;
import com.llc.android_coolview.kobe.bean.news.ThirdNew;
import com.llc.android_coolview.kobe.impl.NewsMenuOnClickListener;
import com.llc.android_coolview.kobe.impl.dao.NewsMenuShow;
import com.llc.android_coolview.util.HttpClientHelper;
import com.llc.android_coolview.util.ProgressDialogUtils;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Toast;

public class NewsFragment extends Fragment implements NewsMenuOnClickListener, MyItemClickListener {

    private View view;
    private NewsMenuShow menuShow;
    public KobeActivity mKobeActivity;
    private ViewStub tencentViewSub, netEaseViewSub, sinaViewSub;
    private View netEaseSubView;
    private ProgressDialogUtils mProgressDialogUtils;
    private KobeNetEaseNewsRecyclerViewAdapter knenrAdapter;

    private List<News> news = new ArrayList<>();

    private static final int NET_EASE = 1;
    private static final int TENCENT = 2;
    private static final int SINA = 3;

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout refreshLayout;

    private boolean isUsed = false;
    private boolean isOnCreate = false, isOnResume = false;

    private SwitchAnimationUtil switchAnimationUtil;

    private Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            mProgressDialogUtils.dismissProgressDialog();
            onLoad();
            switch (msg.what) {
                case NET_EASE:
                    String result = (String) msg.obj;
                    if (result != null) {
                        try {

                            JSONObject jsonObject = new JSONObject(result);
                            JSONArray jsonArray = jsonObject.getJSONArray("T1348647909107");
                            JSONObject firstJsonObject = jsonArray.getJSONObject(0);
                            News firstNew = new FirstNew();
                            ((FirstNew) firstNew).setHasCover(firstJsonObject.optString("hasCover"));
                            ((FirstNew) firstNew).setTemplate(firstJsonObject.optString("template"));
                            ((FirstNew) firstNew).setHasHead(firstJsonObject.optString("hasHead"));
                            firstNew.setSkipID(firstJsonObject.optString("skipID"));
                            firstNew.setReplyCount(firstJsonObject.optString("replyCount"));
                            ((FirstNew) firstNew).setAlias(firstJsonObject.optString("alias"));
                            ((FirstNew) firstNew).setHasImg(firstJsonObject.optString("hasImg"));
                            firstNew.setDigest(firstJsonObject.optString("digest"));
                            ((FirstNew) firstNew).setHasIcon(firstJsonObject.optString("hasIcon"));
                            firstNew.setSkipType(firstJsonObject.optString("skipType"));
                            ((FirstNew) firstNew).setCid(firstJsonObject.optString("cid"));
                            firstNew.setDocid(firstJsonObject.optString("docid"));
                            firstNew.setTitle(firstJsonObject.optString("title"));
                            ((FirstNew) firstNew).setHasAD(firstJsonObject.optString("hasAD"));
                            ((FirstNew) firstNew).setOrder(firstJsonObject.optString("order"));
                            JSONArray imgextra = firstJsonObject.optJSONArray("imgextra");
                            List<Imgextra> imgextras = new ArrayList<>();
                            for (int im = 0; im < imgextra.length(); im++) {
                                Imgextra img = new Imgextra();
                                img.setImgsrc(imgextra.getJSONObject(im).optString("imgsrc"));
                                imgextras.add(img);
                            }
                            ((FirstNew) firstNew).setImgextra(imgextras);
                                ((FirstNew) firstNew).setPriority(firstJsonObject.optString("priority"));
                                firstNew.setLmodify(firstJsonObject.optString("lmodify"));
                                ((FirstNew) firstNew).setEname(firstJsonObject.optString("ename"));
                                ((FirstNew) firstNew).setTname(firstJsonObject.optString("tname"));
                                firstNew.setImgsrc(firstJsonObject.optString("imgsrc"));
                            if (firstJsonObject.has("ads")) {
                                JSONArray adss = firstJsonObject.optJSONArray("ads");
                                List<Ads> mAdss = new ArrayList<>();
                                for (int ad = 0; ad < adss.length(); ad++) {
                                    Ads mAds = new Ads();
                                    mAds.setTitle(adss.getJSONObject(ad).optString("title"));
                                    mAds.setTag(adss.getJSONObject(ad).optString("tag"));
                                    mAds.setImgsrc(adss.getJSONObject(ad).optString("imgsrc"));
                                    mAds.setSubtitle(adss.getJSONObject(ad).optString("subtitle"));
                                    mAds.setUrl(adss.getJSONObject(ad).optString("url"));
                                    mAdss.add(mAds);
                                }
                                ((FirstNew) firstNew).setAds(mAdss);
                            }

                            if (firstJsonObject.has("photosetID")) {
                                ((FirstNew) firstNew).setPhotosetID(firstJsonObject.optString("photosetID"));
                            }
                            firstNew.setPtime(firstJsonObject.optString("ptime"));
                            firstNew.setFlag("first");
                            news.add(firstNew);

                            for (int i = 1; i < jsonArray.length(); i++) {

                                News secondNew = new SecondNew();
                                News thirdNew = new ThirdNew();
                                News fourthNew = new FourthNew();

                                JSONObject secondJsonObject = jsonArray.getJSONObject(i);
                                if (isThirdHas(secondJsonObject)) {
                                    if (secondJsonObject.has("skipType"))
                                        thirdNew.setSkipType(secondJsonObject.getString("skipType"));
                                    if (secondJsonObject.has("title"))
                                        thirdNew.setTitle(secondJsonObject.getString("title"));
                                    if (secondJsonObject.has("imgextra")) {
                                        JSONArray secondimgextra = secondJsonObject.getJSONArray("imgextra");
                                        List<Imgextra> secondimgextras = new ArrayList<>();
                                        for (int im = 0; im < secondimgextra.length(); im++) {
                                            Imgextra img = new Imgextra();
                                            img.setImgsrc(secondimgextra.getJSONObject(im).getString("imgsrc"));
                                            secondimgextras.add(img);
                                        }
                                        ((ThirdNew) thirdNew).setImgextra(secondimgextras);
                                    }
                                    if (secondJsonObject.has("skipID"))
                                        thirdNew.setSkipID(secondJsonObject.getString("skipID"));
                                    if (secondJsonObject.has("priority"))
                                        ((ThirdNew) thirdNew).setPriority(secondJsonObject.getInt("priority"));
                                    if (secondJsonObject.has("lmodify"))
                                        thirdNew.setLmodify(secondJsonObject.getString("lmodify"));
                                    if (secondJsonObject.has("imgsrc"))
                                        thirdNew.setImgsrc(secondJsonObject.getString("imgsrc"));
                                    if (secondJsonObject.has("digest"))
                                        thirdNew.setDigest(secondJsonObject.getString("digest"));
                                    if (secondJsonObject.has("skipType"))
                                        thirdNew.setSkipType(secondJsonObject.getString("skipType"));
                                    if (secondJsonObject.has("replyCount")) {
                                        thirdNew.setReplyCount(secondJsonObject.getString("replyCount"));
                                    }
                                    if (secondJsonObject.has("photosetID"))
                                        ((ThirdNew) thirdNew).setPhotosetID(secondJsonObject.getString("photosetID"));
                                    if (secondJsonObject.has("ptime"))
                                        thirdNew.setPtime(secondJsonObject.getString("ptime"));
                                    thirdNew.setFlag("third");
                                    news.add(thirdNew);

                                } else if (isSecondHas(secondJsonObject)) {

                                    if (secondJsonObject.has("skipID"))
                                        fourthNew.setSkipID(secondJsonObject.getString("skipID"));
                                    if (secondJsonObject.has("digest"))
                                        fourthNew.setDigest(secondJsonObject.getString("digest"));
                                    if (secondJsonObject.has("skipType"))
                                        fourthNew.setSkipType(secondJsonObject.getString("skipType"));
                                    if (secondJsonObject.has("title"))
                                        fourthNew.setTitle(secondJsonObject.getString("title"));
                                    if (secondJsonObject.has("priority"))
                                        ((FourthNew) fourthNew).setPriority(secondJsonObject.getInt("priority"));
                                    if (secondJsonObject.has("lmodify"))
                                        fourthNew.setLmodify(secondJsonObject.getString("lmodify"));
                                    if (secondJsonObject.has("imgsrc"))
                                        fourthNew.setImgsrc(secondJsonObject.getString("imgsrc"));
                                    if (secondJsonObject.has("ptime"))
                                        fourthNew.setPtime(secondJsonObject.getString("ptime"));
                                    if (secondJsonObject.has("videosource"))
                                        ((FourthNew) fourthNew).setVideosource(secondJsonObject.getString("videosource"));
                                    if (secondJsonObject.has("TAGS"))
                                        ((FourthNew) fourthNew).setTAGS(secondJsonObject.getString("TAGS"));
                                    if (secondJsonObject.has("videoID"))
                                        ((FourthNew) fourthNew).setVideoID(secondJsonObject.getString("videoID"));
                                    fourthNew.setFlag("fourth");
                                    news.add(fourthNew);

                                } else {

                                    if (secondJsonObject.has("url_3w")) {
                                        ((SecondNew) secondNew).setUrl_3w(secondJsonObject.getString("url_3w"));
                                    }
                                    if (secondJsonObject.has("votecount")) {
                                        ((SecondNew) secondNew).setVotecount(secondJsonObject.getString("votecount"));
                                    }
                                    if (secondJsonObject.has("replyCount")) {
                                        secondNew.setReplyCount(secondJsonObject.getString("replyCount"));
                                    }
                                    if (secondJsonObject.has("digest")) {
                                        secondNew.setDigest(secondJsonObject.getString("digest"));
                                    }
                                    if (secondJsonObject.has("url")) {
                                        ((SecondNew) secondNew).setUrl(secondJsonObject.getString("url"));
                                    }
                                    if (secondJsonObject.has("docid")) {
                                        secondNew.setDocid(secondJsonObject.getString("docid"));
                                    }
                                    if (secondJsonObject.has("title")) {
                                        secondNew.setTitle(secondJsonObject.getString("title"));
                                    }
                                    if (secondJsonObject.has("source")) {
                                        ((SecondNew) secondNew).setSource(secondJsonObject.getString("source"));
                                    }
                                    if (secondJsonObject.has("priority")) {
                                        ((SecondNew) secondNew).setPriority(secondJsonObject.getString("priority"));
                                    }
                                    if (secondJsonObject.has("lmodify")) {
                                        secondNew.setLmodify(secondJsonObject.getString("lmodify"));
                                    }
                                    if (secondJsonObject.has("imgsrc")) {
                                        secondNew.setImgsrc(secondJsonObject.getString("imgsrc"));
                                    }
                                    if (secondJsonObject.has("subtitle")) {
                                        ((SecondNew) secondNew).setSubtitle(secondJsonObject.getString("subtitle"));
                                    }
                                    if (secondJsonObject.has("boardid")) {
                                        ((SecondNew) secondNew).setBoardid(secondJsonObject.getString("boardid"));
                                    }
                                    if (secondJsonObject.has("ptime")) {
                                        secondNew.setPtime(secondJsonObject.getString("ptime"));
                                    }
                                    if (secondJsonObject.has("imgType")) {
                                        secondNew.setImgType(secondJsonObject.getInt("imgType"));
                                    }
                                    secondNew.setFlag("second");
                                    news.add(secondNew);
                                }
                            }
                            knenrAdapter = new KobeNetEaseNewsRecyclerViewAdapter(news, getActivity());
                            knenrAdapter.setOnItemClickListener(NewsFragment.this);
                            mRecyclerView.setAdapter(knenrAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(getActivity(), getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                    }
                    break;
                case TENCENT:

                    break;
                case SINA:

                    break;
                default:
                    break;
            }
            return false;
        }
    });


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_kobe_fragment_news, null);
        initViews();
        if (isUsed == true && isOnCreate == false && isOnResume == false) {
            initDatas();
        }
        isOnCreate = true;
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isUsed = isVisibleToUser;
        if (isUsed && isOnCreate && isOnResume) {
            initDatas();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        isOnResume = true;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((KobeActivity) getActivity()).setOnKobeResumeWindowFocusChanged(new KobeActivity.OnKobeResumeWindowFocusChanged() {
            @Override
            public void resumeWindowFoucsChanged() {
                if (switchAnimationUtil == null) {
                    switchAnimationUtil = new SwitchAnimationUtil();
                    switchAnimationUtil.startAnimation(mRecyclerView, SwitchAnimationUtil.AnimationType.SCALE);
                }
            }
        });
    }

    private void initViews() {
        tencentViewSub = (ViewStub) view.findViewById(R.id.tencent);
        netEaseViewSub = (ViewStub) view.findViewById(R.id.netease);
        sinaViewSub = (ViewStub) view.findViewById(R.id.sina);
        netEaseViewSub.inflate();
        netEaseSubView = view.findViewById(R.id.activity_kobe_news_fragment_netease);
        mRecyclerView = (RecyclerView) netEaseSubView.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        //设置一个垂直方向的layout manager,也可以设置成水平方向
        int orientation = LinearLayoutManager.VERTICAL;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), orientation, false));
        refreshLayout = (SwipeRefreshLayout) netEaseSubView.findViewById(R.id.refresher);
        refreshLayout.setColorSchemeColors(Color.RED);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh() {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        HttpClientHelper helper = new HttpClientHelper();
                        helper.setRequestMethod("GET");
                        String result = helper.readHtml("http://c.m.163.com/nc/article/headline/T1348647909107/0-140.html");
                        handler.obtainMessage(NET_EASE,result);
                    }

                }.start();
            }
        });
        mProgressDialogUtils = new ProgressDialogUtils(getActivity());
    }

    private void initDatas() {
        menuShow = new NewsMenuShow(mKobeActivity);
        menuShow.setNewsMenuOnClickListener(this);
        mProgressDialogUtils.showProgressDialog("正在获取新闻信息，请稍候...");
        new Thread() {
            @Override
            public void run() {
                super.run();
                HttpClientHelper helper = new HttpClientHelper();
                helper.setRequestMethod("GET");
                String result = helper.readHtml("http://c.m.163.com/nc/article/headline/T1348647909107/0-140.html");
                handler.obtainMessage(NET_EASE,result);
            }

        }.start();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public void onNewsMenuClick(int id) {
        switch (id) {
            case R.id.tencent_news:
                Toast.makeText(getActivity(), "点击了腾讯新闻", Toast.LENGTH_LONG).show();
                if (tencentViewSub == null) {
                    tencentViewSub = (ViewStub) view.findViewById(R.id.tencent);
                    tencentViewSub.inflate();
                } else {
                    tencentViewSub.setVisibility(View.VISIBLE);
                }
                netEaseViewSub.setVisibility(View.GONE);
                sinaViewSub.setVisibility(View.GONE);
                //tencentSubView=view.findViewById(R.id.activity_kobe_news_fragment_tencent);
                break;
            case R.id.netease_news:
                Toast.makeText(getActivity(), "点击了网易新闻", Toast.LENGTH_LONG).show();
                if (netEaseViewSub == null) {
                    netEaseViewSub = (ViewStub) view.findViewById(R.id.netease);
                    netEaseViewSub.inflate();
                } else {
                    netEaseViewSub.setVisibility(View.VISIBLE);
                }
                tencentViewSub.setVisibility(View.GONE);
                sinaViewSub.setVisibility(View.GONE);
                netEaseSubView = view.findViewById(R.id.activity_kobe_news_fragment_netease);
                mRecyclerView = (RecyclerView) netEaseSubView.findViewById(R.id.recyclerView);
                mRecyclerView.setHasFixedSize(true);
                //设置一个垂直方向的layout manager
                int orientation = LinearLayoutManager.VERTICAL;
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), orientation, false));
                break;
            case R.id.sina_news:
                Toast.makeText(getActivity(), "点击了新浪新闻", Toast.LENGTH_LONG).show();
                if (sinaViewSub == null) {
                    sinaViewSub = (ViewStub) view.findViewById(R.id.sina);
                    sinaViewSub.inflate();
                } else {
                    sinaViewSub.setVisibility(View.VISIBLE);
                }
                tencentViewSub.setVisibility(View.GONE);
                netEaseViewSub.setVisibility(View.GONE);
                //sinaSubView=view.findViewById(R.id.activity_kobe_new_fragment_sina);

                break;
            default:
                break;
        }
    }

    public static boolean isThirdHas(JSONObject secondJsonObject) {
        return secondJsonObject.has("docid") && secondJsonObject.has("title") && secondJsonObject.has("imgextra") && secondJsonObject.has("replyCount") && secondJsonObject.has("skipID")
                && secondJsonObject.has("priority") && secondJsonObject.has("lmodify") && secondJsonObject.has("imgsrc") && secondJsonObject.has("digest") && secondJsonObject.has("skipType")
                && secondJsonObject.has("photosetID") && secondJsonObject.has("ptime");

    }

    public static boolean isSecondHas(JSONObject secondJsonObject) {
        return secondJsonObject.has("skipID") && secondJsonObject.has("replyCount") && secondJsonObject.has("videosource") && secondJsonObject.has("digest") && secondJsonObject.has("skipType")
                && secondJsonObject.has("docid") && secondJsonObject.has("title") && secondJsonObject.has("TAGS") && secondJsonObject.has("videoID") && secondJsonObject.has("priority")
                && secondJsonObject.has("lmodify") && secondJsonObject.has("imgsrc") && secondJsonObject.has("ptime");
    }

    private void onLoad() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onItemClickListener(View view, int position) {
        News mNews = news.get(position);
        Intent intent = null;
        switch (mNews.getFlag()) {
            case "first":

                break;
            case "second": {
                intent = new Intent(getActivity(), KobeNewsDetailActivity.class);
                SecondNew secondNew = (SecondNew) mNews;
                String url_3w = secondNew.getUrl_3w();
                String title = secondNew.getTitle();
                intent.putExtra("url_3w", url_3w);
                intent.putExtra("title", title);
                break;
            }
            case "third": {
                intent = new Intent(getActivity(), KobeNewsPicDetailActivity.class);
                ThirdNew thirdNew = (ThirdNew) mNews;
                String title = thirdNew.getTitle();
                intent.putExtra("title", title);
                intent.putExtra("skipID", thirdNew.getSkipID().substring(thirdNew.getSkipID().indexOf("|") + 1));
                intent.putExtra("replycount", thirdNew.getReplyCount());
                break;
            }
            case "fourth":

                break;
        }
        startActivity(intent);
    }

}
