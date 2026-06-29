package com.example.shopquanao.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shopquanao.databinding.ActivityLoginBinding;
import com.example.shopquanao.SERVER;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.LogBtnLogin.setOnClickListener(v -> {
                login();
        });
        binding.txtRegister.setOnClickListener(v -> {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
        });
    }

    private void login(){
        String email = binding.LogEdtEmail.getText().toString().trim();
        String matkhau = binding.LogEdtMatKhau.getText().toString().trim();

        StringRequest request = new StringRequest(Request.Method.POST, SERVER.login,
                        response -> {
                            try{
                                JSONObject object = new JSONObject(response);
                                boolean success = object.getBoolean("success");
                                if(success){
                                    Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.putExtra("email", email);
                                    startActivity(intent);
                                    finish();
                                }
                                else {
                                    Toast.makeText(this, object.getString("message"), Toast.LENGTH_SHORT).show();
                                }
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                        },
                error -> {

                    Log.e("VOLLEY_ERROR", error.toString());

                    Toast.makeText(
                            LoginActivity.this,
                            error.toString(),
                            Toast.LENGTH_LONG
                    ).show();

                }
                ){
                    @Override
                    protected Map<String,String> getParams(){
                        Map<String,String> params = new HashMap<>();
                        params.put("email", email);
                        params.put("matkhau", matkhau);
                        return params;
                    }
                };
        Volley.newRequestQueue(this).add(request);
    }
}