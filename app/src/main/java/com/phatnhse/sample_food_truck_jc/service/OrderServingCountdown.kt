package com.phatnhse.sample_food_truck_jc.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.phatnhse.sample_food_truck_jc.MainActivity
import com.phatnhse.sample_food_truck_jc.R

object CountdownNotificationHelper {

    private val timers = mutableMapOf<Int, Triple<CountDownTimer, Int, Long>>()

    fun showCountdownNotification(context: Context, notificationId: Int, donuts: Int) {
        if (timers.containsKey(notificationId)) {
            return // Do not start a new timer if one is already running with the same ID
        }

        val notificationManager = NotificationManagerCompat.from(context)
        val channelId = "countdown_timer_channel_$notificationId"
        createNotificationChannel(context, channelId)

        val groupId = "group-id"
        val group = NotificationChannelGroup(groupId, "Group Name")
        notificationManager.createNotificationChannelGroup(group)

        val originalTimestamp = System.currentTimeMillis()
        val countDownTimer = object : CountDownTimer(60_000, 1_000) {
            override fun onTick(millisUntilFinished: Long) {
                val remainingTime = (millisUntilFinished / 1000).toInt()
                timers[notificationId] = Triple(this, remainingTime, originalTimestamp)
                val notification = createNotification(
                    context,
                    channelId,
                    notificationId,
                    remainingTime,
                    originalTimestamp,
                    donuts
                )
                notificationManager.notify(notificationId, notification)
            }

            override fun onFinish() {
                timers.remove(notificationId)
            }
        }.start()

        timers[notificationId] = Triple(countDownTimer, 60, originalTimestamp)
    }

    fun cancelCountdownNotification(context: Context, notificationId: Int) {
        timers[notificationId]?.first?.cancel()
        timers.remove(notificationId)
        NotificationManagerCompat.from(context).cancel(notificationId)
    }

    private fun createNotificationChannel(context: Context, channelId: String) {
        val channel = NotificationChannel(
            channelId,
            "Countdown Timer For Order ",
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            description = "A channel for the countdown timer notifications."
        }
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private const val NOTIFICATION_GROUP_KEY = "countdown_timers_group"

    private fun createNotification(
        context: Context,
        channelId: String,
        notificationId: Int,
        remainingTime: Int,
        originalTimestamp: Long,
        donuts: Int
    ): Notification {
        val pendingIntent: PendingIntent =
            Intent(context, MainActivity::class.java).let { notificationIntent ->
                PendingIntent.getActivity(
                    context,
                    notificationId,
                    notificationIntent,
                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                )
            }

        return NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.app_icon)
            .setContentTitle("Order $notificationId")
            .setContentText("$donuts donuts - 0:$remainingTime Remaining")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.app_icon)
            .setOngoing(true)
            .setWhen(originalTimestamp) // Set the original timestamp
            .setGroup(NOTIFICATION_GROUP_KEY) // Set a group key for the notifications
            .build()
    }
}