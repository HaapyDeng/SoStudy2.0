package com.maxplus.sostudy.controller;

import android.view.View;
import android.view.View.OnClickListener;
import java.util.ArrayList;
import java.util.List;
import com.maxplus.sostudy.activity.CourseFragment;
import com.maxplus.sostudy.view.ContactsView;

public class ContactsController implements OnClickListener {

	private ContactsView mContactsView;
	private CourseFragment mContactsActivity;
	
	public ContactsController(ContactsView mContactsView, CourseFragment context) {
		this.mContactsView = mContactsView;
		this.mContactsActivity = context;
		initContacts();
	}

    public void initContacts() {
        //初始化用户名列表
        List<String> userNameList = new ArrayList<String>();
    }
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		
		}
		
	}

}
