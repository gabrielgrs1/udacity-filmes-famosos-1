package br.com.gabriel.filmesfamosos1.ui.feed;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import br.com.gabriel.filmesfamosos1.BuildConfig;
import br.com.gabriel.filmesfamosos1.R;
import br.com.gabriel.filmesfamosos1.api.domain.MovieDto;
import br.com.gabriel.filmesfamosos1.utils.formatter.StringFormatter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {
    private final List<MovieDto.Result> mMovieList;
    private final FeedActivity mContext;

    FeedAdapter(FeedActivity context, List<MovieDto.Result> mMovieList) {
        this.mContext = context;
        this.mMovieList = mMovieList == null ? new ArrayList<>() : mMovieList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View createdView = LayoutInflater.from(mContext).inflate(R.layout.item_movie, viewGroup, false);
        ButterKnife.bind(mContext);
        return new ViewHolder(createdView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(mMovieList.get(i));
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_movie_banner_imageview)
        ImageView mBannerImageView;

        @BindView(R.id.item_movie_date_textview)
        TextView mReleaseDateTextView;

        @BindView(R.id.item_movie_title_textview)
        TextView mTitleTextView;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(MovieDto.Result movie) {
            mReleaseDateTextView.setText(StringFormatter.configureDateToYear(movie.getReleaseDate()));
            mTitleTextView.setText(movie.getTitle());
            setPosterImage(BuildConfig.IMAGE_URL + "/" + movie.getPosterPath().replace(".png", ".svg"));
        }

        void setPosterImage(String posterUrl) {
            Glide.with(mContext)
                    .load(posterUrl)
                    .into(mBannerImageView);
        }

        @OnClick
        void onClick() {
            mContext.goToDetailBy(mMovieList.get(getAdapterPosition()));
        }
    }
}
