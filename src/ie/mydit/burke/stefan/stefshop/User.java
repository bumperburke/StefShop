package ie.mydit.burke.stefan.stefshop;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable
{
	private String name, age, email;
	private float funds;
	
	public User(String name, String age, String funds, String email)
	{
		this.name = name;
		this.age = age;
		this.funds = Float.parseFloat(funds);
		this.email = email;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	public void setAge(String age)
	{
		this.age = age;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	public void setFunds(float funds)
	{
		this.funds = funds;
	}

	public String getName()
	{
		return name;
	}

	public String getAge()
	{
		return age;
	}

	public String getEmail()
	{
		return email;
	}

	public float getFunds()
	{
		return funds;
	}
	
	//Function used in ItemList class to calculate the remaining balance
	public float remBal(float bal, float cost, int qty)
	{
		bal = bal - (cost*qty);
		return bal;
	}
	
//Parcelable Code for passing objects in Intents
	//Reference: The following code is from http://www.easyinfogeek.com/2014/01/android-tutorial-two-methods-of-passing.html
	//Anything that uses Parcelable is a reference to this reference
	public static final Parcelable.Creator<User> CREATOR = new Creator<User>()
	{  
		  public User createFromParcel(Parcel source)
		  {  
		      return new User(source);  
		  }
		  
		  public User[] newArray(int size)
		  {  
		      return new User[size];  
		  }
	};  
	
	private User(Parcel in)
	{
		this.name = in.readString();
		this.age = in.readString();
		this.email = in.readString();
		this.funds = in.readFloat();
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
		  parcel.writeString(name);  
		  parcel.writeString(age);  
		  parcel.writeString(email);
		  parcel.writeFloat(funds);
	}  
	// Reference complete
}
