package com.afeka.liadk.battleship;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afeka.liadk.battleship.Logic.GameSettingsInterface;

public class ResultActivity extends AppCompatActivity {

    private EditText mEditText;
    private Bundle mLevelBundle;
    private DataBaseHandler mDb;
    private int mStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent intent = getIntent();
        Bundle state = intent.getBundleExtra(GameActivity.GAME_STATUS);
        AnimationDrawable animationDrawable = null;
        int step;
        if (state != null) {
            String winner = state.getString(GameActivity.WHO_WIN);
            if (winner != null) {
                TextView whoWin = (TextView) findViewById(R.id.result);
                whoWin.setText(winner);
                if (winner.compareTo(getResources().getString(R.string.win)) == 0) {
                    mStep = state.getInt(GameActivity.NUMBER_OF_STEPS);
                    animationDrawable = (AnimationDrawable) getResources().getDrawable(R.drawable.gradient__list_win, null);
                    findViewById(R.id.loseResult).setVisibility(View.INVISIBLE);
                } else {
                    animationDrawable = (AnimationDrawable) getResources().getDrawable(R.drawable.gradient__list_lose, null);
                    findViewById(R.id.winResult).setVisibility(View.INVISIBLE);
                }
                ((RelativeLayout) findViewById(R.id.resultLayout)).setBackground(animationDrawable);
                animationDrawable.setEnterFadeDuration(1000);
                animationDrawable.setExitFadeDuration(2000);
                animationDrawable.start();
                if (winner.compareTo(getResources().getString(R.string.win)) == 0) {
                    mDb = new DataBaseHandler(this);
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    mEditText = new EditText(this);
                    builder.setTitle(R.string.enter_name).setView(mEditText).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (mEditText.getText().length() != 0) {
                                mDb.insertWinner(mLevelBundle.getSerializable(GameSettingsInterface.LEVEL_CHOOSEN).toString(), mEditText.getText().toString(), mStep);
                            }
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        }
        mLevelBundle = intent.getBundleExtra(GameSettingsInterface.LEVEL_MESSAGE);
    }

    public void createNewGame(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public void tryAgainGame(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(GameSettingsInterface.LEVEL_MESSAGE, mLevelBundle);
        startActivity(intent);
        finish();
    }
}
