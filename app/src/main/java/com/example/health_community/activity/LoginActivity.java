package com.example.health_community.activity;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.health_community.R;
import com.example.health_community.model.User;
import com.example.health_community.util.Constant;
import com.example.health_community.util.HttpUtil;
import com.example.health_community.util.JSonUtil;
import com.example.health_community.util.NormalUtil;
import com.example.health_community.util.SPUtils;
import com.syd.oden.circleprogressdialog.core.CircleProgressDialog;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    private String type;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    java.sql.Date sqlDate = new java.sql.Date(formatter.parse(formatter.format(new Date())).getTime());
    ;
    // Keep track of the login task to ensure we can cancel it if requested.
    private UserLoginTask mAuthLoginTask = null;
    private UserRegisterTask mAuthRegisterTask = null;
    private LinearLayout linearLayout;
    private AutoCompleteTextView mUserAccountACTView;
    private TextInputLayout input_user_name, input_user_account, input_password, input_verify;
    private EditText mUserNameView, mPasswordView, mVerifyView;
    private ImageView verifyCode;
    private User user;
    private View mProgressView;
    private View mLoginFormView;
    private Button login_button;
    CircleProgressDialog circleProgressDialog;
    private String verify;
    private Button forgot_password;
    private Button register;
    boolean click_register,click_login;

    public LoginActivity() throws ParseException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    public void initView() {
        mLoginFormView = findViewById(R.id.form_login);
        mProgressView = findViewById(R.id.progress_login);
        mUserAccountACTView = findViewById(R.id.tv_user_account);
        mUserNameView = findViewById(R.id.tv_user_name);
        mPasswordView = findViewById(R.id.tv_password);
        linearLayout = findViewById(R.id.register_ll);
        mVerifyView = findViewById(R.id.tv_verify);
        verifyCode = findViewById(R.id.verify_code);
        input_user_name = findViewById(R.id.input_user_name);
        input_user_account = findViewById(R.id.input_user_account);
        input_password = findViewById(R.id.input_password);
        input_verify = findViewById(R.id.input_verify);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == getResources().getInteger(R.integer.action_sign_in) || id == EditorInfo.IME_NULL) {
                    if (input_user_name.getVisibility() == View.GONE)
                        attemptLogin("login");
                    else
                        attemptLogin("register");
                    return true;
                } else if (id == getResources().getInteger(R.integer.action_verify) || id == EditorInfo.IME_NULL) {
                    attemptLogin("register");
                    return true;
                }
                return false;
            }
        });

        login_button = findViewById(R.id.btn_login);
        login_button.setOnClickListener(this);
        verifyCode.setOnClickListener(this);
        forgot_password = findViewById(R.id.btn_forgot_password);
        forgot_password.setOnClickListener(this);
        register = findViewById(R.id.btn_forgot_register);
        register.setOnClickListener(this);
        type = "login";
        circleProgressDialog = new CircleProgressDialog(this);
        //可对对话框的大小、进度条的颜色、宽度、文字的颜色、内容等属性进行设置
        circleProgressDialog.setDialogSize(25);
        circleProgressDialog.setProgressColor(getResources().getColor(R.color.colorAccent));
        circleProgressDialog.setProgressColor(Color.BLUE);
        circleProgressDialog.setText("loading...");
        input_user_account.setFocusable(true);
//        mUserAccountACTView.setFocusable(true);
//        mUserAccountACTView.setFocusableInTouchMode(true);
    }

    private void showCircleProgressDialog(boolean show) {
        if (show)
        circleProgressDialog.showDialog();  //显示对话框
        //显示过程中可根据状态改变文字内容及颜色
//        circleProgressDialog.changeText("erro:...");
//        circleProgressDialog.changeTextColor(Color.parseColor("##EB0000"));
        else
        circleProgressDialog.dismiss();//关闭对话框
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                attemptLogin(type);
//                Toast.makeText(this, "sdafasd", Toast.LENGTH_SHORT).show();
                break;
            case R.id.verify_code:
//                Glide.with(this).load("http://192.168.137.1:8080/JSoupDemo/user/valicode.do?" + new Date().getTime()).into(verifyCode);
                Glide.with(this).load(Constant.VALICOE_URL + new Date().getTime()).into(verifyCode);
                break;
            case R.id.btn_forgot_password:
                Snackbar.make(v, getString(R.string.snackbar_forgot_password), Snackbar.LENGTH_LONG)
                        .setAction("^_^", null).show();
                break;
            case R.id.btn_forgot_register:
                //                Snackbar.make(v, getString(R.string.snackbar_register), Snackbar.LENGTH_LONG)
                //                        .setAction("^_^", null).show();
                click_register=!click_register;
                if (click_register)
                attemptRegister();
                else changeToLogin();
                break;
        }
    }

    private void attemptRegister() {
        type = "register";
        input_user_name.setFocusable(true);
        //                input_verify.setFocusedByDefault(true);
        mUserNameView.setFocusable(true);
        mUserNameView.setFocusableInTouchMode(true);
        //                mVerifyView.setFocusedByDefault(true);
        mPasswordView.setImeOptions(EditorInfo.IME_ACTION_GO);
        linearLayout.setVisibility(View.VISIBLE);
        input_user_name.setVisibility(View.VISIBLE);
//        192.168.137.1:8080
//        Glide.with(this).load("http://192.168.137.1:8080/JSoupDemo/user/valicode.do?" + new Date().getTime()).into(verifyCode);
        Glide.with(this).load("https://pydwp.xyz/JSoupDemo/user/valicode.do?" + new Date().getTime()).into(verifyCode);
        login_button.setText("注册");
        register.setText("登录");
//        attemptLogin(type);
//        login_button.setClickable(false);
    }

    private void changeToLogin() {
        type = "login";
        input_user_account.setFocusable(true);
        mUserAccountACTView.setFocusable(true);
        mUserAccountACTView.setFocusableInTouchMode(true);
        linearLayout.setVisibility(View.GONE);
        input_user_name.setVisibility(View.GONE);
        login_button.setText("登录");
    }


    /**
     * Attempts to sign in or register the account specified by the login form. If there are form errors
     * (invalid email, missing fields, etc.), the errors are presented and no actual login attempt is made.
     */
    private void attemptLogin(String type) {
        Log.e("type", type);
//        if (mAuthLoginTask != null||mAuthRegisterTask!=null) {
        if (mAuthLoginTask!= null) {
//            return;
            mAuthLoginTask.cancel(true);
        }
        if (mAuthRegisterTask != null) {
            mAuthRegisterTask.cancel(true);
        }


        // Reset errors.
        input_user_name.setError(null);
        input_password.setError(null);

        String userName = mUserNameView.getText().toString();
        String userAccount = mUserAccountACTView.getText().toString();
        String password = mPasswordView.getText().toString();
        verify = mVerifyView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(userAccount)) {
            input_user_account.setError(getString(R.string.error_no_name));
            focusView = mUserAccountACTView;
            cancel = true;
            //        } else if (!isPhoneValid(userName) && !isEmailValid(userName)) {
        }else if (input_user_name.getVisibility()==View.VISIBLE&&TextUtils.isEmpty(userName)) {
            input_user_name.setError("用户名不得为空！");
            focusView = mUserNameView;
            cancel = true;
        }else if (!isPhoneValid(userAccount)) {
            input_user_account.setError(getString(R.string.error_invalid_account));
            focusView = mUserAccountACTView;
            cancel = true;
        } else if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            input_password.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        } else if (isPhoneValid(userAccount) && TextUtils.isEmpty(password)) {
            input_password.setError(getString(R.string.error_no_password));
            focusView = mPasswordView;
            cancel = true;
        }
        Log.e("cancel", cancel + "");
        if (cancel) {
            focusView.requestFocus();
        } else {
            hideInput(login_button);
//            showProgress(true);
//            showCircleProgressDialog(true);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showCircleProgressDialog(false);
                }
            });
            if (type.equals("login")) {
                mAuthLoginTask = new UserLoginTask(NormalUtil.getRandomString(16), userAccount, userName, password, type, formatter.format(new Date()));
                mAuthLoginTask.execute((Void) null);
            } else {
                mAuthRegisterTask = new UserRegisterTask(NormalUtil.getRandomString(16), userAccount, userName, password, type, formatter.format(new Date()));
                mAuthRegisterTask.execute((Void) null);
            }
        }
    }

    private boolean isPhoneValid(String userName) {
        Pattern p = Pattern.compile("[0-9]*");
        Matcher m = p.matcher(userName);
        //        return m.matches() && userName.length() >= 7 && userName.length() <= 12;
        return m.matches() && userName.length() == 11;
    }

    private boolean isEmailValid(String userName) {
        return userName.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 6 && password.length() <= 20;
    }

    public void hideInput(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

//        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//        mLoginFormView.animate().setDuration(shortAnimTime).alpha(show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//            }
//        });

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }


    private class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mUserId, mUserAccount, mUserName, mUserPassword, mType, mRegisterTime;
        private String responseString;
        boolean success;
        int i;//0:成功 1:账号不存在 注册一个吧 2:账号或密码错误

        UserLoginTask(String user_id, String user_account, String userName, String password, String type, String registerTime){
            mUserId = user_id;
            mUserAccount = user_account;
            mUserName = userName;
            mUserPassword = password;
            mRegisterTime = registerTime;
            mType = type;
            success = false;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            // TODO: register the new account here.
                user = new User(mUserId,  mUserName,mUserAccount,  mUserPassword, mRegisterTime);
                Map<String, Object> map = new HashMap<>();
                map.put("user", user);
                //                map.put("user_name", mUserName);
                //                map.put("user_pass", mUserPassword);
                String json = JSonUtil.mapToJsonGson(map);
                Log.e("login", json);
                //                HttpUtil.sendOkHttpPostRequest(Constant.LOGIN_URL, json, new Callback() {
                HttpUtil.sendOkHttpPostRequest(Constant.LOGIN_URL + "?user_account=" + mUserAccount + "&user_pass=" + mUserPassword, json, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("login_response", "登录失败!");
                        LoginActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "登录失败!", Toast.LENGTH_SHORT).show();
                                SPUtils.setPrefBoolean(Constant.LOGIN_SUCCESS, false);
                            }
                            });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        responseString = response.body().string();
                        Log.e("login_response", responseString);
                        LoginActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (responseString.contains("成功")) {
                                    Toast.makeText(LoginActivity.this, responseString, Toast.LENGTH_SHORT).show();
                                    setUserInSP(true);
                                    success = true;
//                                    finish();
                                } else if (responseString.contains("不存在")) {
                                    Toast.makeText(LoginActivity.this, responseString, Toast.LENGTH_SHORT).show();
                                    SPUtils.setPrefBoolean(Constant.LOGIN_SUCCESS, false);
                                    i=1;
                                    success = false;
                                } else {
                                    Toast.makeText(LoginActivity.this, responseString, Toast.LENGTH_SHORT).show();
                                    i=2;
                                    SPUtils.setPrefBoolean(Constant.LOGIN_SUCCESS, false);
                                    success = false;
                                }
                            }
                        });
                    }
                });
//            success = SPUtils.getPrefBoolean(Constant.LOGIN_SUCCESS, false);
//                return SPUtils.getPrefBoolean(Constant.LOGIN_SUCCESS, false);
            Log.e("success", success + "");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }
            return success;
        }

        private void setUserInSP(boolean s) {
            success = s;
            SPUtils.setPrefBoolean(Constant.LOGIN_SUCCESS, true);
            SPUtils.setPrefString(Constant.USER_NAME, mUserName);
            SPUtils.setPrefString(Constant.USER_ACCOUNT, mUserAccount);
            SPUtils.setPrefString(Constant.USER_ID, mUserId);
            SPUtils.setPrefString(Constant.USER_PASSWORD, mUserPassword);
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthLoginTask = null;
//            showProgress(false);
            if (SPUtils.getPrefBoolean(Constant.LOGIN_SUCCESS, false)) {
                finish();
            } else {
//                showCircleProgressDialog(false);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showCircleProgressDialog(false);
                    }
                });
                switch (i) {
                    case 1:
                        input_user_account.setError(responseString);
                        input_user_account.requestFocus();
                        break;
                    case 2:
                        input_password.setError(getString(R.string.error_incorrect_password));
                        mPasswordView.requestFocus();
                        break;
                }
            }
        }

        @Override
        protected void onCancelled() {
            mAuthLoginTask = null;
            showCircleProgressDialog(false);
//            showProgress(false);
        }
    }

    private class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {

        private final String mUserId, mUserAccount, mUserName, mUserPassword, mType, mRegisterTime;
        private String responseString;
        boolean success;
        int i;//0:成功 1:账号已存在 换一个吧 2:验证码错误

        UserRegisterTask(String user_id, String user_account, String userName, String password, String type, String registerTime) {
            mUserId = user_id;
            mUserAccount = user_account;
            mUserName = userName;
            mUserPassword = password;
            mRegisterTime = registerTime;
            mType = type;
            success = false;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            // TODO: register the new account here.

                user = new User(mUserId, mUserName, mUserAccount, mUserPassword, mRegisterTime);
                Map<String, Object> map = new HashMap<>();
                map.put("user", user);
                map.put("verify", verify);
                String json = JSonUtil.mapToJsonGson(map);
                Log.e("register", json);
                HttpUtil.sendOkHttpPostRequest(Constant.REGISTER_URL, json, new Callback() {
                    boolean success;
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("register_response", "fail!");
                        SPUtils.setPrefBoolean(Constant.LOGIN_SUCCESS, false);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        responseString = response.body().string();
                        Log.e("register_response", responseString);
                        LoginActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (responseString.contains("成功")) {
                                    Toast.makeText(LoginActivity.this, responseString, Toast.LENGTH_SHORT).show();
                                    success = true;
                                    setUserInSP(true);
                                    finish();
                                } else if (responseString.contains("已存在")) {
                                    Toast.makeText(LoginActivity.this, responseString, Toast.LENGTH_SHORT).show();
                                    i=1;
                                    SPUtils.setPrefBoolean(Constant.LOGIN_SUCCESS, false);
                                    success = false;
                                } else {
                                    Toast.makeText(LoginActivity.this, responseString, Toast.LENGTH_SHORT).show();
                                    i=2;
                                    SPUtils.setPrefBoolean(Constant.LOGIN_SUCCESS, false);
                                    success = false;
                                }
                            }
                        });
                    }
                });
//            if (success)
//                return true;
//            else
//                return false;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }
            return success;
//            return SPUtils.getPrefBoolean(Constant.LOGIN_SUCCESS, false);
        }

        private void setUserInSP(boolean s) {
            success = s;
            SPUtils.setPrefBoolean(Constant.LOGIN_SUCCESS, true);
            SPUtils.setPrefString(Constant.USER_NAME, mUserName);
            SPUtils.setPrefString(Constant.USER_ACCOUNT, mUserAccount);
            SPUtils.setPrefString(Constant.USER_ID, mUserId);
            SPUtils.setPrefString(Constant.USER_PASSWORD, mUserPassword);
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthLoginTask = null;
//            showProgress(false);

//            if (success) {
            if (SPUtils.getPrefBoolean(Constant.LOGIN_SUCCESS, false)) {
                finish();
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showCircleProgressDialog(false);
                    }
                });

                switch (i) {
                    case 1:
                        input_user_account.setError(responseString);
                        input_user_account.requestFocus();
                        break;
                    case 2:
                        input_verify.setError(responseString);
                        input_verify.requestFocus();
                        break;
                }
//                input_password.setError(getString(R.string.error_incorrect_password));
//                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthLoginTask = null;
//            showProgress(false);
            showCircleProgressDialog(false);
        }
    }

}
