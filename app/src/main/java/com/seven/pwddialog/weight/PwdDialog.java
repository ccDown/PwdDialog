package com.seven.pwddialog.weight;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.seven.pwddialog.R;

/**
 * @author kuan
 * Created on 2018/9/28.
 * @description
 */
public class PwdDialog extends Dialog {

    public class PwdBuilder {
        private String mTitle;//标题
        private String mAmount;//消费金额
        private String mMobileTicket;//消费积分券
        private String mPassword;//密码
        private Context mContext;
        private View mView;
        private PwdView mPwdView;

        private PwdDialog mPwdDialog;

        public PwdBuilder(Context context) {
            this.mContext = context;
            mPwdDialog = new PwdDialog(context);
            mView = LayoutInflater.from(mContext).inflate(R.layout.item_pwddialog, null);
            mPwdDialog.setContentView(mView,
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));

            TextView amountTv = mView.findViewById(R.id.tv_amount);
            TextView ticketTv = mView.findViewById(R.id.tv_mobileticket);
            mPwdView = mView.findViewById(R.id.pwdv);
            mPwdView.showOrHideKeyBoardView(true);
        }

        public PwdBuilder show(){
            mPwdDialog.show();
            return this;
        }

        public PwdBuilder setmAmount(String mAmount) {
            this.mAmount = mAmount;
            return this;
        }

        public PwdBuilder setmMobileTicket(String mMobileTicket) {
            this.mMobileTicket = mMobileTicket;
            return this;
        }

        public PwdBuilder setmPassword(String mPassword) {
            this.mPassword = mPassword;
            return this;
        }

        public PwdBuilder setmTitle(String mTitle) {
            this.mTitle = mTitle;
            return this;
        }

        public String getmAmount() {
            return mAmount;
        }

        public String getmMobileTicket() {
            return mMobileTicket;
        }

        public String getmPassword() {
            mPassword = mPwdView.getPwd();

            return mPassword;
        }

        public String getmTitle() {
            return mTitle;
        }
    }

    public PwdDialog(Context context) {
        super(context);
    }

}
