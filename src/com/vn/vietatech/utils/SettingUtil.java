package com.vn.vietatech.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;

import com.vn.vietatech.model.Setting;
import com.vn.vietatech.posman.R;

public class SettingUtil {
	private static String FILENAME = "POSinit";
	
	@SuppressLint("NewApi")
	public static void write(Setting setting, Context context) {
		File dir = new File(Environment.getExternalStorageDirectory().getPath()
				+ "/" + context.getResources().getString(R.string.app_folder));
		if(!dir.exists()) {
			dir.mkdirs();
		}
		File file = new File(dir, FILENAME);
		try {
			Properties props = new Properties();
			props.setProperty("ServerIP", setting.getServerIP());
			props.setProperty("Database", setting.getDatabase());
			props.setProperty("User", setting.getUser());
			props.setProperty("Pass", setting.getPass());
			props.setProperty("StoreNo", setting.getStoreNo());
			props.setProperty("POSGroup", setting.getPosGroup());
			props.setProperty("POSId", setting.getPosId());
			props.setProperty("SubMenu", setting.getSubMenu());

			FileWriter writer = new FileWriter(file);
			props.store(writer, "config");
			writer.close();
		} catch (FileNotFoundException ex) {
			// file does not exist
		} catch (IOException ex) {
			// I/O error
		}
	}

	@SuppressLint("NewApi")
	public static Setting read(Context context) {
		
		File file = new File(Environment.getExternalStorageDirectory()
				.getPath() + "/" + context.getResources().getString(R.string.app_folder) + "/" + FILENAME);
		if (file.exists() && file.isFile()) {
			try {
				FileReader reader = new FileReader(file);
				Properties props = new Properties();
				props.load(reader);

				Setting setting = new Setting();
				setting.setServerIP(props.getProperty("ServerIP"));
				setting.setDatabase(props.getProperty("Database"));
				setting.setUser(props.getProperty("User"));
				setting.setPass(props.getProperty("Pass"));
				setting.setStoreNo(props.getProperty("StoreNo"));
				setting.setPosGroup(props.getProperty("POSGroup"));
				setting.setPosId(props.getProperty("POSId"));
				setting.setSubMenu(props.getProperty("SubMenu"));
				reader.close();
				return setting;
			} catch (FileNotFoundException ex) {
				// file does not exist
			} catch (IOException ex) {
				// I/O error
			}
		}
		return null;
	}
}
