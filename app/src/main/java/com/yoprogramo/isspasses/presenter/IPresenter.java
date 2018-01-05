package com.yoprogramo.isspasses.presenter;

import com.yoprogramo.isspasses.entities.Response;

import java.util.List;


public interface IPresenter {

    public interface ILocationActiviy {
        void getIssPasses(List<Response> issResponsesList, String latitud, String longitud);

    }

}
