package handling;
/**
 * 
 * This class is used to store global variables, like the width and length for the views
 * and also contains Events for buttons
 *
 */
public class GloblVars {
	
	/**
	 * 
	 * This enum class is used by mvc by the observer to be able to tell which button was pressed
	 *
	 */
	public enum Events{
		LV_LOGIN_BUTTON,
		EXIT_BUTTON,
		SV_SAIN_BUTTON,
		SV_WHATIF_BUTTON,
		FV_SEARCH_BUTTON,
		SV_BACK_BUTTON,
		LOGOUT_BUTTON,
		MDFY_CRS_TAKEN,
		MDFY_CRS_TAKING,
		CHANGE_GRADE_BUTTON,
		ADD_CLASS_BUTTON,
		DELTE_CLASS_BUTTON
	};
	
	public static final int LV_WIDTH = 300, LV_HEIGHT = 160;
	public static final int SV_WIDTH = 1050, SV_HEIGHT = 750;
	public static final int FV_WIDTH = 400, FV_HEIGHT = 250;
}
