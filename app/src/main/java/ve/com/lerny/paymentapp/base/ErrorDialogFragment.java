package ve.com.lerny.paymentapp.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ve.com.lerny.paymentapp.R;

public class ErrorDialogFragment extends BottomSheetDialogFragment {

    private String message;

    public static String TAG = "ErrorDialogFragment";

    public static ErrorDialogFragment getInstance(String message) {
        ErrorDialogFragment dialog = new ErrorDialogFragment();
        dialog.message = message;
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.error_dialog, container, false);
        ((TextView) view).setText(message);
        return view;
    }

}
