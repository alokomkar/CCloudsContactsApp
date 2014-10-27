package com.cclouds.contactutils;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;

public class ContactsLister {

	Activity mActivity;
	public ContactsLister( Activity activity ) {
		this.mActivity = activity;
	}

	public List<ContactsItem> getContactsFromDB( ) {

		ContentResolver contentResolver = mActivity.getContentResolver();
		Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,
				null, null, null, null);

		ArrayList<ContactsItem> contactsItems = new ArrayList<ContactsItem>();
		if( cursor.getCount() > 0 ) {
			ContactsItem contactsItem = null;
			String id, photoURL;
			Cursor pCursor, eCursor;
			while( cursor.moveToNext() ) {

				contactsItem = new ContactsItem();
				id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
				photoURL = cursor.getString(cursor.getColumnIndex(Utils.hasHoneycomb() ? Contacts.PHOTO_THUMBNAIL_URI : Contacts._ID));
				
				contactsItem.mContactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
				contactsItem.mImageURL = photoURL;
				
				if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
					// get the phone number
					pCursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
							ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
							new String[]{id}, null);
					
					contactsItem.mContactNumber = getContactNumberFromDB( pCursor );
					
					pCursor.close();

					eCursor = contentResolver.query(
							ContactsContract.CommonDataKinds.Email.CONTENT_URI,
							null,
							ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
							new String[]{id}, null);

					contactsItem.mEmail = getEmailFromDB( eCursor );
					
					eCursor.close();
				}
				contactsItems.add(contactsItem);
			}
			cursor.close();
		}
		return contactsItems;
	}

	private String getEmailFromDB(Cursor eCursor) {
		String email;
		if(eCursor.moveToFirst()) {
			email = eCursor.getString(
					eCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
			return (email == null || email.length() == 0 ) ? "" : email; 
		}
		else {
			return ""; 
		}
	}

	private String getContactNumberFromDB(Cursor pCursor) {
		String phone;
		if( pCursor.moveToFirst() )  {
			phone = pCursor.getString(
					pCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
			return (phone == null || phone.length() == 0 ) ? "" : phone; 
		}
		else {
			return ""; 
		}
	}

}
