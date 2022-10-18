package com.team13.colonykeeper.data

import com.team13.colonykeeper.R

object DataSource {
    val yards: MutableList<BeeYard> = listOf(
        BeeYard("Bills House", R.drawable.beeyard_temp),
        BeeYard("Jane's House", R.drawable.beeyard_temp),
        BeeYard("Harold's Farm", R.drawable.beeyard_temp),
        BeeYard("My House", R.drawable.beeyard_temp),
        BeeYard("The Coopers", R.drawable.beeyard_temp),
    ).toMutableList()

}