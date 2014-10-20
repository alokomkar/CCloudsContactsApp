package com.cclouds.contactutils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.cclouds.ccloudscontactsapp.R;

@SuppressLint("DefaultLocale")
public class CustomContactArrayAdapter extends ArrayAdapter<ContactsItem> implements Filterable, SectionIndexer {

	private List<ContactsItem> mContactsItems;
	private LayoutInflater mInflater;
	private CustomContactFilter mCustomContactFilter;
	private HashMap<String, Integer> mapIndex;
	private String[] mSections;
	
	String TAG = getClass().getSimpleName();

	public CustomContactArrayAdapter(Context context) {
		super(context, R.layout.contact_item);
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mapIndex = new LinkedHashMap<String, Integer>();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View view;
		ViewHolderItem viewHolderItem;
		Log.i(TAG, "+++ In getView +++");
		if (convertView == null) {
			Log.i(TAG, "+++ Initializing View for the First Time +++");
			view = mInflater.inflate(R.layout.contact_item, parent, false);
			viewHolderItem = new ViewHolderItem();
			viewHolderItem.mContactNameTextView = (TextView) view.findViewById(R.id.nameValueTxtView);
			viewHolderItem.mContactNumberTextView = (TextView) view.findViewById(R.id.contactNumberValueTxtView);
			viewHolderItem.mEmailTextView = (TextView) view.findViewById(R.id.emailValueTxtView);
			viewHolderItem.mProfileImageView = (ImageView) view.findViewById(R.id.profileImageView);
			view.setTag(viewHolderItem);

		} else {
			view = convertView;
			viewHolderItem = (ViewHolderItem) view.getTag();
			//view.setTag(viewHolderItem);
		}

		ContactsItem item = getItem(position);
		viewHolderItem.mContactNameTextView.setText(item.mContactName);
		viewHolderItem.mContactNumberTextView.setText(item.mContactNumber);
		viewHolderItem.mEmailTextView.setText(item.mEmail);

		return view;
	}

	@Override 
	public ContactsItem getItem( int position ) {
		return mContactsItems.get(position);
	}

	@Override
	public int getCount() {
		if( mContactsItems != null ) {
			return mContactsItems.size();
		}
		return 0;
	}

	public class ViewHolderItem {
		TextView mContactNameTextView;
		TextView mContactNumberTextView;
		TextView mEmailTextView;
		ImageView mProfileImageView;
	}

	public void setData( List<ContactsItem> contactsItem ) {
		clear();
		Log.i(TAG, "+++ Initializing Data +++");
		mContactsItems = contactsItem;
		mContactsItems = contactsItem;
		
		String character = null;
		if( mContactsItems == null ) {
			return;
		}
		for( int index = 0; index < mContactsItems.size(); index++ ) {
			character = mContactsItems.get(index).toString().substring(0, 1).toUpperCase(Locale.US);
			mapIndex.put(character, index);
		}
		
		Set<String> sectionLetters = mapIndex.keySet();
		 
        // create a list from the set to sort
        ArrayList<String> sectionList = new ArrayList<String>(sectionLetters);
 
        Log.d("sectionList", sectionList.toString());
        Collections.sort(sectionList);
 
        mSections = new String[sectionList.size()];
 
        sectionList.toArray(mSections);
	}

	@Override
	public Filter getFilter() {
		if( mCustomContactFilter == null ) {
			mCustomContactFilter = new CustomContactFilter();
		}
		return mCustomContactFilter;
	}

	/**
	 * Custom Filter implementation
	 * */
	public class CustomContactFilter extends Filter {

		@Override
		protected FilterResults performFiltering( CharSequence searchFilter ) {
			FilterResults filterResults = new FilterResults();
			searchFilter = searchFilter.toString().toLowerCase();
			
			ArrayList<ContactsItem> originalList = (ArrayList<ContactsItem>) mContactsItems;
			if( originalList == null ) 
				return filterResults;
			
			if( searchFilter == null || searchFilter.length() == 0 ) {
				filterResults.values = originalList;
				filterResults.count = originalList.size();
			}
			else {
				ArrayList<ContactsItem> FilteredContactsItems = new ArrayList<ContactsItem>();
				ContactsItem contactsItem;
				for ( int index = 0; index < originalList.size(); index++ ) {
					contactsItem = originalList.get(index);
					if( contactsItem.toString().toLowerCase().contains(searchFilter) ) {
						FilteredContactsItems.add(contactsItem);
					}
				}
				filterResults.values = FilteredContactsItems;
				filterResults.count = FilteredContactsItems.size();
			}
			return filterResults;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence sequence, FilterResults results) {
			Log.d(TAG, "PublishResults : " + sequence ); 
			if( results.count == 0 ) 
				notifyDataSetInvalidated();
			
			else {
				mContactsItems = (List<ContactsItem>) results.values;
				notifyDataSetChanged();
			}
		} 

	}

	@Override
	public int getPositionForSection(int sectionIndex) {
		Log.d(TAG, "Position for Selection : "+ sectionIndex );
		return mapIndex.get(mSections[sectionIndex]);
	}

	@Override
	public int getSectionForPosition(int position) {
		Log.d(TAG, "Section for Position : "+ position);
		return 0;
	}

	@Override
	public String[] getSections() {
		return mSections;
	}

}
