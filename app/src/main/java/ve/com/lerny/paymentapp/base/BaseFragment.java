package ve.com.lerny.paymentapp.base;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import ve.com.lerny.paymentapp.models.Payment;

public class BaseFragment extends Fragment {

    protected OnFragmentInteractionListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void hideKeyboard();
        void setTitle(String title);
        void pushFragment(Fragment fragment, String tag);
        Payment getPayment();
        void setPayment(Payment payment);
        void start();
        void showBack();
        void hideBack();
    }

    protected void showError(String message) {
        ErrorDialogFragment bottomSheetDialog = ErrorDialogFragment.getInstance(message);
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            bottomSheetDialog.show(fragmentManager, ErrorDialogFragment.TAG);
        }
    }

}
