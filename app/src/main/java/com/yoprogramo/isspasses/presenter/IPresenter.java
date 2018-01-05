package com.yoprogramo.isspasses.presenter;

import com.yoprogramo.isspasses.model.Response;

import java.util.List;

/**
 * Created by User on 1/4/2018.
 */

public interface IPresenter {

    public interface ILocationActiviy {
        void getIssPasses(List<Response> issResponsesList, String latitud, String longitud);

    }

}
