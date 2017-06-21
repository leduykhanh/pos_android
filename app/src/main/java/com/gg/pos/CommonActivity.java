package com.gg.pos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class CommonActivity extends AppCompatActivity {

    public static final String BUNDLE_KEY_ACTIVITY_LAYOUT    = "bundle_key_activity_layout";
    public static final String BUNDLE_KEY_TOOLBAR_TITLE      = "bundle_key_toolbar_title";

    public static final String BUNDLE_KEY_TEST_BUTTON_TEXT   = "bundle_key_test_button_text";
    public static final String BUNDLE_KEY_SHOW_HOME_BUTTON   = "bundle_key_show_home_button";
    public static final String BUNDLE_KEY_SHOW_RELOAD_BUTTON = "bundle_key_show_Reload_button";
    public static final String BUNDLE_KEY_SELECTED_INDEX     = "bundle_key_selected_index";

    public static final String SELECTED_MODEL = "bundle_selected_model";

    public static final String BUNDLE_KEY_INDEX = "bundle_key_index";

    public static final String BUNDLE_KEY_PAPER_SIZE = "bundle_key_paper_size";
    public static final String BUNDLE_KEY_LANGUAGE = "bundle_key_language";

    public static final String BUNDLE_KEY_INTERFACE = "bundle_key_interface";

    public static final String BUNDLE_KEY_DRAWER_OPEN_STATUS = "bundle_key_drawer_open_status";

    public static final String BUNDLE_KEY_BLACK_MARK_TYPE_INDEX = "bundle_key_black_mark_type_index";

    public static final String BUNDLE_KEY_PORT_NAME = "intent_key_port_name";
    public static final String BUNDLE_KEY_PORT_SETTINGS = "intent_key_port_settings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        setContentView(intent.getIntExtra(CommonActivity.BUNDLE_KEY_ACTIVITY_LAYOUT, R.layout.fragment_dummy));  // Display a user selected menu list.

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null && intent.getBooleanExtra(CommonActivity.BUNDLE_KEY_SHOW_HOME_BUTTON, false)) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        String toolbarTitle = intent.getStringExtra(CommonActivity.BUNDLE_KEY_TOOLBAR_TITLE);

        if (actionBar != null && toolbarTitle != null) {
            actionBar.setTitle(intent.getStringExtra(CommonActivity.BUNDLE_KEY_TOOLBAR_TITLE));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Intent intent = getIntent();

        if (intent.getBooleanExtra(CommonActivity.BUNDLE_KEY_SHOW_RELOAD_BUTTON, false)) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
