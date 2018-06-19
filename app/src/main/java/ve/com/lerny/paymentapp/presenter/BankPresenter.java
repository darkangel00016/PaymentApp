package ve.com.lerny.paymentapp.presenter;

import android.support.annotation.NonNull;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import ve.com.lerny.paymentapp.Constant;
import ve.com.lerny.paymentapp.api.MercadoPagoService;
import ve.com.lerny.paymentapp.base.BaseListener;
import ve.com.lerny.paymentapp.base.BasePresenter;
import ve.com.lerny.paymentapp.models.BankResponse;

public class BankPresenter extends BasePresenter {

    private BankPresenterListener mListener;

    public BankPresenter(BankPresenterListener listener) {
        this.mListener = listener;
    }

    public void getBanks(String paymentId) {
        mListener.showLoading();
        Retrofit client = createHttp();
        MercadoPagoService service = client.create(MercadoPagoService.class);
        service.banks(Constant.api_key, paymentId).enqueue(new Callback<List<BankResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<BankResponse>> call, @NonNull Response<List<BankResponse>> response) {
                mListener.hideLoading();
                if (response.code() == 200) {
                    List<BankResponse> aResponse = response.body();
                    mListener.onSuccess(aResponse);
                } else {
                    mListener.onError(String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<BankResponse>> call, @NonNull Throwable t) {
                mListener.hideLoading();
                mListener.onError(t.getMessage());
            }
        });
    }

    public interface BankPresenterListener extends BaseListener {

        void onSuccess(List<BankResponse> response);

        void onError(String message);

    }

}
