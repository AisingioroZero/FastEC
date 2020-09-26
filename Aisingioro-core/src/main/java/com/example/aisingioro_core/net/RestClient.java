package com.example.aisingioro_core.net;

import com.example.aisingioro_core.net.callback.IError;
import com.example.aisingioro_core.net.callback.IFailure;
import com.example.aisingioro_core.net.callback.IRequest;
import com.example.aisingioro_core.net.callback.ISuccess;
import com.example.aisingioro_core.net.callback.RequestCallback;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class RestClient {
    private final String _URL;
    private static final Map<String, Object> PARAMS = RestCreator.getParams();
    private final IRequest IREQUEST;
    private final ISuccess ISUCCESS;
    private final IError IERROR;
    private final IFailure IFAILURE;
    private final RequestBody BODY;

    public RestClient(String _URL,
                      Map<String, Object> params,
                      IRequest IREQUEST,
                      ISuccess ISUCCESS,
                      IError IERROR,
                      IFailure IFAILURE,
                      RequestBody BODY) {
        this._URL = _URL;
        this.PARAMS.putAll(params);
        this.IREQUEST = IREQUEST;
        this.ISUCCESS = ISUCCESS;
        this.IERROR = IERROR;
        this.IFAILURE = IFAILURE;
        this.BODY = BODY;
    }

    public static RestClientBuilder builder(){
        return new RestClientBuilder();
    }

    private void request(HttpMethod method){
        final RestService service = RestCreator.getRestService();
        Call<String> call = null;
        if(IREQUEST != null ){
            IREQUEST.onRequestStart();
        }
        switch (method){
            case GET:
                call = service.get(_URL, PARAMS);
                break;
            case POST:
                call = service.post(_URL, PARAMS);
                break;
            case PUT:
                call = service.put(_URL, PARAMS);
                break;
            case DELETE:
                call = service.delete(_URL, PARAMS);
                break;
            default:
                break;
        }

        if(call != null){
            call.enqueue(getRequestCallback());
        }
    }

    private Callback<String> getRequestCallback(){
        return new RequestCallback(
                IREQUEST,
                ISUCCESS,
                IERROR,
                IFAILURE
        );
    }

    public final void get(){
        request(HttpMethod.GET);
    }

    public final void post(){
        request(HttpMethod.POST);
    }

    public final void put(){
        request(HttpMethod.PUT);
    }

    public final void delete(){
        request(HttpMethod.DELETE);
    }


}
