package org.lxndr.notes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EditNoteActivity extends Activity {
	
	private long m_ID;
	private TextView m_TitleView;
	private TextView m_BodyView;
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_note_activity);
        
        m_ID = getIntent().getExtras().getLong("id", -1);
        if (m_ID == -1) {
        	setTitle("New note");
        } else {
        	setTitle("Edit note");
        }
        
        m_TitleView = (TextView) findViewById(R.id.edit_note_title);
        m_BodyView = (TextView) findViewById(R.id.edit_note_body);
    }
	
	
	public void onDone(View view) {
		Intent r = new Intent();
		r.putExtra("id", m_ID);
		r.putExtra("title", m_TitleView.getText().toString());
		r.putExtra("body", m_BodyView.getText().toString());
		setResult(RESULT_OK, r);
		finish();
	}
	
}
