package com.example.shopquanao.Activity;


import android.os.Bundle;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;


import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import com.example.shopquanao.databinding.ActivityRegisterBinding;
import com.example.shopquanao.SERVER;


import java.util.HashMap;
import java.util.Map;



public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.ReBtnRegister.setOnClickListener(v -> {
            register();
        });
    }
    private void register(){
        String hoten = binding.ReEdtHoTen.getText().toString();
        String email = binding.ReEdtEmail.getText().toString();
        String matkhau = binding.ReEdtMatKhau.getText().toString();
        StringRequest request = new StringRequest(Request.Method.POST, SERVER.register,
                        response -> {
                            Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
                            finish();
                        },
                        error -> {
                            Toast.makeText(this, "Lỗi server", Toast.LENGTH_SHORT).show();
                        }
                ){
                    @Override
                    protected Map<String,String> getParams(){
                        Map<String,String> map = new HashMap<>();
                        map.put("hoten", hoten);
                        map.put("email", email);
                        map.put("matkhau", matkhau);
                        return map;
                    }
                };
        Volley.newRequestQueue(this).add(request);
    }
}