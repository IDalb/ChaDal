package ca.uqac.etu.jcid.chadal.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import androidx.room.OnConflictStrategy
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
    @Query("SELECT * FROM article_table WHERE shoppingListId = :shoppingListId")
    fun getArticlesByShoppingListId(shoppingListId: Int): Flow<List<Article>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticle(article: Article)

    @Delete
    fun deleteArticle(article: Article)


    @Query("SELECT * FROM article_table")
    fun getAllArticles(): Flow<List<Article>>

}
