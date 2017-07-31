package nl.linkit.itnext.pingpong.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nl.linkit.itnext.pingpong.R;
import nl.linkit.itnext.pingpong.core.AppDefine;
import nl.linkit.itnext.pingpong.core.PPApplication;
import nl.linkit.itnext.pingpong.custom.CircleTransform;
import nl.linkit.itnext.pingpong.managers.DBManager;
import nl.linkit.itnext.pingpong.managers.SchedulersManager;
import nl.linkit.itnext.pingpong.managers.VoiceManager;
import nl.linkit.itnext.pingpong.managers.VoiceManagerImpl;
import nl.linkit.itnext.pingpong.models.Player;
import nl.linkit.itnext.pingpong.ui.contracts.GameContract;
import nl.linkit.itnext.pingpong.ui.presenters.GamePresenter;

public class GameActivity extends AppCompatActivity implements GameContract.View {

    @BindView(R.id.first_avatar_imageview)
    public ImageView firstAvatar;

    @BindView(R.id.first_name_textview)
    public TextView firstName;

    @BindView(R.id.first_score_textview)
    public TextView firstScoreTextView;

    @BindView(R.id.first_service_imageview)
    public ImageView firstServiceImageView;

    @BindView(R.id.second_avatar_imageview)
    public ImageView secondAvatar;

    @BindView(R.id.second_name_textview)
    public TextView secondName;

    @BindView(R.id.second_score_textview)
    public TextView secondScoreTextView;

    @BindView(R.id.second_service_imageview)
    public ImageView secondServiceImageView;

    @Inject
    DBManager dbManager;

    @Inject
    SchedulersManager schedulersManager;
    private GamePresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        ButterKnife.bind(this);

        PPApplication.getAppComponent().inject(this);

        VoiceManager voiceManager = new VoiceManagerImpl(this);

        presenter = new GamePresenter(this, voiceManager, schedulersManager, dbManager);
        presenter.onCreate();

    }

    @Override
    public void setFirstPlayer(Player player) {

        setAvatar(firstAvatar, player.getAvatarName());
        firstName.setText(player.getName());
        firstScoreTextView.setText("0");
    }

    @Override
    public void setSecondPlayer(Player player) {

        setAvatar(secondAvatar, player.getAvatarName());
        secondName.setText(player.getName());
        secondScoreTextView.setText("0");
    }

    @Override
    public void setServiceTurn(boolean isFirstPlayerServing) {

        firstServiceImageView.setVisibility(isFirstPlayerServing ? View.VISIBLE : View.INVISIBLE);
        secondServiceImageView.setVisibility(isFirstPlayerServing ? View.INVISIBLE : View.VISIBLE);
    }

    @Override
    public void setFirsPlayerScore(int score) {
        firstScoreTextView.setText(String.valueOf(score));
    }

    @Override
    public void setSecondPlayerScore(int score) {
        secondScoreTextView.setText(String.valueOf(score));
    }

    @Override
    public void finishGame() {
        finish();
    }

    private void setAvatar(ImageView imageView, String filename) {

        String url = AppDefine.BASE_URL + filename;
        Picasso.with(this).load(url)
                .transform(new CircleTransform())
                .into(imageView);
    }

    @Override
    protected void onPause() {
        super.onPause();

        presenter.onFinish();
    }

    @OnClick(R.id.first_avatar_imageview)
    public void firstAvatarClick() {

        presenter.incrementFirstPlayerScore();
    }

    @OnClick(R.id.second_avatar_imageview)
    public void secondAvatarClick() {

        presenter.incrementSecondPlayerScore();
    }
}
