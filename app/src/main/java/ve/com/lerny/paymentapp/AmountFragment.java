package ve.com.lerny.paymentapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import ve.com.lerny.paymentapp.base.BaseFragment;
import ve.com.lerny.paymentapp.models.Payment;

public class AmountFragment extends BaseFragment implements View.OnClickListener {

    public static String TAG = "AmountFragment";
    private AppCompatEditText otherAmount;
    private View form;
    private View paymentForm;
    private TextView textAmount;
    private TextView labelMethodPayment;
    private ImageView iconMethodPayment;
    private TextView labelCardIssuer;
    private ImageView iconCardIssuer;
    private TextView textInstallment;

    public AmountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Amount.
     */
    public static AmountFragment newInstance() {
        return new AmountFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListener.setTitle(getString(R.string.fragment_amount));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        View view = getView();
        if (view != null) {
            form = view.findViewById(R.id.form);
            paymentForm = view.findViewById(R.id.payment);
            textAmount = view.findViewById(R.id.textAmount);
            labelMethodPayment = view.findViewById(R.id.labelMethodPayment);
            iconMethodPayment = view.findViewById(R.id.iconMethodPayment);
            labelCardIssuer = view.findViewById(R.id.labelCardIssuer);
            iconCardIssuer = view.findViewById(R.id.iconCardIssuer);
            textInstallment = view.findViewById(R.id.textInstallment);
            AppCompatButton button_20 = view.findViewById(R.id.button_20);
            AppCompatButton button_40 = view.findViewById(R.id.button_40);
            AppCompatButton button_60 = view.findViewById(R.id.button_60);
            AppCompatButton button_80 = view.findViewById(R.id.button_80);
            AppCompatButton button_100 = view.findViewById(R.id.button_100);
            AppCompatButton btnNext = view.findViewById(R.id.btnNext);
            AppCompatButton action_start_over = view.findViewById(R.id.action_start_over);
            otherAmount = view.findViewById(R.id.otherAmount);
            btnNext.setOnClickListener(this);
            button_20.setOnClickListener(this);
            button_40.setOnClickListener(this);
            button_60.setOnClickListener(this);
            button_80.setOnClickListener(this);
            button_100.setOnClickListener(this);
            action_start_over.setOnClickListener(this);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_amount, container, false);
    }

    @Override
    public void onClick(View v) {
        Integer viewId = v.getId();
        switch (viewId) {
            case R.id.button_20:
                actionNext(20.0);
                break;
            case R.id.button_40:
                actionNext(40.0);
                break;
            case R.id.button_60:
                actionNext(60.0);
                break;
            case R.id.button_80:
                actionNext(80.0);
                break;
            case R.id.button_100:
                actionNext(100.0);
                break;
            case R.id.btnNext:
                String amount = otherAmount.getText().toString();
                if (amount.equals("")) {
                    showError(getString(R.string.amount_required));
                } else {
                    actionNext(Double.valueOf(otherAmount.getText().toString()));
                }
                break;
            case R.id.action_start_over:
                Payment payment = mListener.getPayment();
                payment.reset();
                mListener.setPayment(payment);
                showForm();
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mListener.setTitle(getString(R.string.fragment_amount));
        mListener.hideBack();
        Payment payment = mListener.getPayment();
        if (payment.finish) {
            showPayment();
        } else {
            showForm();
        }
    }

    private void showForm() {
        form.setVisibility(View.VISIBLE);
        paymentForm.setVisibility(View.GONE);
        initForm();
    }

    private void showPayment() {
        form.setVisibility(View.GONE);
        paymentForm.setVisibility(View.VISIBLE);
        initPaymentForm();
    }

    private void initForm() {
        otherAmount.setText("");
    }

    private void initPaymentForm() {
        Payment payment = mListener.getPayment();
        textAmount.setText(getString(R.string.payment_amount, payment.amount));
        labelMethodPayment.setText(payment.method.name);
        Picasso
                .get()
                .load(payment.method.secure_thumbnail)
                .into(iconMethodPayment);
        labelCardIssuer.setText(payment.bank.name);
        Picasso
                .get()
                .load(payment.bank.secure_thumbnail)
                .into(iconCardIssuer);
        textInstallment.setText(payment.installment.recommended_message);
    }

    private void actionNext(Double amount) {
        if (amount <= 0) {
            Toast.makeText(getActivity(), R.string.must_select_amount, Toast.LENGTH_SHORT).show();
        } else {
            Payment payment = mListener.getPayment();
            payment.amount = amount;
            mListener.setPayment(payment);
            mListener.hideKeyboard();
            mListener.pushFragment(PaymentMethodFragment.newInstance(), PaymentMethodFragment.TAG);
        }
    }
}
