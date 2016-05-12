package com.hm.zhihuclient.frag_information;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.hm.zhihuclient.R;
import com.hm.zhihuclient.data.Contents;
import com.hm.zhihuclient.frag_beauty.BeautyShowActivity;
import com.hm.zhihuclient.frag_information.adapter.ListViewAdapter;
import com.hm.zhihuclient.frag_information.model.DetailsModel;
import com.hm.zhihuclient.frag_information.model.GitModel;
import com.hm.zhihuclient.frag_information.view.WaterDropListView;
import com.hm.zhihuclient.model.HttpMode;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.List;

public class InformationFragment extends Fragment implements ViewPagerEx.OnPageChangeListener, WaterDropListView.IWaterDropListViewListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private SliderLayout sliderLayout;
    private WaterDropListView listView;
    private ListViewAdapter adapter;

    private int pageNum = 1;
    private HttpMode httpMode;
    private DetailsModel detailsModel;
    private TextSliderView textSliderView;
    private ProgressWheel progressWheel;

    public InformationFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_information, container, false);
        progressWheel=(ProgressWheel)root.findViewById(R.id.progress_wheel) ;
        setBanner(root);
        setList(root);
        return root;
    }

    private void setBanner(View root) {
        sliderLayout = (SliderLayout) root.findViewById(R.id.slider);
        setConfig(sliderLayout);
        httpMode = new HttpMode();
        httpMode.setUrlListener(new HttpMode.UrlListener() {
            @Override
            public void onSucceed(Object details) {
                if (progressWheel.getVisibility()==View.VISIBLE){
                    progressWheel.setVisibility(View.GONE);
                }
                sliderLayout.setVisibility(View.VISIBLE);
                List<DetailsModel> listDetails = ((GitModel) details).getResults();
                for (int i = 0; i < listDetails.size(); i++) {
                    textSliderView = new TextSliderView(getActivity());
                    detailsModel = listDetails.get(i);
                    // initialize a SliderLayout
                    textSliderView
                            .description(detailsModel.getWho())
                            .image(detailsModel.getUrl())
                            .setScaleType(BaseSliderView.ScaleType.Fit);
                    textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView slider) {
                            Intent intent = new Intent(getActivity(), BeautyShowActivity.class);
                            intent.putExtra("url", slider.getUrl());
                            getActivity().startActivity(intent);
                        }
                    });

                    //add your extra information
                    textSliderView.bundle(new Bundle());
                    textSliderView.getBundle()
                            .putString("extra", detailsModel.getWho());
                    textSliderView.setOnImageLoadListener(new BaseSliderView.ImageLoadListener() {
                        @Override
                        public void onStart(BaseSliderView target) {

                        }

                        @Override
                        public void onEnd(boolean result, BaseSliderView target) {
                            textSliderView
                                    .description(detailsModel.getWho())
                                    .image(R.mipmap.ic_launcher)
                                    .setScaleType(BaseSliderView.ScaleType.Fit);
                        }
                    });

                    sliderLayout.addSlider(textSliderView);
                }
            }
        });
        httpMode.getInformationFromNet(Contents.MainBannerUrl);
    }

    private void setConfig(SliderLayout sliderLayout) {
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(4000);
        sliderLayout.addOnPageChangeListener(this);
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.DepthPage);
    }

    private void setList(View root) {
        listView = (WaterDropListView) root.findViewById(R.id.transformers);
        listView.setWaterDropListViewListener(this);
        listView.setPullLoadEnable(true);
        httpMode = new HttpMode();
        httpMode.setUrlListener(new HttpMode.UrlListener() {
            @Override
            public void onSucceed(Object details) {
                if (progressWheel.getVisibility()==View.VISIBLE){
                    progressWheel.setVisibility(View.GONE);
                }
                listView.setVisibility(View.VISIBLE);
                adapter = new ListViewAdapter(getActivity());
                adapter.setData(((GitModel) details).getResults());
                listView.setAdapter(adapter);
            }
        });
        httpMode.getInformationFromNet(Contents.getMainListUrl(1));


    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onRefresh() {
        httpMode = new HttpMode();
        httpMode.setUrlListener(new HttpMode.UrlListener() {
            @Override
            public void onSucceed(Object details) {
                adapter.onRefresh(((GitModel) details).getResults());
                listView.stopRefresh();

            }
        });
        httpMode.getInformationFromNet(Contents.getMainListUrl(1));
    }

    @Override
    public void onLoadMore() {
        pageNum++;
        httpMode = new HttpMode();
        httpMode.setUrlListener(new HttpMode.UrlListener() {
            @Override
            public void onSucceed(Object details) {
                adapter.onLoadMore(((GitModel) details).getResults());
                listView.stopLoadMore();
            }
        });
        httpMode.getInformationFromNet(Contents.getMainListUrl(pageNum));
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
