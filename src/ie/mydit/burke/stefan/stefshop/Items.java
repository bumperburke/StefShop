package ie.mydit.burke.stefan.stefshop;

import android.os.Parcel;
import android.os.Parcelable;

public class Items implements Parcelable
{
	private String item;
	private String description;
	private String price;
	private int quantity;
	
	public Items(String i, String d, String p, int q)
	{
		this.item = i;
		this.description = d;
		this.price = p;
		this.quantity = q;
	}

	public void setItem(String item)
	{
		this.item = item;
	}

	public void setDescripton(String descripton)
	{
		this.description = descripton;
	}

	public void setPrice(String price)
	{
		this.price = price;
	}
	
	public void setQuantity(int q)
	{
		this.quantity = q;
	}

	public String getItem()
	{
		return item;
	}
	
	public int getQuantity()
	{
		return quantity;
	}

	public String getDescripton()
	{
		return description;
	}

	public String getPrice()
	{
		return price;
	}

//Parcelable Code for passing objects in Intents
	//Reference: http://www.easyinfogeek.com/2014/01/android-tutorial-two-methods-of-passing.html
	public static final Parcelable.Creator<Items> CREATOR = new Creator<Items>()
			{  
		public Items createFromParcel(Parcel source)
		{  
			return new Items(source);  
		}

		public Items[] newArray(int size)
		{  
			return new Items[size];  
		}
			};  

			private Items(Parcel in)
			{
				this.item = in.readString();
				this.description = in.readString();
				this.price = in.readString();
				this.quantity = in.readInt();
			}

			@Override
			public int describeContents()
			{
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public void writeToParcel(Parcel parcel, int flags)
			{  
				parcel.writeString(item);  
				parcel.writeString(description);  
				parcel.writeString(price);
				parcel.writeInt(quantity);
			}  
	//Reference complete
}
