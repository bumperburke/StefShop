package ie.mydit.burke.stefan.stefshop;

import java.util.ArrayList;
import android.app.ListActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class ItemList extends ListActivity implements OnClickListener
{
	Resources res;
	TextView header, header2, usr, eaddr, funds;
	Button cart, proceed;
	String[] itm, desc, cost;
	String user, age, balance, email;
	User muser;
	ArrayList<Items> itemscart = new ArrayList<Items>();
	float bal;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_list);
		
		//PULL VALUES PASSED TO INTENT
		//Reference to Parcelable in User class
		muser = (User)getIntent().getExtras().getParcelable("user");
		email = muser.getEmail();
		System.out.println("balance: " +bal);
		
		//Above List
		//Setting the text views
		header = (TextView)findViewById(R.id.heading);
		header2 = (TextView)findViewById(R.id.heading2);
		usr = (TextView)findViewById(R.id.usr);
		usr.append(" " + muser.getName());
		eaddr = (TextView)findViewById(R.id.eaddr);
		eaddr.append(" " + muser.getEmail());
		funds = (TextView)findViewById(R.id.funds);
		funds.append(" €" + muser.getFunds());
		
		//Retrieving string array values
		res = getResources();
		itm = res.getStringArray(R.array.item_array);
		desc = res.getStringArray(R.array.item_desc);
		cost = res.getStringArray(R.array.item_price);
		
		
		setListAdapter(new ListCustomAdapter());
		
		//Below List
		proceed = (Button)findViewById(R.id.proceed);
		proceed.setOnClickListener(this);
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

	
	@SuppressWarnings("rawtypes")
	class ListCustomAdapter extends ArrayAdapter
	{
		
		@SuppressWarnings("unchecked")
		ListCustomAdapter()
		{
			super(ItemList.this, R.layout.row, itm);
		}

		@Override
		public View getView(final int position, View arg1, ViewGroup parent)
		{
			View row = arg1;
			
			//Ensures rows are recycled
			if(row == null)
			{
				LayoutInflater inflator = getLayoutInflater();
				row = inflator.inflate(R.layout.row, parent, false);
			}
			
			//Main body of each row
			ImageView pic = (ImageView)row.findViewById(R.id.image);
			
			TextView item = (TextView) row.findViewById(R.id.selection);
			item.setText(itm[position]);
			
			TextView description = (TextView) row.findViewById(R.id.description);
			description.setText(desc[position]);
			
			TextView price = (TextView) row.findViewById(R.id.price);
			price.setText("€" + cost[position]);
			
			final Spinner quantity = (Spinner) row.findViewById(R.id.quantity);
			
			
			final ImageButton cart = (ImageButton) row.findViewById(R.id.cart);
			cart.setImageResource(R.drawable.cart);
			cart.setOnClickListener(new OnClickListener()
			{
				public void onClick(View v)
				{
					bal = muser.getFunds();
					int qty = Integer.parseInt(quantity.getSelectedItem().toString());
					
					//If no quantity selected
					if(qty == 0)
					{
						Toast.makeText(getApplicationContext(), "Please Select A Quantity", Toast.LENGTH_SHORT).show();
					}
					
					float unitcost = Float.parseFloat(cost[position]);
					float newbal;

					//A check if funds are sufficient
					if((bal - (unitcost*qty)) < 0)
					{
						System.out.println("unit cost = "+unitcost);
						String result = String.valueOf(bal);
						funds.setText("Balance: €" + result);
						funds.setTextColor(Color.parseColor("#FF0000"));
						Toast.makeText(getApplicationContext(), "Insufficient Funds!", Toast.LENGTH_SHORT).show();
					}

					//If funds are sufficient do this
					else if((bal - (unitcost*qty)) >= 0)
					{
						//function in User class
						newbal = muser.remBal(bal, unitcost, qty);
						muser.setFunds(newbal);

						String result = String.valueOf(newbal);
						funds.setText("Balance: €" + result);
						funds.setTextColor(Color.parseColor("#000000"));

						//Create an instance of Items and add to Array List
						Items i = new Items(itm[position], desc[position], cost[position], qty);
						itemscart.add(i);

						Toast.makeText(getApplicationContext(), "Added " +qty+ " x " +itm[position]+ " To Cart", Toast.LENGTH_SHORT).show();
					}
				}
			});
			
			//Draw an image where it is suppse to be
			if(itm[position].equals("Avonmore Milk"))
			{
				pic.setImageResource(R.drawable.milk);
			}
			
			else if(itm[position].equals("Brennans Bread"))
			{
				pic.setImageResource(R.drawable.brennans_bread);
			}
			
			else if(itm[position].equals("Bellview Eggs"))
			{
				pic.setImageResource(R.drawable.bellview_eggs);
			}
			
			else if(itm[position].equals("Kellogs Crunchy Nut"))
			{
				pic.setImageResource(R.drawable.crunchy_nut);
			}
			
			else if(itm[position].equals("Coca Cola"))
			{
				pic.setImageResource(R.drawable.coca_cola);
			}
			
			else if(itm[position].equals("Bisto Gravy"))
			{
				pic.setImageResource(R.drawable.bisto);
			}
			
			else if(itm[position].equals("koppaberg"))
			{
				pic.setImageResource(R.drawable.koppaberg);
			}
			
			return (row);
		}
		
	}


	@Override
	public void onClick(View arg0)
	{
		//On click pass the cart and email address in intent
		Intent intent = new Intent(ItemList.this, Checkout.class);
		intent.putExtra("items", itemscart);
		intent.putExtra("email", email);
		startActivity(intent);
	}
	
}