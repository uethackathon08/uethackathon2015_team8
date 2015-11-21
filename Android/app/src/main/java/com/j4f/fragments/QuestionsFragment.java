package com.j4f.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j4f.R;
import com.j4f.activities.MainActivity;
import com.j4f.adapters.QuestionAdapter;
import com.j4f.cores.CoreFragment;
import com.j4f.models.Answer;
import com.j4f.models.Category;
import com.j4f.models.Question;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by hunter on 11/21/2015.
 */
public class QuestionsFragment extends CoreFragment {

    private ArrayList<Question> questionList;
    private QuestionAdapter questionAdapter;
    private UltimateRecyclerView questionRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, container, false);
        initViews(view);
        initListener();
        initModels();
        initAnimations();
        return view;
    }

    public void initQuestionRecyclerViewData() {
        ArrayList<Category> categoryList = new ArrayList<Category>();
        categoryList.add(new Category("C1", "Android", "Link", 5, 5));
        ArrayList<Answer> answersList = new ArrayList<Answer>();
        answersList.add(new Answer("U123456", "Trương Quốc Tuấn", "https://lh6.googleusercontent.com/-ewfAMUBQ-nM/AAAAAAAAAAI/AAAAAAAAAC4/_Mw-qQKu9zA/photo.jpg", "Trả lời ngu vồn", null, 5, 4, new Date()));
        answersList.add(new Answer("U123456", "Nguyễn Văn Giáp", "https://lh6.googleusercontent.com/-ewfAMUBQ-nM/AAAAAAAAAAI/AAAAAAAAAC4/_Mw-qQKu9zA/photo.jpg", "Trả lời ngu vồn", null, 5, 4, new Date()));
        questionList = new ArrayList<Question>();
        questionList.add(new Question(categoryList, "U12345", "Việt Anh Vũ", "https://lh6.googleusercontent.com/-ewfAMUBQ-nM/AAAAAAAAAAI/AAAAAAAAAC4/_Mw-qQKu9zA/photo.jpg","Đây là đài tiếng nói Việt Nam", "Đây là đài tiếng nói Việt Nam, phát thanh từ Hà Nội, thủ đô nước cộng hòa xã hội chủ nghĩa Việt Nam", "http://media.tinmoi.vn/2013/04/30/318643_500.jpg", new Date(), 25, 15, 20, answersList));
        questionList.add(new Question(categoryList, "U12345", "Nguyễn Thạc Thống", "https://lh6.googleusercontent.com/-ewfAMUBQ-nM/AAAAAAAAAAI/AAAAAAAAAC4/_Mw-qQKu9zA/photo.jpg","Đây là đài tiếng nói Việt Nam", "Đây là đài tiếng nói Việt Nam, phát thanh từ Hà Nội, thủ đô nước cộng hòa xã hội chủ nghĩa Việt Nam", "http://media.tinmoi.vn/2013/04/30/318643_500.jpg", new Date(), 25, 15, 20, answersList));
        questionList.add(new Question(categoryList, "U12345", "Việt Anh Vũ", "https://lh6.googleusercontent.com/-ewfAMUBQ-nM/AAAAAAAAAAI/AAAAAAAAAC4/_Mw-qQKu9zA/photo.jpg","Đây là đài tiếng nói Việt Nam", "Đây là đài tiếng nói Việt Nam, phát thanh từ Hà Nội, thủ đô nước cộng hòa xã hội chủ nghĩa Việt Nam", "http://media.tinmoi.vn/2013/04/30/318643_500.jpg", new Date(), 25, 15, 20, answersList));

    }

    @Override
    public void onClick(View view) {
    }

    @Override
    protected void initModels() {
        initQuestionRecyclerViewData();
        questionAdapter = new QuestionAdapter(questionList, mActivity);
        questionRecyclerView.setHasFixedSize(true);
        questionRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        questionRecyclerView.setAdapter(questionAdapter);


//        questionRecyclerView.setScrollViewCallbacks(new ObservableScrollViewCallbacks() {
//            @Override
//            public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
//            }
//
//            @Override
//            public void onDownMotionEvent() {
//            }
//
//            @Override
//            public void onUpOrCancelMotionEvent(ObservableScrollState observableScrollState) {
//                Display display = mActivity.getWindowManager().getDefaultDisplay();
//                int height = display.getHeight();
//                if (observableScrollState == ObservableScrollState.DOWN) {
//                    mActivity.showToastLong(height);
//                    questionRecyclerView.showToolbar(mActivity.toolbar, questionRecyclerView, height);
//                } else if (observableScrollState == ObservableScrollState.UP) {
//                    mActivity.showToastLong(height);
//                    questionRecyclerView.hideToolbar(mActivity.toolbar, questionRecyclerView, height);
//                } else if (observableScrollState == ObservableScrollState.STOP) {
//
//                }
//            }
//        });

        questionRecyclerView.enableLoadmore();
        questionRecyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, final int maxLastVisiblePosition) {
                mContext.showToastLong("Load more");
            }
        });
    }

    @Override
    protected void initViews(View v) {
        questionRecyclerView = (UltimateRecyclerView) v.findViewById(R.id.question_list);
    }

    @Override
    protected void initAnimations() {

    }

    @Override
    protected void initListener() {
    }

    public static final long serialVersionUID = 6036846677812555352L;
    public static MainActivity mActivity;
    public static QuestionsFragment mInstance;
    public static QuestionsFragment getInstance(MainActivity activity) {
        if (mInstance == null) {
            mInstance = new QuestionsFragment();
        }
        mActivity = activity;
        return mInstance;
    }
    private QuestionsFragment() {

    }
}
