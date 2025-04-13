package com.first.demo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.first.demo.adaptors.ArticleAdapter;
import com.first.demo.models.Article;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;

public class MedicationFragment extends Fragment implements ArticleAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private ArticleAdapter adapter;
    private final List<Article> allArticles = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_medication, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        searchView = view.findViewById(R.id.searchView);

        adapter = new ArticleAdapter(new ArrayList<>(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(this::fetchArticles);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override public boolean onQueryTextSubmit(String query) { return false; }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterArticles(newText);
                return true;
            }
        });

        fetchArticles();
        return view;
    }

    private void fetchArticles() {
        swipeRefreshLayout.setRefreshing(true);
        new Thread(() -> {
            try {
                Document doc = Jsoup.connect("https://www.who.int/health-topics").get();
                Elements topicCards = doc.select(".list-view--item");

                List<Article> articles = new ArrayList<>();
                for (Element card : topicCards) {
                    String title = card.select("a").text();
                    String link = "https://www.who.int" + card.select("a").attr("href");
                    String imageUrl = card.select("img").attr("src");
                    String description = card.select("p").text();

                    if (imageUrl != null && !imageUrl.startsWith("http")) {
                        imageUrl = "https://www.who.int" + imageUrl;
                    }

                    articles.add(new Article(title, link, description, imageUrl));
                }

                requireActivity().runOnUiThread(() -> {
                    allArticles.clear();
                    allArticles.addAll(articles);
                    adapter.updateArticles(articles);
                    swipeRefreshLayout.setRefreshing(false);
                });

            } catch (Exception e) {
                Log.e("ArticleFetchError", "Error fetching articles", e);
                requireActivity().runOnUiThread(() -> {
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getContext(), "Failed to fetch articles", Toast.LENGTH_SHORT).show();
                });
            }
        }).start();
    }

    private void filterArticles(String query) {
        List<Article> filtered = new ArrayList<>();
        for (Article article : allArticles) {
            if (article.getTitle().toLowerCase().contains(query.toLowerCase())) {
                filtered.add(article);
            }
        }
        adapter.updateArticles(filtered);
    }

    @Override
    public void onItemClick(Article article) {
        Intent intent = new Intent(getContext(), ArticleDetailActivity.class);
        intent.putExtra("title", article.getTitle());
        intent.putExtra("description", article.getDescription());
        intent.putExtra("imageUrl", article.getImageUrl());
        startActivity(intent);
    }
}
