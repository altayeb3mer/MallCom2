package com.example.mallcom.Utils;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.example.mallcom.R;
import com.mikepenz.actionitembadge.library.ActionItemBadge;

import java.text.DecimalFormat;

public class Global {
    
    public void changeStatusBarColor(Activity activity,int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        }
    }

    public String formatNumber(String number){
        DecimalFormat formatter = null;
        String yourFormattedString;
        try {
            formatter = new DecimalFormat("#,###,###");
            yourFormattedString = formatter.format(Double.parseDouble(number));
        } catch (Exception e) {
            yourFormattedString = "0";
            e.printStackTrace();
        }

        return yourFormattedString;
    }
    public String formatNumber(double number){
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String yourFormattedString = formatter.format(number);
        return yourFormattedString;

    }
    public void setCartCount(Activity activity, MenuItem menuItem, int count){
        ActionItemBadge.update(activity, menuItem, menuItem.getIcon(), ActionItemBadge.BadgeStyles.GREEN, count);

    }

}
