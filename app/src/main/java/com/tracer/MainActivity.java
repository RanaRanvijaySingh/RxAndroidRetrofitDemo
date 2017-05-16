package com.tracer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.tracer.models.WeatherData;
import com.tracer.webservice.ApiClient;
import com.tracer.webservice.WeatherApi;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.textViewResponse)
    TextView mTextViewResponse;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        makeWeatherApiCall();
    }

    private void makeWeatherApiCall() {
        final WeatherApi weatherApi = ApiClient.getClient().create(WeatherApi.class);
        final Observable<Response<WeatherData>> weatherData =
                weatherApi.getWeatherData("Pune", "b1b15e88fa797225412429c1c50c122a1");
        weatherData.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<WeatherData>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        mTextViewResponse.setText("onError");
                    }

                    @Override
                    public void onNext(final Response<WeatherData> weatherDataResponse) {
                        mTextViewResponse.setText(weatherDataResponse.body().getName());
                    }
                });

    }
}