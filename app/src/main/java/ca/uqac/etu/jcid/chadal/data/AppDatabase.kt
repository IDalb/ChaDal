package ca.uqac.etu.jcid.chadal.data


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
@Database(entities = [ShoppingListEntity::class, Article::class], version = 5, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun shoppingListDao(): ShoppingListDao
    abstract fun articleDao(): ArticleDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null


        private val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {

                val cursor = database.query("SELECT name FROM sqlite_master WHERE type='table' AND name='article_table'")
                val tableExists = cursor.moveToFirst()
                cursor.close()

                if (tableExists) {

                    database.execSQL("ALTER TABLE article_table RENAME TO article_table_old")
                }


                database.execSQL(
                    """
                    CREATE TABLE article_table (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        name TEXT NOT NULL,
                        categoryName INTEGER NOT NULL,
                        price REAL NOT NULL,
                        taxPercentage REAL NOT NULL,
                        imageResource TEXT
                    )
                    """
                )

                if (tableExists) {

                    database.execSQL(
                        """
                        INSERT INTO article_table (id, name, categoryName, price, taxPercentage, imageResource)
                        SELECT id, name, 
                               CAST(category AS INTEGER), -- Convertir l'ancienne colonne `category` (TEXT) en INTEGER
                               price, 
                               0.0, -- Valeur par défaut pour taxPercentage
                               imageResource
                        FROM article_table_old
                        """
                    )


                    database.execSQL("DROP TABLE article_table_old")
                }
            }
        }

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "shopping_database"
                )
                    .addMigrations(MIGRATION_3_4)
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
