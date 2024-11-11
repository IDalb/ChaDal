package ca.uqac.etu.jcid.chadal.data


import java.text.SimpleDateFormat
import java.util.Locale


data class Course(
    val budget: String = "",
    val date: String = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(System.currentTimeMillis()),
    val articleCount: Int = 0,
    val timestamp: Long = System.currentTimeMillis()
)