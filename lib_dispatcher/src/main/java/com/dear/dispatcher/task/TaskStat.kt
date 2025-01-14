package com.dear.dispatcher.task

import com.dear.tools.log.LogUtil
import java.util.concurrent.atomic.AtomicInteger

object TaskStat {

    @Volatile
    private var sCurrentSituation = ""
    private val sBeans: MutableList<TaskStatBean> = ArrayList()
    private var sTaskDoneCount = AtomicInteger()
    private const val sOpenLaunchStat = false // 是否开启统计

    var currentSituation: String
        get() = sCurrentSituation
        set(currentSituation) {
            if (!sOpenLaunchStat) {
                return
            }
            LogUtil.i("currentSituation   $currentSituation")
            sCurrentSituation = currentSituation
            setLaunchStat()
        }

    fun markTaskDone() {
        sTaskDoneCount.getAndIncrement()
    }

    fun setLaunchStat() {
        val bean = TaskStatBean()
        bean.situation = sCurrentSituation
        bean.count = sTaskDoneCount.get()
        sBeans.add(bean)
        sTaskDoneCount = AtomicInteger(0)
    }

}