package ie.mydit.burke.stefan.stefshop;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Checkout extends Activity implements OnClickListener
{
	ArrayList<Items> cart = new ArrayList<Items>();
	TextView headn, headp, headq, productinfo, totalcost, vatcost, vattotal;
	float itemcost, sum = 0, sumvat, vat = 0.21f, total;
	int qty, cartq;
	String email, carti, cartp, cartqS, itemtotal, totalvat, totalwvat;
	Button sendmail, finish;
	//Reference: http://examples.javacodegeeks.com/core-java/lang/stringbuilder/java-stringbuilder-example/
	StringBuilder body = new StringBuilder();
	//Reference complete
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.checkout_screen);
		
		//Reference: http://stackoverflow.com/questions/4999991/what-is-a-bundle-in-an-android-application
		Bundle b = getIntent().getExtras();
		//Reference complete
		
		//Reference: linked to other Parcelable references
		cart = b.getParcelableArrayList("items");
		//Reference complete
		email = (String) b.get("email");

		headn = (TextView)findViewById(R.id.headn);
		headp = (TextView)findViewById(R.id.headp);
		headq = (TextView)findViewById(R.id.headq);
		productinfo = (TextView)findViewById(R.id.productinfo);
		
		
		for (int i = 0; i < cart.size(); i++)
		{
			carti = cart.get(i).getItem();
			cartp = cart.get(i).getPrice();
			cartq = cart.get(i).getQuantity();
			productinfo.append(carti+"                  "+cartp+"                   "+cartq+'\n');
			cartqS = String.valueOf(cartq);
			itemcost = Float.parseFloat(cart.get(i).getPrice());
			qty = cart.get(i).getQuantity();
			sum = sum + (itemcost * qty);
			
			//Used To Append The List To A String So It Can Be Easily E-mailed
			body.append(" " +carti); 
			body.append(" " +cartp);
			body.append(" " +cartqS+ " ");
		}
		
		
		//Printing Initial Cost To Screen
		totalcost = (TextView)findViewById(R.id.cost);
		itemtotal = String.valueOf(sum);
		totalcost.setText("Total : €" +itemtotal);
		
		//Finding VAT and Printing To Screen
		vatcost = (TextView)findViewById(R.id.vat);
		sumvat = (sum*vat);
		totalvat = String.valueOf(sumvat);
		vatcost.setText("VAT @ 21%: €" +totalvat);
		
		//Finding Total and Printing To Screen
		vattotal = (TextView)findViewById(R.id.vattotal);
		total = sum + sumvat;
		totalwvat = String.valueOf(total);
		vattotal.setText("Total (incl. VAT): €" +totalwvat);
		
		//Send Email Functionality
		sendmail = (Button)findViewById(R.id.sendEmail);
		sendmail.setOnClickListener(this);
		
		//Finish Button
		finish = (Button)findViewById(R.id.finished);
		finish.setOnClickListener(this);
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
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v)
	{
		if(v.equals(sendmail))
		{
			//Using an Asynchronous task to send receipt to users email
			//Reference: http://stackoverflow.com/questions/14374578/using-asynctask-to-send-android-email
			final GMailSender sender = new GMailSender("stefshopapp@gmail.com", "SBdt2283");
			new AsyncTask<Void, Void, Void>()
			{
		        @Override public Void doInBackground(Void... arg)
		        {
		            try 
		            {     
		            	sender.sendMail("StefShop Receipt",   
	                     "Thank You For Shopping At StefShop \n"
	                     + "Here Is Your Receipt: " +'\n' +body+
	                     '\n'+ "Total: €" +itemtotal+ '\n'+
	                     "VAT: €" +totalvat+ '\n'+
	                     "Total (Incl. VAT): €" +totalwvat+ '\n'+
	                     "We Hope To See You Again Soon.",   
	                     "stefshopapp@gmail.com",
	                     email);
		            } 
		            catch (Exception e) 
		            {   
		            	Log.e("SendMail", e.getMessage(), e);   
		            }
		            
					return null; 
		        }
			}.execute();
			//Reference complete
			
			Toast.makeText(Checkout.this, "Mail Sent Successfully", Toast.LENGTH_LONG).show();
		}
		
		else if(v.equals(finish))
		{
			//This intent finishes the app and returns a user to the main screen
			//Reference: http://stackoverflow.com/questions/4732184/how-to-finish-an-android-application
			Toast.makeText(Checkout.this, "Thank You For Shopping At StefShop", Toast.LENGTH_LONG).show();
			Intent intent = new Intent(getApplicationContext(), MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			//Reference Complete
		}
	}
	
}
