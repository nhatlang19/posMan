package com.vn.vietatech.posman;

import com.vn.vietatech.model.Session;

import android.app.Application;

public class MyApplication extends Application {
	private Session[] listSessions = null;

	public Session[] getSessions() {
		return listSessions;
	}

	public void setSessions(Session[] listSessions) {
		this.listSessions = listSessions;
	}
}
