package com.springcamp.rostykboiko.rada3.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.springcamp.rostykboiko.rada3.MainActivity;
import com.springcamp.rostykboiko.rada3.R;

public class EditorActivity extends AppCompatActivity {

    private EditText editTitle;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        ImageView backButton = (ImageView) findViewById(R.id.backBtn);
        editTitle = (EditText) findViewById(R.id.txtTitle);

        backButton.setOnClickListener(Global_OnClickListener);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(EditorActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

        super.onBackPressed();
    }

    private final View.OnClickListener Global_OnClickListener = new View.OnClickListener() {
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.backBtn:
                    onBackPressed();
                    break;
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(EditorActivity.this, SettingsActivity.class);
            startActivity(intent);
        }
        if (id == R.id.action_delete) {
            return true;
        }
        if (id == R.id.action_share) {
            return true;
        }
        if (id == R.id.backBtn) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
