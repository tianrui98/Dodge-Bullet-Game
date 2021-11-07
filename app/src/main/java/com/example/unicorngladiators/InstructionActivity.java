package com.example.unicorngladiators;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class InstructionActivity extends Activity implements View.OnClickListener {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instruction_activity);
        Button backBtn = (Button) findViewById(R.id.okBtn);
        backBtn.setOnClickListener(this);
        ImageView instructions =(ImageView) findViewById(R.id.imageView9);
        instructions.setImageResource(R.drawable.demo);//set the source in java class

    }

    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.okBtn:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
