package dev.proj.shoppinglist;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NewListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_list);

        EditText etListTitle = findViewById(R.id.etListTitle);
        Button btnOK = findViewById(R.id.buttonOK);
        TextView tvListTitle = findViewById(R.id.tvListTitle);
        RadioButton radioBtnYES = findViewById(R.id.radioButtonYes);
        RadioButton radioBtnNO = findViewById(R.id.radioButtonNo);
        Button btnSave = findViewById(R.id.buttonSave);

        // get username from WelcomeActivity, so it can be passed when returning to WelcomeActivity
        Bundle bundleGet = getIntent().getExtras();
        String username = bundleGet.getString("user");

        // on OK button click, check if list title is empty
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String listTitle = etListTitle.getText().toString();

                if (listTitle.isEmpty()){  // check if the user entered a list title
                    Toast.makeText(NewListActivity.this, "Choose a name for your list!",
                            Toast.LENGTH_SHORT).show();
                    tvListTitle.setTextColor(Color.RED);
                } else {
                    if (view != null) {  // automatically hide keyboard after entering a list title
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context
                                .INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    tvListTitle.setTextColor(getColor(R.color.colorDark));
                    tvListTitle.setText(listTitle);
                }
            }
        });

        // on SAVE button click, return to WelcomeActivity with the same username
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eListTitle = etListTitle.getText().toString();  // EditText list title
                String tListTitle = tvListTitle.getText().toString();  // TextView list title

                // 1) check if a list title is empty
                // 2) check if one of radio buttons isn't checked
                // 3) check if the user has saved the changes to his list title
                if (eListTitle.isEmpty()){
                    Toast.makeText(NewListActivity.this, "Choose a name for your list!",
                            Toast.LENGTH_SHORT).show();
                    tvListTitle.setTextColor(Color.RED);
                } else if (!radioBtnYES.isChecked() && !radioBtnNO.isChecked()){
                    Toast.makeText(NewListActivity.this, "Choose share list option!",
                            Toast.LENGTH_SHORT).show();
                } else if (!eListTitle.equals(tListTitle)) {
                    Toast.makeText(NewListActivity.this, "Click OK to save list title!",
                            Toast.LENGTH_SHORT).show();
                    tvListTitle.setTextColor(Color.RED);
                } else {
                    Toast.makeText(NewListActivity.this, "List " + tListTitle + " created",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(NewListActivity.this, WelcomeActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("user", username);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }
}
