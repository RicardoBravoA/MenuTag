package com.rba.menutag;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.rba.menutag.model.response.TagEntity;
import com.rba.menutag.util.control.tag.TagGroup;
import com.rba.menutag.util.control.tag.listener.TagListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TagGroup tagGroup1, tagGroup2;
    private List<TagEntity> tagEntityList;
    private boolean[] arrayTag1, arrayTag2;
    private int numberTags = 10;
    private Button btnResult;
    private TextView lblResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tagGroup1 = (TagGroup) findViewById(R.id.tagGroup1);
        tagGroup2 = (TagGroup) findViewById(R.id.tagGroup2);
        btnResult = (Button) findViewById(R.id.btnResult);
        lblResult = (TextView) findViewById(R.id.lblResult);

        tagEntityList = new ArrayList<>();
        arrayTag1 = new boolean[numberTags];
        arrayTag2 = new boolean[numberTags];

        for (int i = 0; i < numberTags; i++){
            TagEntity tagEntity = new TagEntity(String.valueOf(i), "Tag "+i);
            tagGroup1.addTag(tagEntity);
            tagGroup2.addTag(tagEntity);
            tagEntityList.add(tagEntity);
        }

        Arrays.fill(arrayTag1, Boolean.FALSE);
        Arrays.fill(arrayTag2, Boolean.FALSE);

        tagGroup1.setTagListener(new TagListener() {
            @Override
            public void tagSelected(int index) {
                arrayTag1[index] = true;
            }

            @Override
            public void tagDeselected(int index) {
                arrayTag1[index] = false;
            }
        });

        tagGroup2.setTagListener(new TagListener() {
            @Override
            public void tagSelected(int index) {
                arrayTag2[index] = true;
            }

            @Override
            public void tagDeselected(int index) {
                arrayTag2[index] = false;
            }
        });

        btnResult.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnResult:
                boolean value = false;
                String selected = "Selected Group 1: \n";
                String selectedGroup2="";

                for(int i = 0; i < numberTags; i++){
                    if(arrayTag1[i]){
                        selected +=  tagEntityList.get(i).getDescription()+"\n";
                        value = true;
                    }
                }

                if(!value){
                    selected = getString(R.string.not_tags_group1)+"\n";
                }

                value = false;

                for(int i = 0; i < numberTags; i++){
                    if(arrayTag2[i]){
                        selectedGroup2 +=  tagEntityList.get(i).getDescription()+"\n";
                        value = true;
                    }
                }

                if(!value){
                    selected += getString(R.string.not_tags_group2)+"\n";
                }else{
                    selected += "Selected Group 2: \n";
                    selected += selectedGroup2;
                }

                lblResult.setText(selected);


                break;
            default:
                break;
        }
    }
}
