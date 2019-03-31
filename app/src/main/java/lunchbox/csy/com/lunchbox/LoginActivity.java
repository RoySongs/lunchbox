package lunchbox.csy.com.lunchbox;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

import lunchbox.csy.com.lunchbox.model.BasicResult;
import lunchbox.csy.com.lunchbox.model.SignUpData;
import lunchbox.csy.com.lunchbox.model.SignUpResult;
import lunchbox.csy.com.lunchbox.remote.RemoteService;
import lunchbox.csy.com.lunchbox.remote.ServiceGenerator;
import lunchbox.csy.com.lunchbox.util.SharedPreferenceUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends Activity {

    private static String TAG = "AAAAA";
    long timeLeftInMillionSeconds = 1000 * 60 * 3;
    int backCount = 0;

    String email;
    String username;
    String password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // SharedPreference Util Class 를 만들어 앱전체에서 사용하기 위함임.
        SharedPreferenceUtil spUtil = new SharedPreferenceUtil(this);
        if (!TextUtils.isEmpty(spUtil.getSharedMyId()) && !TextUtils.isEmpty(spUtil.getSharedPassword())) {
            GlobalUser.getInstance().login(this, spUtil.getSharedMyId(), spUtil.getSharedPassword());
        }

        initFirst();
    }

    public void initFirst() {
        setContentView(R.layout.activity_login);
        Button btnCreate = findViewById(R.id.btn_email);
        Button btn_login = findViewById(R.id.btn_login);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initEmailValidView();
            }
        });

//        btn_login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                initLoginView();
//            }
//        });

    }


    private void initEmailValidView() {
        setContentView(R.layout.activity_first_email_valid);

        EditText etEmail = findViewById(R.id.et_email);
        Button btnSend = findViewById(R.id.btn_send);
        final EditText etCode = findViewById(R.id.et_code);
        final TextView tvCountDown = findViewById(R.id.tv_timecount);
        final Button btnNext = findViewById(R.id.btn_next);

        btnNext.setClickable(false);
        tvCountDown.setVisibility(View.INVISIBLE);

        // If you run Activity backwards, get the email address that the user input already.
        if (email != null) {
            etEmail.setText(email);
        }

        etEmail.setImeOptions(EditorInfo.IME_ACTION_DONE);
        // Why it's not working?
        etEmail.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_DONE) {
                btnNext.performClick();
            }
            return false;
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               email = etEmail.getText().toString();

                // Check validation email by Server if no problem, then go to next view.
                try {
                    sendVerificationEmail(email, btnNext, tvCountDown);
                } catch (NullPointerException e) {
                    Toast.makeText(LoginActivity.this, "Please input correect email address.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyEmailAndCode(etEmail.getText().toString(), etCode.getText().toString());
            }
        });
    }

    private void sendVerificationEmail(String email, final Button btnNext, TextView countDownTimerTV) {
        Log.d(TAG, "sendVerificationEmail()= to(email): " + email);

        // Check email Regex
        if (!validEmail(email)) {
            Toast.makeText(LoginActivity.this, "This is not a correct email format.", Toast.LENGTH_SHORT).show();
            return;
        }

        // backCount 세지 않음.
        RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);

        Call<BasicResult> call = null;
        try {
            call = remoteService.validateEmail(email);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        try {
            call.enqueue(new Callback<BasicResult>() {
                @Override
                public void onResponse(Call<BasicResult> call, Response<BasicResult> response) {
                    Log.d(TAG, "onResponse()");
                    try {
                        BasicResult validResult = response.body();
                        Log.d(TAG, validResult.toString());

                        switch (validResult.getCode()) {
                            case 100:
                                btnNext.setClickable(true);
                                btnNext.setBackgroundColor(getResources().getColor(R.color.link_blue));
                                countDownTimerTV.setVisibility(View.VISIBLE);
                                BasicCountDownTimer basicCountDownTimer = BasicCountDownTimer.getInstance(LoginActivity.this);
                                if (!basicCountDownTimer.isTimerRunning()) { // not running. initialize.
                                    basicCountDownTimer.setCountDownTimerFormat("Verify your email in: (", ":", ")");
                                    basicCountDownTimer.setTimeLeftInMilliseconds(timeLeftInMillionSeconds);
                                    basicCountDownTimer.startTimer(countDownTimerTV, btnNext);
                                } else { // running
                                    basicCountDownTimer.stopTimer();
                                    basicCountDownTimer.setTimeLeftInMilliseconds(timeLeftInMillionSeconds);
                                    basicCountDownTimer.startTimer(countDownTimerTV, btnNext);
                                }
                                Toast.makeText(LoginActivity.this, validResult.getMessage(), Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Toast.makeText(LoginActivity.this, validResult.getMessage(), Toast.LENGTH_SHORT).show();
                                break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<BasicResult> call, Throwable throwable) {
                    throwable.printStackTrace();
                    Toast.makeText(LoginActivity.this, throwable.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        Log.d(TAG, String.valueOf(pattern));
        Log.d(TAG, String.valueOf(pattern.matcher(email).matches()));

        return pattern.matcher(email).matches();
    }


    public void verifyEmailAndCode(String email, String code) {

        RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);

        Call<BasicResult> call = remoteService.verifyEmailAndCode(email, code);
        call.enqueue(new Callback<BasicResult>() {
            @Override
            public void onResponse(Call<BasicResult> call, Response<BasicResult> response) {
                BasicResult basicResult = response.body();

                switch (basicResult.getCode()) {
                    case 100:
                        Toast.makeText(LoginActivity.this, basicResult.getMessage(), Toast.LENGTH_SHORT).show();
                        initCreateUserView(email);
                        break;
                    default:
                        Toast.makeText(LoginActivity.this, basicResult.getCode() + ": " + basicResult.getMessage(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<BasicResult> call, Throwable throwable) {
                throwable.printStackTrace();
                Toast.makeText(LoginActivity.this, throwable.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 검증 후, id, password 를 입력하는 화면
    private void initCreateUserView(String email) {

        Log.d(TAG, "initCreateUserView()= email: " + email);

        backCount = -1;
        setContentView(R.layout.activity_first_create);
        EditText etUsername = findViewById(R.id.et_fullname);
        EditText etPassword = findViewById(R.id.et_password);
        Button btnContinue = findViewById(R.id.btn_continue);

        // If the user ever input username or password before, keep them again.
        if (username != null) {
            etUsername.setText(username);
        }

        if (password != null) {
            etPassword.setText(password);
        }

        etPassword.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                btnContinue.performClick();
            }
            return false;
        });

        btnContinue.setOnClickListener((v) -> {
            username = etUsername.getText().toString();
            password = etPassword.getText().toString();

            // Check if username is empty
            if (TextUtils.isEmpty(username)) {
                Toast.makeText(LoginActivity.this, "Please input username.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Check if password is empty
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(LoginActivity.this, "Please input password.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Check email Regex
            if (!validUsername(username)) {
                Toast.makeText(LoginActivity.this, "You can only use a-z, 0-9, _ for username.", Toast.LENGTH_SHORT).show();
                return;
            }


            // Check password Regex
            if (!validPassword(password)) {
                Toast.makeText(LoginActivity.this, "Password needs to be included at least one number, one lower case letter, one upper case letter, one special character and longer than 8 without any spaces.", Toast.LENGTH_SHORT).show();
                return;
            }


            try {
                SignUpData signUpData = new SignUpData(email, username, password);

                RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);

                Call<SignUpResult> call = remoteService.insertMember(signUpData);
                call.enqueue(new Callback<SignUpResult>() {
                    @Override
                    public void onResponse(Call<SignUpResult> call, Response<SignUpResult> response) {
                        SignUpResult signUpResult = response.body();
                        try {
                            Log.d("BBBBB", String.valueOf(signUpResult.code));
                            switch (signUpResult.code) {
                                case 100:
                                    try {
                                        GlobalUser.getInstance().login(LoginActivity.this, username, password);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        Toast.makeText(LoginActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                    break;
                                default:
                                    Toast.makeText(LoginActivity.this, signUpResult.code + ": " + signUpResult.message, Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<SignUpResult> call, Throwable throwable) {
                        throwable.printStackTrace();
                        Toast.makeText(LoginActivity.this, throwable.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(LoginActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validUsername(String username) {
        String USERNAME_PATTERN = "^[A-Za-z0-9_]+$";
        Pattern pattern = Pattern.compile(USERNAME_PATTERN);
        return pattern.matcher(username).matches();
    }

    private boolean validPassword(String password) {
        String PASSWORD_PATTERN = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        return pattern.matcher(password).matches();
    }

}
