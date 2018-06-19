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
import ve.com.lerny.paymentapp.models.InstallmentsCostsResponse;
import ve.com.lerny.paymentapp.models.InstallmentsResponse;

public class InstallmentsPresenter extends BasePresenter {

    private InstallmentsPresenterListener mListener;

    public InstallmentsPresenter(InstallmentsPresenterListener listener) {
        this.mListener = listener;
    }

    public void getInstallments(String paymentId, String issuerId, Double amount) {
        mListener.showLoading();
        Retrofit client = createHttp();
        MercadoPagoService service = client.create(MercadoPagoService.class);
        service.installments(Constant.api_key, paymentId, issuerId, amount).enqueue(new Callback<List<InstallmentsResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<InstallmentsResponse>> call, @NonNull Response<List<InstallmentsResponse>> response) {
                mListener.hideLoading();
                if (response.code() == 200) {
                    List<InstallmentsResponse> aResponse = response.body();
                    if (aResponse != null && aResponse.size() == 1) {
                        List<InstallmentsCostsResponse> items = aResponse.get(0).payer_costs;
                        mListener.onSuccess(items);
                    } else {
                        mListener.onError("There is not installments availables.");
                    }
                } else {
                    mListener.onError(String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<InstallmentsResponse>> call, @NonNull Throwable t) {
                mListener.hideLoading();
                mListener.onError(t.getMessage());
            }
        });
    }

    public interface InstallmentsPresenterListener extends BaseListener {

        void onSuccess(List<InstallmentsCostsResponse> response);

        void onError(String message);

    }

}
