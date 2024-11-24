package ca.uqac.etu.jcid.chadal.data


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
@Database(entities = [ShoppingListEntity::class, Article::class], version = 4, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun shoppingListDao(): ShoppingListDao
    abstract fun articleDao(): ArticleDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // Définir la migration de la version 2 à 3
        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Vérifiez si la table existe
                val cursor = database.query("SELECT name FROM sqlite_master WHERE type='table' AND name='article_table'")
                val tableExists = cursor.moveToFirst()
                cursor.close()

                if (tableExists) {
                    // Renommer l'ancienne table
                    database.execSQL("ALTER TABLE article_table RENAME TO article_table_old")
                }

                // Créer la nouvelle table
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
                    // Copier les données (avec transformation si nécessaire)
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

                    // Supprimer l'ancienne table
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
                    .addMigrations(MIGRATION_2_3) // Ajout de la migration ici
                    .fallbackToDestructiveMigration() // Optionnel : supprime les données en cas de changement non géré
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
