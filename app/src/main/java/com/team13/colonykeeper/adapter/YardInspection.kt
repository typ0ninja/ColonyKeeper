package com.team13.colonykeeper.adapter

import com.team13.colonykeeper.database.Scheduled
import com.team13.colonykeeper.database.Yard

data class YardInspection(
    val yard: Yard,
    val ScheduledInspections: List<Scheduled>)