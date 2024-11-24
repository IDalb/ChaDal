package ca.uqac.etu.jcid.chadal.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Query("SELECT * FROM article_table ORDER BY name ASC")
    fun getAllArticles(): List<Article> // Retourne une liste d'articles
}
