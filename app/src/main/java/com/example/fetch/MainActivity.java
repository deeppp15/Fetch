package com.example.fetch;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fetch.api.ApiClient;
import com.example.fetch.data.ItemData;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        View mainView = findViewById(R.id.main);
        ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        fetchItems();
        fetchItems();
    }

    private void fetchItems() {
        Call<List<ItemData>> call = ApiClient.INSTANCE.getApiService().getItemsList();
        call.enqueue(new Callback<List<ItemData>>() {
            @Override
            public void onResponse(Call<List<ItemData>> call, Response<List<ItemData>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ItemData> items = response.body();

                    // Filter out items with empty name and group by listId
                    Map<Integer, List<ItemData>> groupedItems = items.stream()
                            .filter(itemData -> itemData.getName() != null && !itemData.getName().isEmpty())
                            .collect(Collectors.groupingBy(
                                    ItemData::getListId,
                                    TreeMap::new,
                                    Collectors.collectingAndThen(
                                            Collectors.toList(),
                                            list -> {
                                                list.sort((a, b) -> Integer.compare(a.getItemId(), b.getItemId()));
                                                return list;
                                            }
                                    )
                            ));

                    // Set adapter with grouped and sorted items
                    adapter = new ItemAdapter(groupedItems);
                    recyclerView.setAdapter(adapter);
                } else {
                    Log.e("ERROR", "Response was not successful");
                }
            }

            @Override
            public void onFailure(Call<List<ItemData>> call, Throwable t) {
                Log.e("FAILED", "Request failed", t);
            }
        });
    }
}
