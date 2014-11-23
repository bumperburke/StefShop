package ie.mydit.burke.stefan.stefshop;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener
{
	TextView welcome, note1, note2;
	ImageView logo;
	EditText name, age, funds, email;
	RadioGroup sexGroup;
	RadioButton genderM, genderF;
	Spinner job;
	Button start;
	String usr, yrs, bal, eaddr;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Text Fields
		welcome = (TextView)findViewById(R.id.welcome);
		note1 = (TextView)findViewById(R.id.note1);
		note2 = (TextView)findViewById(R.id.note2);
		
		//Logo
		logo = (ImageView)findViewById(R.id.logo);
		
		//Name Field
		name = (EditText)findViewById(R.id.usrName);
		
		//Radio Button
		sexGroup = (RadioGroup)findViewById(R.id.sex);
		
		genderM = (RadioButton)findViewById(R.id.male);
		
		genderF = (RadioButton)findViewById(R.id.female);
		
		//Job Title Spinner
		job = (Spinner)findViewById(R.id.jobSpinner);
		
		//Age Field
		age = (EditText)findViewById(R.id.usrAge);
		
		//Funds Field
		funds = (EditText)findViewById(R.id.usrFunds);
		
		//Email Field
		email = (EditText)findViewById(R.id.usrEmail);
		
		//Button
		start = (Button)findViewById(R.id.start);
		start.setOnClickListener(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings)
		{
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View arg0)
	{	
		//This is to make sure fields contain the required info
		//Reference: http://stackoverflow.com/questions/11535011/edittext-field-is-required-before-moving-on-to-another-activity
		if (name.getText().toString().trim().equals(""))
		{    
			name.setError("Field Required!");
		}
		
		else if (Integer.parseInt(age.getText().toString()) < 18)
		{
			age.setError("You Must Be 18 or Older To Use Stefshop!");
		}
		
		else if (funds.getText().toString().trim().equals(""))
		{
			funds.setError("Field Required!");
		}
		
		else if (email.getText().toString().trim().equals(""))
		{
			email.setError("Field Required!");
		}
		//Reference complete
		
		else
		{
			//Creates an instance of User to pass in intent
			usr = name.getText().toString();
			yrs = age.getText().toString();
			bal = funds.getText().toString();
			eaddr = email.getText().toString();
			User u = new User(usr, yrs, bal, eaddr);
			
			Intent intent = new Intent(MainActivity.this, ItemList.class);
			intent.putExtra("user", u);
			startActivity(intent);
		}
	}
}
