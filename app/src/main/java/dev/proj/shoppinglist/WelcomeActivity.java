package dev.proj.shoppinglist;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        TextView tvTitle = findViewById(R.id.tvTitle);
        TextView tvUserWelcome = findViewById(R.id.tvUserWelcome);
        Button btnNewList = findViewById(R.id.buttonNewList);
        Button btnSeeLists = findViewById(R.id.buttonSeeLists);

        // get current user's username
        Bundle bundle = getIntent().getExtras();
        String username = bundle.getString("user");

        // change USER text view to current user's username
        tvUserWelcome.setText(username);

        // build an alert dialog for confirmation of making a new list
        btnNewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvTitle.setText(getString(R.string.list_title));
                AlertDialog.Builder builder = new AlertDialog.Builder(WelcomeActivity.this);
                builder.setTitle(R.string.new_list_dialog)
                        .setMessage(R.string.are_you_sure)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(WelcomeActivity.this,
                                        NewListActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("user", username);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });
    }
}
