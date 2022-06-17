package com.example.ajedrez.utils;

import android.content.Context;
import androidx.appcompat.app.AlertDialog;
import com.example.ajedrez.R;

public class AlertsManager {

    private static AlertsManager instance = new AlertsManager();

    public interface OnClickDialog{
        void onClick();
    }

    public static AlertsManager getInstance() {
        if(instance == null){
            instance = new AlertsManager();
        }
        return instance;
    }

    /**
     * Presents an alert dialog.
     *
     * @param title   - the title to use
     * @param message - the message to show
     */
    public void showAlertDialog(final String title, final String message, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (title != null) {
            builder.setTitle(title);
        }
        builder.setMessage(message);
        builder.setPositiveButton("OK", (dialog, id) -> {});
        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * Presents an alert dialog with a method on the ok button
     *
     * @param title   - the title to use
     * @param message - the message to show
     * @param function - the action that will be executed after pressing the ok button
     */
    public void showAlertDialogWithAction(final String title, final String message, Context context, Runnable function) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (title != null) {
            builder.setTitle(title);
        }
        builder.setMessage(message);
        builder.setPositiveButton("OK", (dialog, id) -> function.run());
        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * Presents an alert dialog with a method on the ok button
     *
     * @param title   - the title to use
     * @param message - the message to show
     * @param clickDialog - the action that will be executed after pressing the ok button
     * @param clickDialogCancel - the action that will be executed after pressing the No button in case it is not null
     */
    public void showYesOrNoDialog(final String title,
                                  final String message,
                                  Context context,
                                  OnClickDialog clickDialog,
                                  OnClickDialog clickDialogCancel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(context.getString(R.string.yes_button), (dialog, id) -> clickDialog.onClick());
        builder.setNegativeButton(context.getString(R.string.no_button), (dialog, id) -> clickDialogCancel.onClick());
        AlertDialog alert = builder.create();
        alert.show();
    }
}
