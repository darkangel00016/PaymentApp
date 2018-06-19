package ve.com.lerny.paymentapp.base;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ve.com.lerny.paymentapp.Constant;

public class BasePresenter {

    protected Retrofit createHttp() {
        return new Retrofit.Builder()
                .baseUrl(Constant.base_api)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}
