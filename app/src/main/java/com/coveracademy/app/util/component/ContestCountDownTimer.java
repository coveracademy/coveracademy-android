package com.coveracademy.app.util.component;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import com.coveracademy.api.model.Contest;
import com.coveracademy.app.R;

import butterknife.ButterKnife;

public class ContestCountDownTimer {

  private CountDownTimer countDownTimer;

  public ContestCountDownTimer(final Context context, Contest contest, View view) {
    final TextView daysRemainingView = ButterKnife.findById(view, R.id.days_remaining);
    final TextView hoursRemainingView = ButterKnife.findById(view, R.id.hours_remaining);
    final TextView minutesRemainingView = ButterKnife.findById(view, R.id.minutes_remaining);
    final TextView secondsRemainingView = ButterKnife.findById(view, R.id.seconds_remaining);
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