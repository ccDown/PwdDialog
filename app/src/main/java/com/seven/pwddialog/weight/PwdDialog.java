package com.seven.pwddialog.weight;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

        private TextView amountTv;

        private TextView ticketTv;

        private PwdDialog mPwdDialog;

        public PwdBuilder(Context context) {
            this.mContext = context;
            mPwdDialog = new PwdDialog(context);
            mView = LayoutInflater.from(mContext).inflate(R.layout.item_pwddialog, null);
            mPwdDialog.setContentView(mView,
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));

            amountTv = mView.findViewById(R.id.tv_amount);
            ticketTv = mView.findViewById(R.id.tv_mobileticket);
            mPwdView = mView.findViewById(R.id.pwdv);
            mPwdView.showOrHideKeyBoardView(true);

            Button cancelBtn = mView.findViewById(R.id.btn_cancel_dialog);
            Button ensureBtn = mView.findViewById(R.id.btn_ensure_dialog);

            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pwdClickListener.cancelClickListener(mPwdDialog,v);
                }
            });

            ensureBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pwdClickListener.ensureClickListener(mPwdDialog,v);
                }
            });
        }

        public PwdBuilder show() {
            mPwdDialog.show();
            return this;
        }

        public PwdBuilder setmAmount(String mAmount) {
            this.mAmount = mAmount;
            amountTv.setText(mAmount);
            return this;
        }

        public PwdBuilder setmMobileTicket(String mMobileTicket) {
            this.mMobileTicket = mMobileTicket;
            ticketTv.setText(mMobileTicket);
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

        private PwdClickListener pwdClickListener;

        public PwdBuilder setPwdClickListener(PwdClickListener pwdClickListener) {
            this.pwdClickListener = pwdClickListener;
            return this;
        }

    }

    public PwdDialog(Context context) {
        super(context);
    }

    public interface PwdClickListener {
        void cancelClickListener(PwdDialog dialog,View v);

        void ensureClickListener(PwdDialog dialog,View v);
    }
}
