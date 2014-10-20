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
			String email, phone, id, photoURL;
			while( cursor.moveToNext() ) {

				contactsItem = new ContactsItem();
				id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
				photoURL = cursor.getString(cursor.getColumnIndex(Utils.hasHoneycomb() ? Contacts.PHOTO_THUMBNAIL_URI : Contacts._ID));
				contactsItem.mContactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
				contactsItem.mImageURL = photoURL;
				if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
					// get the phone number
					Cursor pCursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
							ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
							new String[]{id}, null);
					if( pCursor.moveToFirst() )  {
						phone = pCursor.getString(
								pCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
						contactsItem.mContactNumber = (phone == null || phone.length() == 0 ) ? "" : phone; 
					}
					else {
						contactsItem.mContactNumber = ""; 
					}
						
					
					pCursor.close();

					Cursor emailCur = contentResolver.query(
							ContactsContract.CommonDataKinds.Email.CONTENT_URI,
							null,
							ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
							new String[]{id}, null);
					if(emailCur.moveToFirst()) {
						email = emailCur.getString(
								emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
						contactsItem.mEmail = (email == null || email.length() == 0 ) ? "" : email; 
					}
					else {
						contactsItem.mEmail =  ""; 
					}
					emailCur.close();

				}
				contactsItems.add(contactsItem);

			}
			cursor.close();
		}
		return contactsItems;
	}

}
