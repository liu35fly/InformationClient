package com.hm.zhihuclient.frag_setup;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.hm.zhihuclient.MainActivity;
import com.hm.zhihuclient.R;
import com.hm.zhihuclient.utils.SharePerfenerceManager;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SetUpFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SetUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SetUpFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SetUpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SetUpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SetUpFragment newInstance(String param1, String param2) {
        SetUpFragment fragment = new SetUpFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        View root = inflater.inflate(R.layout.fragment_set_up, container, false);
        init(root);
        return root;
    }

    public void init(View root) {
        final ImageView isShortCut = (ImageView) root.findViewById(R.id.setup_isShortCut);
        RelativeLayout clean = (RelativeLayout) root.findViewById(R.id.setup_clean);

        isShortCut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SharePerfenerceManager.newInstance(getActivity()).isShortCut()) {
                    isShortCut.setBackgroundResource(R.mipmap.ic_launcher);
                    SharePerfenerceManager.newInstance(getActivity()).isShortCut(false);
                } else {
                    isShortCut.setBackgroundResource(R.mipmap.default_icon);
                    ShortCutUtils.createShortcut(getActivity(), R.mipmap.default_icon, "测试app", MainActivity.class);
                    SharePerfenerceManager.newInstance(getActivity()).isShortCut(true);
                }
            }
        });

        clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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
