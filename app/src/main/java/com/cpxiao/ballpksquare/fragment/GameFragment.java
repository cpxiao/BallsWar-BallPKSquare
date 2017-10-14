package com.cpxiao.ballpksquare.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.cpxiao.R;
import com.cpxiao.androidutils.library.utils.PreferencesUtils;
import com.cpxiao.androidutils.library.utils.ThreadUtils;
import com.cpxiao.ballpksquare.OnGameListener;
import com.cpxiao.ballpksquare.mode.extra.Extra;
import com.cpxiao.ballpksquare.views.ShooterGameView;
import com.cpxiao.gamelib.fragment.BaseZAdsFragment;

/**
 * @author cpxiao on 2017/9/27.
 */

public class GameFragment extends BaseZAdsFragment {
    private ShooterGameView shooterGameView;

    public static GameFragment newInstance(Bundle bundle) {
        GameFragment fragment = new GameFragment();
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        final Context context = getHoldingActivity();
        shooterGameView = (ShooterGameView) view.findViewById(R.id.shooterGameView);
        shooterGameView.setOnGameListener(new OnGameListener() {
            @Override
            public void onGameOver(int score) {
                showGameOverDialog(context, score);
            }

        });
    }


    private void showGameOverDialog(final Context context, final int score) {
        ThreadUtils.getInstance().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int bestScore = PreferencesUtils.getInt(context, Extra.Key.BEST_SCORE, 0);
                bestScore = Math.max(score, bestScore);
                PreferencesUtils.putInt(context, Extra.Key.BEST_SCORE, score);

                String msg = getString(R.string.score) + ": " + score + "\n"
                        + getString(R.string.best_score) + ": " + bestScore;
                AlertDialog dialog = new AlertDialog.Builder(context)
                        .setTitle(R.string.game_over)
                        .setMessage(msg)
                        .setPositiveButton(R.string.play_again, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (shooterGameView != null) {
                                    shooterGameView.restart();
                                }
                                dialog.dismiss();
                            }
                        })
                        .create();
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_game;
    }
}
