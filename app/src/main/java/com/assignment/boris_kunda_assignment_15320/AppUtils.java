package com.assignment.boris_kunda_assignment_15320;

import android.app.Application;
import android.content.res.Resources;
import android.view.Window;
import android.view.WindowManager;

import androidx.lifecycle.ViewModelProvider;

import com.assignment.boris_kunda_assignment_15320.viewmodel.MovieViewModel;

public final class AppUtils {

    private AppUtils () {
    }

    public static void setStatusBarColor (Window iWindow, Resources iResources) {
        iWindow.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        iWindow.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        iWindow.setStatusBarColor(iResources.getColor(R.color.grey));
    }

}
