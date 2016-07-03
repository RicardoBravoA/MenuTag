package com.rba.menutag.util.control.tag;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.rba.menutag.R;
import com.rba.menutag.model.response.TagEntity;
import com.rba.menutag.util.control.tag.listener.TagListener;

/**
 * Created by Ricardo Bravo on 3/07/16.
 */

public class TagGroup extends TagLayout implements TagListener {

    private Context context;
    private int tagHeight;
    private int selectedColor = -1;
    private int selectedFontColor = -1;
    private int unselectedColor = -1;
    private int unselectedFontColor = -1;
    private int selectTransitionMS = 750;
    private int deselectTransitionMS = 500;
    private boolean singleChoice;

    private TagListener tagListener;

    public TagGroup(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public TagGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TagGroup, 0, 0);

        try {
            selectedColor = a.getColor(R.styleable.TagGroup_selectedColor, -1);
            selectedFontColor = a.getColor(R.styleable.TagGroup_selectedFontColor, -1);
            unselectedColor = a.getColor(R.styleable.TagGroup_deselectedColor, -1);
            unselectedFontColor = a.getColor(R.styleable.TagGroup_deselectedFontColor, -1);
            selectTransitionMS = a.getInt(R.styleable.TagGroup_selectTransitionMS, 750);
            deselectTransitionMS = a.getInt(R.styleable.TagGroup_deselectTransitionMS, 500);
            singleChoice = a.getBoolean(R.styleable.TagGroup_singleChoice, false);
        } finally {
            a.recycle();
        }

        init();
    }

    private void init() {
        tagHeight = (int) (28 * getResources().getDisplayMetrics().density + 0.5f);
        singleChoice = false;
    }

    public void setSelectedColor(int selectedColor) {
        this.selectedColor = selectedColor;
    }

    public void setSelectedFontColor(int selectedFontColor) {
        this.selectedFontColor = selectedFontColor;
    }

    public void setUnselectedColor(int unselectedColor) {
        this.unselectedColor = unselectedColor;
    }

    public void setUnselectedFontColor(int unselectedFontColor) {
        this.unselectedFontColor = unselectedFontColor;
    }

    public void setSelectTransitionMS(int selectTransitionMS) {
        this.selectTransitionMS = selectTransitionMS;
    }

    public void setDeselectTransitionMS(int deselectTransitionMS) {
        this.deselectTransitionMS = deselectTransitionMS;
    }

    public void setSingleChoice(boolean singleChoice) {
        this.singleChoice = singleChoice;
        if (singleChoice) {
            for (int i = 0; i < getChildCount(); i++) {
                Tag tag = (Tag) getChildAt(i);
                tag.deselect();
            }
        }
    }

    public void setTagListener(TagListener tagListener) {
        this.tagListener = tagListener;
    }

    public void addTag(TagEntity tagEntity) {
        Tag tag = new Tag.TagBuilder().index(getChildCount())
                .label(tagEntity.getDescription())
                .selectedColor(selectedColor)
                .selectedFontColor(selectedFontColor)
                .unselectedColor(unselectedColor)
                .unselectedFontColor(unselectedFontColor)
                .selectTransitionMS(selectTransitionMS)
                .deselectTransitionMS(deselectTransitionMS)
                .tagHeight(tagHeight)
                .tagListener(this)
                .build(context);

        addView(tag);
    }

    public void setSelectedTag(int index) {
        Tag tag = (Tag) getChildAt(index);
        tag.select();
    }

    @Override
    public void tagSelected(int index) {

        if (singleChoice) {
            for (int i = 0; i < getChildCount(); i++) {
                Tag tag = (Tag) getChildAt(i);
                if (i != index) {
                    tag.deselect();
                }
            }
        }

        if (tagListener != null) {
            tagListener.tagSelected(index);
        }
    }

    @Override
    public void tagDeselected(int index) {
        if (tagListener != null) {
            tagListener.tagDeselected(index);
        }
    }

    public boolean isSelected(int index) {
        if (index > 0 && index < getChildCount()) {
            Tag tag = (Tag) getChildAt(index);
            return tag.isSelected();
        }
        return false;
    }

}
