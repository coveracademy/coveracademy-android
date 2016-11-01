package com.coveracademy.app.util.component;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.coveracademy.api.model.Contest;
import com.coveracademy.app.R;

public class ContestCountDownTimer {

  private CountDownTimer countDownTimer;

  public ContestCountDownTimer(final Context context, Contest contest, final TextView daysRemainingView, final TextView hoursRemainingView, final TextView minutesRemainingView, final TextView secondsRemainingView) {
    long millisInFuture = contest.getEndDate().getTime() - System.currentTimeMillis();
    countDownTimer = new CountDownTimer(millisInFuture, 1000) {

      @Override
      public void onTick(long millisUntilFinished) {
        int days = (int) ((millisUntilFinished / 1000) / 86400);
        int hours = (int) (((millisUntilFinished / 1000) - (days * 86400)) / 3600);
        int minutes = (int) (((millisUntilFinished / 1000) - (days * 86400) - (hours * 3600)) / 60);
        int seconds = (int) ((millisUntilFinished / 1000) % 60);
        daysRemainingView.setText(context.getString(R.string.activity_contest_days_remaining, days));
        hoursRemainingView.setText(context.getString(R.string.activity_contest_hours_remaining, hours));
        minutesRemainingView.setText(context.getString(R.string.activity_contest_minutes_remaining, minutes));
        secondsRemainingView.setText(context.getString(R.string.activity_contest_seconds_remaining, seconds));
      }

      @Override
      public void onFinish() {

      }
    };
  }

  public void start() {
    countDownTimer.start();
  }
}