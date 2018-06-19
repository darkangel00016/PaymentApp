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
import ve.com.lerny.paymentapp.models.MethodPaymentResponse;

public class PaymentMethodPresenter extends BasePresenter {

    private PaymentMethodPresenterListener mListener;

    public PaymentMethodPresenter(PaymentMethodPresenterListener listener) {
        this.mListener = listener;
    }

    public void getMethods() {
        mListener.showLoading();
        Retrofit client = createHttp();
        MercadoPagoService service = client.create(MercadoPagoService.class);
        service.paymentMethod(Constant.api_key).enqueue(new Callback<List<MethodPaymentResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<MethodPaymentResponse>> call, @NonNull Response<List<MethodPaymentResponse>> response) {
                mListener.hideLoading();
                if (response.code() == 200) {
                    List<MethodPaymentResponse> aResponse = response.body();
                    mListener.onSuccess(aResponse);
                } else {
                    mListener.onError(String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<MethodPaymentResponse>> call, @NonNull Throwable t) {
                mListener.hideLoading();
                mListener.onError(t.getMessage());
            }
        });
    }

    public interface PaymentMethodPresenterListener extends BaseListener {

        void onSuccess(List<MethodPaymentResponse> response);

        void onError(String message);

    }

}
