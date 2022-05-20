package com.nagico.bookstore.views.groups

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.CompoundButton
import android.widget.LinearLayout
import android.widget.RadioButton

class MultiLineRadioGroup : LinearLayout {
    /**
     *
     * Returns the identifier of the selected radio button in this group.
     * Upon empty selection, the returned value is -1.
     *
     * @return the unique id of the selected radio button in this group
     * @attr ref android.R.styleable#MyRadioGroup_checkedButton
     * @see .check
     * @see .clearCheck
     */
    // holds the checked id; the selection is empty by default
    var checkedRadioButtonId = -1
        private set

    // tracks children radio buttons checked state
    private var mChildOnCheckedChangeListener: CompoundButton.OnCheckedChangeListener? = null

    // when true, mOnCheckedChangeListener discards events
    private var mProtectFromCheckedChange = false
    private var mOnCheckedChangeListener: OnCheckedChangeListener? = null
    private var mPassThroughListener: PassThroughHierarchyChangeListener? = null

    /**
     * {@inheritDoc}
     */
    constructor(context: Context?) : super(context) {
        orientation = VERTICAL
        init()
    }

    /**
     * {@inheritDoc}
     */
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    private fun init() {
        mChildOnCheckedChangeListener = CheckedStateTracker()
        mPassThroughListener = PassThroughHierarchyChangeListener()
        super.setOnHierarchyChangeListener(mPassThroughListener)
    }

    /**
     * {@inheritDoc}
     */
    override fun setOnHierarchyChangeListener(listener: OnHierarchyChangeListener) {
        // the user listener is delegated to our pass-through listener
        mPassThroughListener!!.mOnHierarchyChangeListener = listener
    }

    /**
     * set the default checked radio button, without notification the listeners
     */
    fun setCheckWithoutNotif(id: Int) {
        if (id != -1 && id == checkedRadioButtonId) {
            return
        }
        mProtectFromCheckedChange = true
        if (checkedRadioButtonId != -1) {
            setCheckedStateForView(checkedRadioButtonId, false)
        }
        if (id != -1) {
            setCheckedStateForView(id, true)
        }
        checkedRadioButtonId = id
        mProtectFromCheckedChange = false
    }

    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams) {
        val btns = getAllRadioButton(child)
        if (btns != null && btns.size > 0) {
            for (button in btns) {
                if (button.isChecked) {
                    mProtectFromCheckedChange = true
                    if (checkedRadioButtonId != -1) {
                        setCheckedStateForView(checkedRadioButtonId, false)
                    }
                    mProtectFromCheckedChange = false
                    setCheckedId(button.id)
                }
            }
        }
        super.addView(child, index, params)
    }

    /**
     * get all radio buttons which are in the view
     *
     * @param child
     */
    private fun getAllRadioButton(child: View): List<RadioButton> {
        val btns: MutableList<RadioButton> = ArrayList()
        if (child is RadioButton) {
            btns.add(child)
        } else if (child is ViewGroup) {
            val counts = child.childCount
            for (i in 0 until counts) {
                btns.addAll(getAllRadioButton(child.getChildAt(i)))
            }
        }
        return btns
    }

    /**
     *
     * Sets the selection to the radio button whose identifier is passed in
     * parameter. Using -1 as the selection identifier clears the selection;
     * such an operation is equivalent to invoking [.clearCheck].
     *
     * @param id the unique id of the radio button to select in this group
     * @see .getCheckedRadioButtonId
     * @see .clearCheck
     */
    fun check(id: Int) {
        // don't even bother
        if (id != -1 && id == checkedRadioButtonId) {
            return
        }
        if (checkedRadioButtonId != -1) {
            setCheckedStateForView(checkedRadioButtonId, false)
        }
        if (id != -1) {
            setCheckedStateForView(id, true)
        }
        setCheckedId(id)
    }

    private fun setCheckedId(id: Int) {
        checkedRadioButtonId = id
        if (mOnCheckedChangeListener != null) {
            mOnCheckedChangeListener!!.onCheckedChanged(this, checkedRadioButtonId)
        }
    }

    private fun setCheckedStateForView(viewId: Int, checked: Boolean) {
        val checkedView = findViewById<View>(viewId)
        if (checkedView != null && checkedView is RadioButton) {
            checkedView.isChecked = checked
        }
    }

    /**
     *
     * Clears the selection. When the selection is cleared, no radio button
     * in this group is selected and [.getCheckedRadioButtonId] returns
     * null.
     *
     * @see .check
     * @see .getCheckedRadioButtonId
     */
    fun clearCheck() {
        check(-1)
    }

    /**
     *
     * Register a callback to be invoked when the checked radio button
     * changes in this group.
     *
     * @param listener the callback to call on checked state change
     */
    fun setOnCheckedChangeListener(listener: OnCheckedChangeListener?) {
        mOnCheckedChangeListener = listener
    }

    /**
     * {@inheritDoc}
     */
    override fun generateLayoutParams(attrs: AttributeSet): LayoutParams {
        return LayoutParams(context, attrs)
    }

    /**
     * {@inheritDoc}
     */
    override fun checkLayoutParams(p: ViewGroup.LayoutParams): Boolean {
        return p is LayoutParams
    }

    override fun generateDefaultLayoutParams(): LinearLayout.LayoutParams {
        return LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onInitializeAccessibilityEvent(event: AccessibilityEvent) {
        super.onInitializeAccessibilityEvent(event)
        event.className = MultiLineRadioGroup::class.java.name
    }

    override fun onInitializeAccessibilityNodeInfo(info: AccessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(info)
        info.className = MultiLineRadioGroup::class.java.name
    }

    /**
     *
     * This set of layout parameters defaults the width and the height of
     * the children to [.WRAP_CONTENT] when they are not specified in the
     * XML file. Otherwise, this class ussed the value read from the XML file.
     *
     *
     *
     * See
     * for a list of all child view attributes that this class supports.
     */
    class LayoutParams : LinearLayout.LayoutParams {
        /**
         * {@inheritDoc}
         */
        constructor(c: Context?, attrs: AttributeSet?) : super(c, attrs) {}

        /**
         * {@inheritDoc}
         */
        constructor(w: Int, h: Int) : super(w, h) {}

        /**
         * {@inheritDoc}
         */
        constructor(w: Int, h: Int, initWeight: Float) : super(w, h, initWeight) {}

        /**
         * {@inheritDoc}
         */
        constructor(p: ViewGroup.LayoutParams?) : super(p) {}

        /**
         * {@inheritDoc}
         */
        constructor(source: MarginLayoutParams?) : super(source) {}

        /**
         *
         * Fixes the child's width to
         * [ViewGroup.LayoutParams.WRAP_CONTENT] and the child's
         * height to  [ViewGroup.LayoutParams.WRAP_CONTENT]
         * when not specified in the XML file.
         *
         * @param a          the styled attributes set
         * @param widthAttr  the width attribute to fetch
         * @param heightAttr the height attribute to fetch
         */
        override fun setBaseAttributes(
            a: TypedArray,
            widthAttr: Int, heightAttr: Int
        ) {
            width = if (a.hasValue(widthAttr)) {
                a.getLayoutDimension(widthAttr, "layout_width")
            } else {
                WRAP_CONTENT
            }
            height = if (a.hasValue(heightAttr)) {
                a.getLayoutDimension(heightAttr, "layout_height")
            } else {
                WRAP_CONTENT
            }
        }
    }

    /**
     *
     * Interface definition for a callback to be invoked when the checked
     * radio button changed in this group.
     */
    interface OnCheckedChangeListener {
        /**
         *
         * Called when the checked radio button has changed. When the
         * selection is cleared, checkedId is -1.
         *
         * @param group     the group in which the checked radio button has changed
         * @param checkedId the unique identifier of the newly checked radio button
         */
        fun onCheckedChanged(group: MultiLineRadioGroup?, checkedId: Int)
    }

    private inner class CheckedStateTracker : CompoundButton.OnCheckedChangeListener {
        override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
            // prevents from infinite recursion
            if (mProtectFromCheckedChange) {
                return
            }
            mProtectFromCheckedChange = true
            if (checkedRadioButtonId != -1) {
                setCheckedStateForView(checkedRadioButtonId, false)
            }
            mProtectFromCheckedChange = false
            val id = buttonView.id
            setCheckedId(id)
        }
    }

    /**
     *
     * A pass-through listener acts upon the events and dispatches them
     * to another listener. This allows the table layout to set its own internal
     * hierarchy change listener without preventing the user to setup his.
     */
    private inner class PassThroughHierarchyChangeListener : OnHierarchyChangeListener {
        var mOnHierarchyChangeListener: OnHierarchyChangeListener? = null

        /**
         * {@inheritDoc}
         */
        @SuppressLint("NewApi")
        override fun onChildViewAdded(parent: View, child: View) {
            if (parent === this@MultiLineRadioGroup) {
                val btns = getAllRadioButton(child)
                if (btns != null && btns.isNotEmpty()) {
                    for (btn in btns) {
                        var id = btn.id
                        // generates an id if it's missing
                        if (id == NO_ID && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                            id = generateViewId()
                            btn.id = id
                        }
                        btn.setOnCheckedChangeListener(
                            mChildOnCheckedChangeListener
                        )
                    }
                }
            }
            mOnHierarchyChangeListener?.onChildViewAdded(parent, child)
        }

        /**
         * {@inheritDoc}
         */
        override fun onChildViewRemoved(parent: View, child: View) {
            if (parent === this@MultiLineRadioGroup) {
                val btns = getAllRadioButton(child)
                if (btns != null && btns.isNotEmpty()) {
                    for (btn in btns) {
                        btn.setOnCheckedChangeListener(null)
                    }
                }
            }
            mOnHierarchyChangeListener?.onChildViewRemoved(parent, child)
        }
    }
}