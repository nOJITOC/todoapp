package com.mmteams91.todoapp.core.ui.views

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import com.mmteams91.todoapp.R

@SuppressLint("RestrictedApi")
class CheckableImageButton : android.support.design.widget.CheckableImageButton {

    var changeCheckOnClick = true

    internal var checkedChangeListener: OnCheckedChangeListener? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        obtainAttributes(attrs)
    }

    private fun obtainAttributes(attrs: AttributeSet) {
        changeCheckOnClick = attrs.getAttributeBooleanValue(R.styleable.CheckableImageButton_changeCheckOnClick, true)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        obtainAttributes(attrs)
    }


    override fun performClick(): Boolean {
        if (changeCheckOnClick)
            setChecked(!isChecked, true)
        return super.performClick()
    }

    override fun setChecked(checked: Boolean) {
        setChecked(checked, false)
    }

    fun setChecked(checked: Boolean, callOnCLick: Boolean) {
        if (isChecked != checked && callOnCLick && checkedChangeListener != null)
            checkedChangeListener!!.onCheckedChanged(this, checked)
        super.setChecked(checked)
    }

    fun setOnCheckedChangeListener(mCheckedChangeListener: OnCheckedChangeListener?) {
        this.checkedChangeListener = mCheckedChangeListener
    }

    interface OnCheckedChangeListener {
        fun onCheckedChanged(buttonView: CheckableImageButton, isChecked: Boolean)

    }

}
