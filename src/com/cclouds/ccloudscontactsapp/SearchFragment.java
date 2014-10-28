package com.cclouds.ccloudscontactsapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class SearchFragment extends Fragment {

	String TAG = SearchFragment.class.getSimpleName();
	EditText mQueryEditText;

	onSearchListener mOnSearchListener;
	int mSearchMode = -1;

	public SearchFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		/**
		 * Inflate the layout for this fragment
		 */
		View view = inflater.inflate(R.layout.search_fragment, container, false);

		initUIComponents( view );

		return view;
	}

	private void initUIComponents( View view ) {

		Button buttonA = (Button) view.findViewById(R.id.buttonA);
		Button buttonB = (Button) view.findViewById(R.id.buttonB);
		Button buttonC = (Button) view.findViewById(R.id.buttonC);
		Button buttonD = (Button) view.findViewById(R.id.buttonD);
		Button buttonE = (Button) view.findViewById(R.id.buttonE);
		Button buttonF = (Button) view.findViewById(R.id.buttonF);
		Button buttonG = (Button) view.findViewById(R.id.buttonG);
		Button buttonH = (Button) view.findViewById(R.id.buttonH);
		Button buttonI = (Button) view.findViewById(R.id.buttonI);
		Button buttonJ = (Button) view.findViewById(R.id.buttonJ);
		Button buttonK = (Button) view.findViewById(R.id.buttonK);
		Button buttonL = (Button) view.findViewById(R.id.buttonL);
		Button buttonM = (Button) view.findViewById(R.id.buttonM);
		Button buttonN = (Button) view.findViewById(R.id.buttonN);
		Button buttonO = (Button) view.findViewById(R.id.buttonO);
		Button buttonP = (Button) view.findViewById(R.id.buttonP);
		Button buttonQ = (Button) view.findViewById(R.id.buttonQ);
		Button buttonR = (Button) view.findViewById(R.id.buttonR);
		Button buttonS = (Button) view.findViewById(R.id.buttonS);
		Button buttonT = (Button) view.findViewById(R.id.buttonT);
		Button buttonU = (Button) view.findViewById(R.id.buttonU);
		Button buttonV = (Button) view.findViewById(R.id.buttonV);
		Button buttonW = (Button) view.findViewById(R.id.buttonW);
		Button buttonX = (Button) view.findViewById(R.id.buttonX);
		Button buttonY = (Button) view.findViewById(R.id.buttonY);
		Button buttonZ = (Button) view.findViewById(R.id.buttonZ);

		buttonA.setOnClickListener(mButtonClickListener);
		buttonB.setOnClickListener(mButtonClickListener);
		buttonC.setOnClickListener(mButtonClickListener);
		buttonD.setOnClickListener(mButtonClickListener);
		buttonE.setOnClickListener(mButtonClickListener);
		buttonF.setOnClickListener(mButtonClickListener);
		buttonG.setOnClickListener(mButtonClickListener);
		buttonH.setOnClickListener(mButtonClickListener);
		buttonI.setOnClickListener(mButtonClickListener);
		buttonJ.setOnClickListener(mButtonClickListener);
		buttonK.setOnClickListener(mButtonClickListener);
		buttonL.setOnClickListener(mButtonClickListener);
		buttonM.setOnClickListener(mButtonClickListener);
		buttonN.setOnClickListener(mButtonClickListener);
		buttonO.setOnClickListener(mButtonClickListener);
		buttonP.setOnClickListener(mButtonClickListener);
		buttonQ.setOnClickListener(mButtonClickListener);
		buttonR.setOnClickListener(mButtonClickListener);
		buttonS.setOnClickListener(mButtonClickListener);
		buttonT.setOnClickListener(mButtonClickListener);
		buttonU.setOnClickListener(mButtonClickListener);
		buttonV.setOnClickListener(mButtonClickListener);
		buttonW.setOnClickListener(mButtonClickListener);
		buttonX.setOnClickListener(mButtonClickListener);
		buttonY.setOnClickListener(mButtonClickListener);
		buttonZ.setOnClickListener(mButtonClickListener);

		Button clearButton = (Button) view.findViewById(R.id.clearBtn);
		clearButton.setOnClickListener(mButtonClickListener);
		
		mQueryEditText = (EditText) view.findViewById(R.id.searchEditText);
		mQueryEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int count, int after) {
				search( s );
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				//search( s );
			}

			@Override
			public void afterTextChanged(Editable s) {
				//search( s );
			}
		});

	}

	protected void search(CharSequence s) {

		mSearchMode = ContactListFragment.SEARCH_QUERY_MODE;
		mOnSearchListener.queryByString(s.toString());

	}

	OnClickListener mButtonClickListener = new OnClickListener() {

		@Override
		public void onClick( View v ) {

			if( v.getId() == R.id.clearBtn ) {
				mQueryEditText.setText("");
				setActivityTitle("CClouds Contacts");
				switch ( mSearchMode ) {
				case ContactListFragment.SEARCH_ALPHABET_MODE :
					mOnSearchListener.queryByAlphabet("");
					break;
				case ContactListFragment.SEARCH_QUERY_MODE :
					mOnSearchListener.queryByString("");
					break;
				}
			}
			else {
				Button selectedButton = (Button) v;
				String selectedText = selectedButton.getText().toString();
				setSearchActivityTitle(selectedText);	
			}
		}
	};

	protected void setSearchActivityTitle( String selectedText ) {

		String titleMessage = "Selected Alphabet : " + selectedText;
		Log.d(TAG, titleMessage ); 
		setActivityTitle(titleMessage);
		mSearchMode = ContactListFragment.SEARCH_ALPHABET_MODE;
		mOnSearchListener.queryByAlphabet(selectedText);

	}

	private void setActivityTitle( String title ) {
		getActivity().setTitle(title);
	}

	public interface onSearchListener { 
		public void queryByString( String searchString );
		public void queryByAlphabet( String alphabet );
	}

	@Override 
	public void onAttach( Activity activity ) {

		try {
			super.onAttach(activity);
			mOnSearchListener = (onSearchListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement onSearchListener");
		}

	}

	@Override
	public void onDetach() {

		super.onDetach();
		mOnSearchListener = null;

	}

}
