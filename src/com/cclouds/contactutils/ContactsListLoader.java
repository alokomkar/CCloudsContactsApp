package com.cclouds.contactutils;

import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

public class ContactsListLoader extends AsyncTaskLoader<List<ContactsItem>>{

	Context mContext;
	List<ContactsItem> mContactItems;
	String TAG = getClass().getSimpleName();
	
	public ContactsListLoader(Context context) {
		super(context.getApplicationContext());
		this.mContext = context;
	}

	@Override
	public List<ContactsItem> loadInBackground() {
		ContentResolver contentResolver = mContext.getContentResolver();
		mContactItems = new ContactsLister(contentResolver).getContactsFromDB();
		Collections.sort(mContactItems, ALPHA_COMPARATOR);
		return mContactItems;
	}

	@Override
	public void deliverResult(List<ContactsItem> contactItems) {
		
		if (isReset()) {
			Log.w(TAG, "+++ Warning! An async query came in while the Loader was reset! +++");
			if (contactItems != null) {
				return;
			}
		}

		// Hold a reference to the old data so it doesn't get garbage collected.
		// protect it until the new data has been delivered.
		List<ContactsItem> oldContacts = mContactItems;
		mContactItems = contactItems;

		if (isStarted()) {
			Log.i(TAG, "+++ Delivering results to the LoaderManager for" +
					" the ListFragment to display! +++");
			// If the Loader is in a started state, have the superclass deliver the
			// results to the client.
			super.deliverResult(contactItems);
		}

		// Invalidate the old data as we don't need it any more.
		if (oldContacts != null && oldContacts != contactItems) {
			Log.i(TAG, "+++ Releasing any old data associated with this Loader. +++");
		}
	}

	@Override
	protected void onStartLoading() {
		Log.i(TAG, "+++ onStartLoading()  +++");

		if (mContactItems != null) {
			// Deliver any previously loaded data immediately.
			Log.i(TAG, "+++ Delivering previously loaded data to the client...");
			deliverResult(mContactItems);
		}
		if (takeContentChanged()) {
			Log.i(TAG, "+++ A content change has been detected... so force load! +++");
			forceLoad();
		} else if (mContactItems == null) {
			Log.i(TAG, "+++ The current data is data is null... so force load! +++");
			forceLoad();
		}
	}

	@Override
	protected void onStopLoading() {
		Log.i(TAG, "+++ onStopLoading()  +++");
		cancelLoad();
	}

	@Override
	protected void onReset() {
		Log.i(TAG, "+++ onReset()  +++");
		// Ensure the loader is stopped.
		onStopLoading();
		// At this point we can release the resources associated with 'ContactItems'.
		if (mContactItems != null) {
			mContactItems = null;
		}
	}

	@Override
	public void onCanceled(List<ContactsItem> contactItems) {
		Log.i(TAG, "+++ onCanceled()  +++");
		super.onCanceled(contactItems);
	}

	@Override
	public void forceLoad() {
		Log.i(TAG, "+++ forceLoad()  +++");
		super.forceLoad();
	}

	/**
	 * Performs alphabetical comparison of ContactsItem objects. This is
	 * used to sort queried data in loadInBackground.
	 */
	private static final Comparator<ContactsItem> ALPHA_COMPARATOR = new Comparator<ContactsItem>() {
		Collator sCollator = Collator.getInstance();

		@Override
		public int compare(ContactsItem contactsItem1, ContactsItem contactsItem2) {
			return sCollator.compare(contactsItem1.toString(), contactsItem2.toString());
		}
	};

}
