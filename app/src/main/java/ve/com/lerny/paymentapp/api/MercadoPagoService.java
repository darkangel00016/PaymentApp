package ve.com.lerny.paymentapp.api;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ve.com.lerny.paymentapp.models.BankResponse;
import ve.com.lerny.paymentapp.models.InstallmentsResponse;
import ve.com.lerny.paymentapp.models.MethodPaymentResponse;

public interface MercadoPagoService {

    @GET("payment_methods")
    Call<List<MethodPaymentResponse>> paymentMethod(@Query("public_key") String publicKey);

    @GET("payment_methods/card_issuers")
    Call<List<BankResponse>> banks(@Query("public_key") String publicKey, @Query("payment_method_id") String paymentMethod_id);

    @GET("payment_methods/installments")
    Call<List<InstallmentsResponse>> installments(@Query("public_key") String publicKey, @Query("payment_method_id") String paymentMethod_id, @Query("issuer.id") String issuerId, @Query("amount") Double amount);

}
