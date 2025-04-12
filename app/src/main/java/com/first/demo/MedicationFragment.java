package com.first.demo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;


import com.first.demo.adaptors.ArticleAdapter;
import com.first.demo.models.Article;
import com.first.demo.models.MedlinePlusService;
import com.first.demo.models.RssFeed;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
//import org.w3c.dom.Element;
//import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MedicationFragment#newInstance} factory method to
 * create an instance of this fragment.
// */
//public class MedicationFragment extends Fragment {
//
//    private TextView articlesTextView;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
//                             @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_medication, container, false);
//
//        articlesTextView = view.findViewById(R.id.articlesTextView);
//        fetchHealthArticles();
//
//        return view;
//    }
//
//    private void fetchHealthArticles() {
//        // Example dummy content — replace this with API logic
//        String exampleArticles = "• Stay hydrated\n• Exercise regularly\n• Eat balanced meals";
//        articlesTextView.setText(exampleArticles);
//
//        // In real case, you can use Retrofit/Volley or HttpURLConnection to call an API
//    }

//}

//
//public class MedicationFragment extends Fragment {
//
//    private TextView articlesTextView;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
//                             @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_medication, container, false);
////        articlesTextView = view.findViewById(R.id.articlesTextView);
//        fetchHealthNews();
//        return view;
//    }
//
//    private void fetchHealthNews() {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://medlineplus.gov/")
//                .addConverterFactory(SimpleXmlConverterFactory.create())
//                .build();
//
//        MedlinePlusService service = retrofit.create(MedlinePlusService.class);
//        Call<RssFeed> call = service.getHealthNews();
//
//        call.enqueue(new Callback<RssFeed>() {
//            @Override
//            public void onResponse(Call<RssFeed> call, Response<RssFeed> response) {
//                if (response.isSuccessful()) {
//                    List<Article> articles = response.body().getChannel().getArticles();
//                    StringBuilder builder = new StringBuilder();
//                    for (int i = 0; i < Math.min(5, articles.size()); i++) {
//                        Article article = articles.get(i);
//                        builder.append("• ").append(article.getTitle()).append("\n");
//                        builder.append(article.getLink()).append("\n\n");
//                    }
//                    articlesTextView.setText(builder.toString());
//                } else {
//                    articlesTextView.setText("Failed to fetch articles.");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<RssFeed> call, Throwable t) {
//                articlesTextView.setText("Error: " + t.getMessage());
//            }
//        });
//    }
//}

//public class MedicationFragment extends Fragment {
//
//    private RecyclerView recyclerView;
//    private ArticleAdapter adapter;
//    private List<Article> articleList = new ArrayList<>();
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
//                             @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_medication, container, false);
//
//        recyclerView = view.findViewById(R.id.articlesRecyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        adapter = new ArticleAdapter(articleList);
//        recyclerView.setAdapter(adapter);
//
//        fetchHealthArticles();
//
//        return view;
//    }
//
//    private void fetchHealthArticles() {
//        String rssUrl = "https://medlineplus.gov/feeds/news_en.xml";
//
//        new Thread(() -> {
//            try {
//                URL url = new URL(rssUrl);
//                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                connection.setRequestMethod("GET");
//                InputStream inputStream = connection.getInputStream();
//
//                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//                DocumentBuilder builder = factory.newDocumentBuilder();
//                Document doc = builder.parse(inputStream);
//                doc.getDocumentElement().normalize();
//
//                NodeList items = doc.getElementsByTagName("item");
//                articleList.clear();
//
//                for (int i = 0; i < items.getLength(); i++) {
//                    // Ensure we are dealing with an Element node
//                    if (items.item(i) instanceof Element) {
//                        Element element = (Element) items.item(i);  // Cast to Element
//                        String title = element.getElementsByTagName("title").item(0).getTextContent();
//                        String link = element.getElementsByTagName("link").item(0).getTextContent();
//                        String description = element.getElementsByTagName("description").item(0).getTextContent();
//
//                        articleList.add(new Article(title, link, description));
//                    }
//                }
//
//                requireActivity().runOnUiThread(() -> adapter.notifyDataSetChanged());
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }).start();
//    }
//}

//public class MedicationFragment extends Fragment implements ArticleAdapter.OnItemClickListener {
//
//    private RecyclerView recyclerView;
//    private ArticleAdapter adapter;
//    private List<Article> allArticles = new ArrayList<>();
//    private SwipeRefreshLayout swipeRefreshLayout;
//    private SearchView searchView;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
//                             @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_medication, container, false);
//
//        recyclerView = view.findViewById(R.id.recyclerView);
//        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
//        searchView = view.findViewById(R.id.searchView);
//
//        adapter = new ArticleAdapter(new ArrayList<>(), this);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.setAdapter(adapter);
//
//        swipeRefreshLayout.setOnRefreshListener(this::fetchArticles);
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override public boolean onQueryTextSubmit(String query) { return false; }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                filterArticles(newText);
//                return true;
//            }
//        });
//
//        fetchArticles();
//        return view;
//    }
//
//    private void fetchArticles() {
//        swipeRefreshLayout.setRefreshing(true);
//        new Thread(() -> {
//            try {
////                Document doc = Jsoup.connect("https://magazine.medlineplus.gov/").get();
//                Document doc = Jsoup.connect("https://www.who.int/health-topics/").get();
//                Elements articleCards = doc.select(".Card_cardWrapper__flTRm");
//
//                List<Article> articles = new ArrayList<>();
//                for (Element card : articleCards) {
//                    String title = card.select(".Card_title__zA5UO").text();
//                    String link = "https://www.who.int/health-topics/" + card.select("a").attr("href");
//                    String imageUrl = card.select("img").attr("src");
//                    String date = card.select(".Card_date__n64GF").text();
//
//                    articles.add(new Article(title, link, imageUrl, date));
//                }
//
//                requireActivity().runOnUiThread(() -> {
//                    allArticles.clear();
//                    allArticles.addAll(articles);
//                    adapter.updateArticles(articles);
//                    swipeRefreshLayout.setRefreshing(false);
//                });
//            } catch (Exception e) {
//                requireActivity().runOnUiThread(() -> {
//                    swipeRefreshLayout.setRefreshing(false);
//                    Toast.makeText(getContext(), "Failed to fetch articles", Toast.LENGTH_SHORT).show();
//                });
//            }
//        }).start();
//    }
//
//    private void filterArticles(String query) {
//        List<Article> filtered = new ArrayList<>();
//        for (Article article : allArticles) {
//            if (article.getTitle().toLowerCase().contains(query.toLowerCase())) {
//                filtered.add(article);
//            }
//        }
//        adapter.updateArticles(filtered);
//    }
//
//    @Override
//    public void onItemClick(Article article) {
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setData(Uri.parse(article.getLink()));
//        startActivity(intent);
//    }
//}
//package com.first.demo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.first.demo.adaptors.ArticleAdapter;
import com.first.demo.models.Article;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class MedicationFragment extends Fragment implements ArticleAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private ArticleAdapter adapter;
    private List<Article> allArticles = new ArrayList<>();
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
                    String description = "";  // WHO doesn't show desc in topics

                    articles.add(new Article(title, link, description));
                }

                requireActivity().runOnUiThread(() -> {
                    allArticles.clear();
                    allArticles.addAll(articles);
                    adapter.updateArticles(articles);
                    swipeRefreshLayout.setRefreshing(false);
                });
            } catch (Exception e) {
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
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(article.getLink()));
        startActivity(intent);
    }
}
