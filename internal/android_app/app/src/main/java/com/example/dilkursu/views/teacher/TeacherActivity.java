package com.example.dilkursu.views.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dilkursu.GlobalConfig;
import com.example.dilkursu.R;
import com.example.dilkursu.models.Instructor;
import com.example.dilkursu.views.other.SignInActivity;
import com.example.dilkursu.views.student.BranchInfoActivity;

public class TeacherActivity extends AppCompatActivity implements View.OnClickListener {
    TextView edTxt_userName;
    Button btn_showBranch;
    Button btn_timeTable;
    Button btn_myInfos;
    Button btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        defineCurrentUser();
        defineVariables();
        defineListeners();
        initViews();
    }

    private void defineCurrentUser() {
        if (GlobalConfig.currentUser == null) {
            GlobalConfig.InitializeCurrentUser(GlobalConfig.UserType.INSTRUCTOR);
            Intent intent = getIntent();
            String person_id = intent.getStringExtra("person_id");

            Instructor i = null;

            try {
                GlobalConfig.currentUser.setBranchName(GlobalConfig.connection.getBranchName(person_id));
                i = GlobalConfig.connection.getInstructor(person_id);
            } catch (Exception e) {
                GlobalConfig.currentUser.setBranchName("");
            }

            GlobalConfig.connection.bindPerson(GlobalConfig.currentUser, person_id);
            GlobalConfig.connection.bindBranch(GlobalConfig.currentUser.getBranch(), GlobalConfig.currentUser.getBranchName());
            if (i != null)
                ((Instructor) (GlobalConfig.currentUser)).setKnownLanguages(i.getKnownLanguages());

        }
    }

    public void defineVariables() {
        edTxt_userName = findViewById(R.id.TeacherActivity_edTxt_userName);
        btn_showBranch = findViewById(R.id.TeacherActivity_btn_showBranch);
        btn_timeTable = findViewById(R.id.TeacherActivity_btn_timeTable);
        btn_myInfos = findViewById(R.id.TeacherActivity_btn_myInfos);
        btn_logout = findViewById(R.id.TeacherActivity_btn_logout);
    }

    public void defineListeners() {
        btn_myInfos.setOnClickListener(this);
        btn_timeTable.setOnClickListener(this);
        btn_showBranch.setOnClickListener(this);
        btn_logout.setOnClickListener(this);


    }

    private void initViews() {
        String userName = String.format("%s %s", GlobalConfig.currentUser.getFname(), GlobalConfig.currentUser.getLname());
        edTxt_userName.setText(userName);
    }

    @Override
    public void onClick(View v) {
        if (v == btn_showBranch) {
            if (GlobalConfig.currentUser.getBranch().getName() != null) {
                // Handle clicks for BtnShowBranch
                Intent intent = new Intent(getApplicationContext(), BranchInfoActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Şube bilgilerinizi görüntülerken hata oluştu.", Toast.LENGTH_LONG).show();
            }
        } else if (v == btn_timeTable) {
            // Handle clicks for BtnMyPayments
            Intent intent = new Intent(getApplicationContext(), TeacherTimeTableActivity.class);
            startActivity(intent);
        } else if (v == btn_myInfos) {
            // Handle clicks for BtnMyInfos
            Intent intent = new Intent(getApplicationContext(), TeacherInfoActivity.class);
            startActivity(intent);
        } else if (v == btn_logout) {
            Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
            finish();
            startActivity(intent);
        }
    }


}
