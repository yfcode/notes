package org.lxndr.notes;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	static final int REQUEST_ADD_NOTE = 1;
	static final int REQUEST_EDIT_NOTE = 2;
	
	private Storage m_Storage;
	private ItemAdapter m_Adapter;
	private long m_CurrentFolder = 0;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        
        m_Storage = new Storage(this);
        m_Storage.init();
        
        m_Adapter = new ItemAdapter(this);
        ListView list = (ListView) findViewById(R.id.item_list);
        list.setAdapter(m_Adapter);
        
        showFolder(m_CurrentFolder);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.add_note_menu:
        	addNote();
            return true;
        case R.id.edit_note_menu:
            editNote();
            return true;
        case R.id.delete_note_menu:
        	deleteNote();
        	return true;
        case R.id.add_folder_menu:
        	addFolder();
        	return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
    
    private void addFolder() {
    	AlertDialog.Builder dialog = new AlertDialog.Builder(this);
    	dialog.setTitle("Alert Dialog With EditText");
    	dialog.setMessage("Enter Your Name Here");
    	final EditText input = new EditText(this);
    	dialog.setView(input);
    	final Context context = this;
        
    	dialog.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
             //You will get as string input data in this variable.
             // here we convert the input to a string and show in a toast.
             String srt = input.getEditableText().toString();
             Toast.makeText(context, srt, Toast.LENGTH_LONG).show();
            }
        });
    	
    	AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }
    
    
    private void addNote() {
    	Intent i = new Intent(this, EditNoteActivity.class);
    	i.putExtra("id", (long) -1);
    	startActivityForResult(i, REQUEST_ADD_NOTE);
    }
    
    
    private void editNote() {
    	Intent i = new Intent(this, EditNoteActivity.class);
    	i.putExtra("id", (long) -1);
    	startActivityForResult(i, REQUEST_EDIT_NOTE);
    }
    
    
    private void deleteNote() {
    	int id = 0;
    	m_Storage.deleteNote(id);
    }
    
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	switch (requestCode) {
    	case REQUEST_ADD_NOTE:
    		if (resultCode == RESULT_OK) {
    			m_Storage.addNote(m_CurrentFolder,
    					data.getStringExtra("title"),
    					data.getStringExtra("body"));
    			showFolder(m_CurrentFolder);
    		}
    		break;
    	case REQUEST_EDIT_NOTE:
    		if (resultCode == RESULT_OK) {
    			m_Storage.editNote(data.getLongExtra("id", 0),
    					data.getStringExtra("title"),
    					data.getStringExtra("body"));
    			showFolder(m_CurrentFolder);
    		}
    		break;
    	}
    }
    
    
    public void showFolder(long folder) {
    	m_CurrentFolder = folder;
    	
    	m_Adapter.clear();
    	m_Adapter.addAll(m_Storage.getFolderList(folder));
    	m_Adapter.addAll(m_Storage.getNoteList(folder));
    }
    
}
