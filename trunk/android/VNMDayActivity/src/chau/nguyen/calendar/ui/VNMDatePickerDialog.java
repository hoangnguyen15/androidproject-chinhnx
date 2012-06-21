package chau.nguyen.calendar.ui;

import java.util.Calendar;
import java.util.Locale;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.DatePicker.OnDateChangedListener;
import chau.nguyen.R;
import chau.nguyen.calendar.VietCalendar;

public class VNMDatePickerDialog extends AlertDialog implements OnClickListener, OnDateChangedListener {
     private static final String YEAR = "year";
     private static final String MONTH = "month";
     private static final String DAY = "day";
     private RadioGroup radioGroup;
     private RadioButton solarRadio;
     private RadioButton lunarRadio;
     private final DatePicker mDatePicker;
     private final OnDateSetListener mCallBack;
     private final Calendar mCalendar;
          
     private int mInitialYear;
     private int mInitialMonth;
     private int mInitialDay;
     /**

      * The callback used to indicate the user is done filling in the date.

      */
     public interface OnDateSetListener {
         /**

          * @param view The view associated with this listener.

          * @param year The year that was set.

          * @param monthOfYear The month that was set (0-) for compatibility

          *  with {@link java.util.Calendar}.

          * @param dayOfMonth The day of the month that was set.

          */
         void onDateSet(DatePicker view, boolean isSolarSelected, int year, int monthOfYear, int dayOfMonth);
     }
     /**

      * @param context The context the dialog is to run in.

      * @param callBack How the parent is notified that the date is set.

      * @param year The initial year of the dialog.

      * @param monthOfYear The initial month of the dialog.

      * @param dayOfMonth The initial day of the dialog.

      */
     public VNMDatePickerDialog(Context context, OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth) {
         this(context, android.R.style.Theme_Dialog,
                 callBack, year, monthOfYear, dayOfMonth);
     }
     /**

      * @param context The context the dialog is to run in.

      * @param theme the theme to apply to this dialog

      * @param callBack How the parent is notified that the date is set.

      * @param year The initial year of the dialog.

      * @param monthOfYear The initial month of the dialog.

      * @param dayOfMonth The initial day of the dialog.

      */
     public VNMDatePickerDialog(Context context, int theme, OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth) {
        super(context);        
        mCallBack = callBack;
        mInitialYear = year;
        mInitialMonth = monthOfYear;
        mInitialDay = dayOfMonth;
        mCalendar = Calendar.getInstance();
        updateTitle(mInitialYear, mInitialMonth, mInitialDay);
        setButton(context.getText(R.string.date_time_set), this);
        setButton2(context.getText(R.string.cancel), (OnClickListener) null);
        LayoutInflater inflater = 
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
        Locale defaultLocale = Locale.getDefault();
		Locale.setDefault(VietCalendar.vnLocale);
        View view = inflater.inflate(R.layout.vnm_date_picker_dialog, null);                
        mDatePicker = (DatePicker) view.findViewById(R.id.datePicker);
        mDatePicker.init(mInitialYear, mInitialMonth, mInitialDay, this);
        Locale.setDefault(defaultLocale);
        
        this.radioGroup = (RadioGroup)view.findViewById(R.id.radio_group);
        this.lunarRadio = (RadioButton)view.findViewById(R.id.radio_lunar);
        this.solarRadio = (RadioButton)view.findViewById(R.id.radio_solar);
        this.solarRadio.setChecked(true);
        this.lunarRadio.setChecked(false);
        setView(view);
    }

    @Override
    public void show() {
        super.show();

        /* Sometimes the full month is displayed causing the title

         * to be very long, in those cases ensure it doesn't wrap to

         * 2 lines (as that looks jumpy) and ensure we ellipsize the end.

         */

    }
    public void onClick(DialogInterface dialog, int which) {
        if (mCallBack != null) {
            mDatePicker.clearFocus();
            mCallBack.onDateSet(mDatePicker, solarRadio.isChecked(), mDatePicker.getYear(), 
                    mDatePicker.getMonth(), mDatePicker.getDayOfMonth());
        }

    }

    public void onDateChanged(DatePicker view, int year, int month, int day) {
        updateTitle(year, month, day);
    }

        

    public void updateDate(int year, int monthOfYear, int dayOfMonth) {
        mInitialYear = year;
        mInitialMonth = monthOfYear;
        mInitialDay = dayOfMonth;
        mDatePicker.updateDate(year, monthOfYear, dayOfMonth);
    }

    

    private void updateTitle(int year, int month, int day) {
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, day);
        
        setTitle(VietCalendar.formatVietnameseDate(mCalendar.getTime()));
    }

     

     @Override
     public Bundle onSaveInstanceState() {
         Bundle state = super.onSaveInstanceState();
         state.putInt(YEAR, mDatePicker.getYear());
         state.putInt(MONTH, mDatePicker.getMonth());
         state.putInt(DAY, mDatePicker.getDayOfMonth());
         return state;

     }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int year = savedInstanceState.getInt(YEAR);
        int month = savedInstanceState.getInt(MONTH);
         int day = savedInstanceState.getInt(DAY);
         mDatePicker.init(year, month, day, this);
         updateTitle(year, month, day);
    }
    
    public void setRadioGroupDateTypeVisible(boolean value) {
    	if (value) {
    		this.radioGroup.setVisibility(View.VISIBLE);
    	} else {
    		this.radioGroup.setVisibility(View.INVISIBLE);
    	}
    }
}
