package com.example.kejin.iot_demo;

/**
 * Created by kejin on 26/03/2018.
 */

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

public class AmentityChooseDialog extends Dialog implements View.OnClickListener {

    private Dialog mDialog;
    private Context mContext;
    private Button mSureButton;
    private Button mCloseDialog;
    private AmenityChooseInterface amenityChooseInterface;
    private RadioGroup radioGroup;



    public AmentityChooseDialog(Context context, AmenityChooseInterface amenityChooseInterface){

        super(context);
        Log.e("aa", "in recur");
        this.mContext = context;
        this.amenityChooseInterface =amenityChooseInterface;
        this.mDialog = new Dialog(context, R.style.dialog);
        Log.e("aa", "in recur1");
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_amenities_choose, null);
        mDialog.setContentView(view);

        Log.e("aa", "in recur2");
        mCloseDialog = (Button) view.findViewById(R.id.date_choose_close_btn3);
        mSureButton = (Button) view.findViewById(R.id.sure_btn3);
        radioGroup = (RadioGroup) view.findViewById(R.id.gamenity_group);

        mSureButton.setOnClickListener(this);
        Log.e("aa", "in recur3");
        mCloseDialog.setOnClickListener(this);
        Log.e("aa", "in recur4");


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.e("aa", "in recur change");
                selectRadioButton();
            }
        });


    }

    private void selectRadioButton() {

        Log.e("aa", "in recur5");

        int id = radioGroup.getCheckedRadioButtonId();
        RadioButton rb = (RadioButton)mDialog.findViewById(id);
        amenityChooseInterface.getAmenity(rb.getText().toString());



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sure_btn3://确定选择按钮监听

                dismissDialog();
                break;
            case R.id.date_choose_close_btn3://关闭日期选择对话框
                dismissDialog();
                break;

            default:
                break;
        }
    }

    public void showAmenityChooseDialog() {

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


    public interface AmenityChooseInterface{
        void getAmenity(String amenity);
    }
}
