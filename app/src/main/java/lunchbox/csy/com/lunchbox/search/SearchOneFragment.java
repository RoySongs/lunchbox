package lunchbox.csy.com.lunchbox.search;

import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lunchbox.csy.com.lunchbox.R;
import lunchbox.csy.com.lunchbox.item.SearchItem;
import lunchbox.csy.com.lunchbox.model.SearchResult;
import lunchbox.csy.com.lunchbox.remote.RemoteService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchOneFragment extends Fragment implements View.OnClickListener{

    RemoteService remoteService;
    EditText searchWord;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_search_sub_one, container, false);

        Button searchBtn = view.findViewById(R.id.search_btn);
        searchBtn.setOnClickListener(this);

        //category = view.findViewById()
        searchWord = view.findViewById(R.id.searchWord);
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RemoteService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        remoteService = retrofit.create(RemoteService.class);

        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.search_btn){
           // searchItem= new SearchItem("", "",category.getText().toString());
           // searchItem= new SearchItem("korean", "korean","korean");

            Call<SearchResult> searchList = remoteService.getSearchData(searchWord.getText().toString());
            searchList.enqueue(new Callback<SearchResult>() {
                @Override
                public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                    SearchResult requestResult = response.body();
                    switch (requestResult.getCode()) {
                        case 200:
                            //Toast.makeText(getActivity(), response.body().toString(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(getActivity(), requestResult.toString(), Toast.LENGTH_SHORT).show();
                            break;
                        case 401:
                            Toast.makeText(getActivity(), requestResult.getMessage(), Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
                @Override
                public void onFailure(Call<SearchResult> call, Throwable throwable) {
                    throwable.printStackTrace();
                    Toast.makeText(getActivity(), throwable.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
