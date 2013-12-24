package org.lxndr.notes;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;


public class NoteActivity extends Activity {	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_note_activity);
        
        TextView bodyView = (TextView) findViewById(R.id.note_body);
        
        setTitle("Note: " + getIntent().getStringExtra("title"));
        bodyView.setText(getIntent().getStringExtra("body"));
    }
	
}
