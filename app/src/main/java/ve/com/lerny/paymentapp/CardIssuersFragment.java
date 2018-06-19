package ve.com.lerny.paymentapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import ve.com.lerny.paymentapp.adapter.BankAdapter;
import ve.com.lerny.paymentapp.base.BaseFragment;
import ve.com.lerny.paymentapp.models.BankResponse;
import ve.com.lerny.paymentapp.models.Payment;
import ve.com.lerny.paymentapp.presenter.BankPresenter;

public class CardIssuersFragment extends BaseFragment implements BankPresenter.BankPresenterListener, AdapterView.OnItemClickListener {

    public static String TAG = "CardIssuersFragment";

    private BankPresenter presenter;

    private List<BankResponse> mList;

    private BankAdapter adapter;

    private ListView mListView;

    private View mLoading;

    public CardIssuersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Amount.
     */
    public static CardIssuersFragment newInstance() {
        return new CardIssuersFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListener.setTitle(getString(R.string.fragment_bank));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        Payment payment = mListener.getPayment();
        presenter.getBanks(payment.method.id);
    }

    @Override
    public void onResume() {
        super.onResume();
        setHasOptionsMenu(true);
        mListener.setTitle(getString(R.string.fragment_bank));
        mListener.showBack();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void init() {
        presenter = new BankPresenter(this);
        View view = getView();
        mList = new ArrayList<>();
        if (view != null) {
            mListView = view.findViewById(R.id.listView);
            mLoading = view.findViewById(R.id.loading);
            adapter = new BankAdapter(getContext(), mList);
            mListView.setAdapter(adapter);
            mListView.setOnItemClickListener(this);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment_method, container, false);
    }

    @Override
    public void onSuccess(List<BankResponse> response) {
        mList.clear();
        mList.addAll(response);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onError(String message) {
        showError(message);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        BankResponse item = mList.get(position);
        Payment payment = mListener.getPayment();
        payment.bank = item;
        mListener.setPayment(payment);
        mListener.pushFragment(InstallmentsFragment.newInstance(), InstallmentsFragment.TAG);
    }

    @Override
    public void showLoading() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mListView.setVisibility(View.GONE);
                mLoading.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void hideLoading() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mListView.setVisibility(View.VISIBLE);
                mLoading.setVisibility(View.GONE);
            }
        });
    }
}
