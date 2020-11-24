package com.example.soukousschallenge.model;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.soukousschallenge.R;

import org.w3c.dom.Text;

import java.util.List;

public class ScoreAdapter extends BaseAdapter{

    private Context mContext;
    private List<Score> scores;
    private LayoutInflater inflater;

    public ScoreAdapter(Context context, List<Score> scores){
        this.mContext = context;
        this.scores = scores;
        this.inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount(){
        return scores.size();
    }

    @Override
    public Score getItem(int i){
        return scores.get(i);
    }

    @Override
    public long getItemId(int i){
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup){

        view = inflater.inflate(R.layout.liste_scores_items,null);
        Score currentItem = getItem(i);
        String scoreValue = Integer.toString(currentItem.getValeurScore());

        TextView scoreOrder_tv = view.findViewById(R.id.activity_score_order_txt);
        TextView scoreValue_tv = view.findViewById(R.id.activity_score_value_txt);


        scoreOrder_tv.setText("YOUHOUUU");
        scoreValue_tv.setText(scoreValue);
        return view;
    }
}