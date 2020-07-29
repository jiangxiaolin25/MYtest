package com.xinguodu.pincontrol;

/*import android.util.Log;*/

public class PinControl {

	private static final String TAG = "PinServiceControl";

	public native void open_pincontrol();
	
	public native void close_pincontrol();
	
	public native int select_pincontrol();
	
	public native int get_next_event(int [] p_event_id, int [] p_event_buf_len, byte [] event_buf);
	
	public native int send_nonce_to_auth(int nonce_buf_len, byte [] nonce_buf);
	
	public native int show_new_back(int backimg_id);
	
	public native int show_new_pad();
	
	public native int display_asterisk(int asterisk_count);
	
	public native int hide_surface();
	
	public native int quit_key();

	public native int get_buffer();
	
	public native int set_pinmode(int mode);

	public native int add_asset_images_to_service(Object AssetManager, String [] ImagesAssetName, String [] CfgsAssetName);	

	public native int add_abs_images_to_service(String [] AbsImagesPath, String [] AbsCfgsPath);
	
	public native int set_asterisk_count(int min, int max);
	
	static {
		System.loadLibrary("pin_control");
	}
}
