package com.yoprogramo.isspasses.presenter;

import android.util.Log;

import com.yoprogramo.isspasses.entities.ISSPass;
import com.yoprogramo.isspasses.entities.Response;
import com.yoprogramo.isspasses.model.RetrofitAdapter;
import com.yoprogramo.isspasses.view.IView;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MainViewActivityPresenter implements IPresenter.ILocationActiviy{

IView.ILocationActivityView locationAvtivityViewInterface;

    public MainViewActivityPresenter(IView.ILocationActivityView locationAvtivityViewInterface) {
        this.locationAvtivityViewInterface = locationAvtivityViewInterface;
    }

    @Override
    public void getIssPasses(final List<Response> issResponsesList, String latitud, String longitud ) {

        Observable<ISSPass> resultObservable = RetrofitAdapter.Factory.createObservable(latitud,longitud);
        Observer issObserver = new Observer<ISSPass>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ISSPass issPass) {
                //iDetailFollowerView.setItems(repoUser);

                if (issPass != null && issPass.getResponse() != null) {

                    for(Response item : issPass.getResponse()){
                        issResponsesList.add(item);
                    }

                }

                locationAvtivityViewInterface.notifyAdapter();
                Log.d("TAG", "onNext: " + issResponsesList.toString());
            }

        };

        resultObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(issObserver);


    }


}
