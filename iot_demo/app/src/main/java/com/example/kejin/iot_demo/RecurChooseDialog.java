package com.example.kejin.iot_demo;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * Created by kejin on 25/03/2018.
 */

public class RecurChooseDialog extends Dialog implements View.OnClickListener {

    private Dialog mDialog;
    private Context mContext;
    private RadioGroup gRecurGroup;
    private Button mSureButton;
    private Button mCloseDialog;
    private Button mRecurButton;
    private RecurChooseInterface recurChooseInterface;




    public RecurChooseDialog(Context context, RecurChooseInterface recurChooseInterface){


        super(context);
        Log.e("aa", "in recur");
        this.mContext = context;
        this.recurChooseInterface =recurChooseInterface;
        this.mDialog = new Dialog(context, R.style.dialog);
        Log.e("aa", "in recur1");
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_recur_choose, null);
        mDialog.setContentView(view);

        Log.e("aa", "in recur2");
        mCloseDialog = (Button) view.findViewById(R.id.date_choose_close_btn2);
        mSureButton = (Button) view.findViewById(R.id.sure_btn2);
        mRecurButton = (Button) view.findViewById(R.id.recur_btn);
        gRecurGroup = (RadioGroup) view.findViewById(R.id.recur_group);
        Log.e("aa", "in recur3");
        int id = gRecurGroup.getCheckedRadioButtonId();
         Button btt= (Button) mDialog.findViewById(R.id.ch_recur_2);
        Button rb = (Button)mDialog.findViewById(id);

        gRecurGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.e("aa", "in recur change");
                selectRadioButton();
            }
        });


        mSureButton.setOnClickListener(this);
        Log.e("aa", "in recur3");
        mCloseDialog.setOnClickListener(this);
        Log.e("aa", "in recur4");

//        RadioButton rb = (RadioButton)this.findViewById(gRecurGroup.getCheckedRadioButtonId());
//        Log.e("aa", rb.toString());



    }

    private void selectRadioButton() {

        Log.e("aa", "in recur5");

        int id = gRecurGroup.getCheckedRadioButtonId();
        RadioButton rb = (RadioButton)mDialog.findViewById(id);
        System.out.println(gRecurGroup);
        System.out.println(rb);
        Log.e("aa", gRecurGroup.toString());
        Log.e("aa", rb.toString());
        Log.e("aa","in recur6");
        recurChooseInterface.getRecur(rb.getText().toString());



    }


@Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sure_btn2://确定选择按钮监听
//                int id = gRecurGroup.getCheckedRadioButtonId();
//                RadioButton rb = (RadioButton)mDialog.findViewById(id);
//                recurChooseInterface.getRecur(rb.getText().toString());
                dismissDialog();
                break;
            case R.id.date_choose_close_btn2://关闭日期选择对话框
//                int id1 = gRecurGroup.getCheckedRadioButtonId();
//                RadioButton rb1 = (RadioButton)mDialog.findViewById(id1);
//                recurChooseInterface.getRecur(rb1.getText().toString());
                dismissDialog();
                break;

            default:
                break;
        }
    }


    public void showRecurChooseDialog() {

        if (Looper.myLooper() != Looper.getMainLooper()) {

            return;
        }

        if (null == mContext || ((Activity) mContext).isFinishing()) {

            // 界面已被销毁
            return;
        }

        if (null != mDialog) {

            mDialog.show();
            return;
        }

        if (null == mDialog) {

            return;
        }

        mDialog.setCanceledOnTouchOutside(true);
        mDialog.show();

    }



    private void dismissDialog() {

        if (Looper.myLooper() != Looper.getMainLooper()) {

            return;
        }

        if (null == mDialog || !mDialog.isShowing() || null == mContext
                || ((Activity) mContext).isFinishing()) {

            return;
        }

        mDialog.dismiss();
        this.dismiss();
    }

    public interface RecurChooseInterface{
        void getRecur(String time);
    }
}
