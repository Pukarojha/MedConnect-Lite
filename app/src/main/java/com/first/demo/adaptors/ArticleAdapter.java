package com.first.demo.adaptors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.first.demo.R;
import com.first.demo.models.Article;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Article article);
    }

    private List<Article> articles;
    private final OnItemClickListener listener;

    public ArticleAdapter(List<Article> articles, OnItemClickListener listener) {
        this.articles = articles;
        this.listener = listener;
    }

    public void updateArticles(List<Article> updated) {
        this.articles = updated;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        holder.bind(articles.get(position));
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder {
        TextView titleView, descriptionView;
        ImageView thumbnail;

        ArticleViewHolder(View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.articleTitle);
            descriptionView = itemView.findViewById(R.id.articleDate); // Renamed in layout
            thumbnail = itemView.findViewById(R.id.articleImage);
        }

        void bind(Article article) {
            titleView.setText(article.getTitle());
            descriptionView.setText(article.getDescription().isEmpty() ? "Tap to view more" : article.getDescription());
            Glide.with(thumbnail.getContext()).load(article.getImageUrl()).into(thumbnail);
            itemView.setOnClickListener(v -> listener.onItemClick(article));
        }
    }
}
