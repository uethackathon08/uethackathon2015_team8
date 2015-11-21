package com.j4f.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j4f.R;
import com.j4f.adapters.QuestionAdapter;
import com.j4f.cores.CoreActivity;
import com.j4f.cores.CoreFragment;
import com.j4f.enums.Category;
import com.j4f.models.Answer;
import com.j4f.models.Question;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by hunter on 11/21/2015.
 */
public class QuestionsFragment extends CoreFragment {

    private ArrayList<Question> questionList;
    private QuestionAdapter questionAdapter;
    private RecyclerView questionRecyclerView;

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
        categoryList.add(Category.ANDROID);
        categoryList.add(Category.COMPUTER_VISION);
        categoryList.add(Category.MACHINE_LEARNING);
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
        questionRecyclerView.setHasFixedSize(true);
        questionRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        questionAdapter = new QuestionAdapter(questionList, mActivity);
        questionRecyclerView.setAdapter(questionAdapter);
    }

    @Override
    protected void initViews(View v) {
        questionRecyclerView = (RecyclerView) v.findViewById(R.id.question_list);
    }

    @Override
    protected void initAnimations() {

    }

    @Override
    protected void initListener() {
    }

    public static final long serialVersionUID = 6036846677812555352L;
    public static CoreActivity mActivity;
    public static QuestionsFragment mInstance;
    public static QuestionsFragment getInstance(CoreActivity activity) {
        if (mInstance == null) {
            mInstance = new QuestionsFragment();
        }
        mActivity = activity;
        return mInstance;
    }
    private QuestionsFragment() {

    }
}
