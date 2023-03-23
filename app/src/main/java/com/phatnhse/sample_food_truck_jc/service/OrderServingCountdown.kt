package com.phatnhse.sample_food_truck_jc.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.CountDownTimer
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.graphics.drawable.toBitmap
import com.phatnhse.sample_food_truck_jc.MainActivity
import com.phatnhse.sample_food_truck_jc.R

object CountdownNotificationHelper {
    private val timers = mutableMapOf<Int, Triple<CountDownTimer, Int, Long>>()

    private const val CHANNEL_ID = "order-scheduler-channel-id"
    private const val CHANNEL_NAME = "Order Scheduler Channel"
    private const val CHANNEL_DESCRIPTION = "A channel for the order scheduler notifications."
    private const val NOTIFICATION_GROUP_KEY = "order-schedule-group-key"
    private const val NOTIFICATION_GROUP_NAME = "Order Scheduler Group"

    private const val TIME_COMPLETE_SINGLE_ORDER = 60_000L
    private const val TIME_COUNTDOWN_INTERVAL = 1_000L

    fun showCountdownNotification(context: Context, notificationId: Int, donuts: Int) {
        if (timers.containsKey(notificationId)) {
            return // Do not start a new timer if one is already running with the same ID
        }

        val notificationManager = NotificationManagerCompat.from(context)
        createNotificationChannel(context)

        val group = NotificationChannelGroup(NOTIFICATION_GROUP_KEY, NOTIFICATION_GROUP_NAME)
        notificationManager.createNotificationChannelGroup(group)

        val originalTimestamp = System.currentTimeMillis()
        val countDownTimer =
            object : CountDownTimer(TIME_COMPLETE_SINGLE_ORDER, TIME_COUNTDOWN_INTERVAL) {
                @RequiresApi(Build.VERSION_CODES.S)
                override fun onTick(millisUntilFinished: Long) {
                    val remainingTime = (millisUntilFinished / 1000).toInt()
                    timers[notificationId] = Triple(this, remainingTime, originalTimestamp)
                    val notification = createNotification(
                        context,
                        notificationId,
                        remainingTime,
                        originalTimestamp,
                        donuts
                    )
                    notificationManager.notify(notificationId, notification)
                }

                override fun onFinish() {
                    // TODO
                    // Update Notification content when the order is completed
                    timers.remove(notificationId)
                }
            }.start()

        timers[notificationId] =
            Triple(countDownTimer, (TIME_COMPLETE_SINGLE_ORDER / 1000).toInt(), originalTimestamp)
    }

    fun cancelCountdownNotification(context: Context, notificationId: Int) {
        timers[notificationId]?.first?.cancel()
        timers.remove(notificationId)
        NotificationManagerCompat.from(context).cancel(notificationId)
    }

    private fun createNotificationChannel(context: Context) {
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            description = CHANNEL_DESCRIPTION
        }

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun createNotification(
        context: Context,
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

        val appIcon = context.getDrawable(R.drawable.app_icon)
        val notificationLargeIcon = appIcon?.toBitmap()

        val orderTitle = "Order $notificationId"
        val orderContent = "$donuts donuts - 0:$remainingTime Remaining"

        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.app_icon)
            .setContentTitle(orderTitle)
            .setContentText(orderContent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .setWhen(originalTimestamp)
            .setLargeIcon(notificationLargeIcon)
            .setGroup(NOTIFICATION_GROUP_KEY)
            .build()
    }
}