package com.first.demo.adaptors;

import com.first.demo.R;
import com.first.demo.models.Article;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(com.first.demo.models.Article article);
    }

    private List<Article> articles;
    private OnItemClickListener listener;

    public ArticleAdapter(List<Article> articles, OnItemClickListener listener) {
        this.articles = articles;
        this.listener = listener;
    }

    public void updateArticles(List<Article> updated) {
        this.articles = updated;
        notifyDataSetChanged();
    }

    @NonNull @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_article, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        holder.bind(articles.get(position));
    }

    @Override public int getItemCount() {
        return articles.size();
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder {
        TextView titleView, dateView;
        ImageView thumbnail;

        ArticleViewHolder(View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.articleTitle);
            dateView = itemView.findViewById(R.id.articleDate);
            thumbnail = itemView.findViewById(R.id.articleImage);
        }

        void bind(Article article) {
            titleView.setText(article.getTitle());
            dateView.setText(article.getDescription());
            Glide.with(thumbnail.getContext()).load(article.getLink()).into(thumbnail);
            itemView.setOnClickListener(v -> listener.onItemClick(article));
        }
    }
}
