package com.cookbook.app.di

import android.content.Context
import androidx.room.Room
import com.cookbook.app.data.local.CookbookDatabase
import com.cookbook.app.data.local.dao.CategoryDao
import com.cookbook.app.data.local.dao.RecipeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): CookbookDatabase =
        Room.databaseBuilder(context, CookbookDatabase::class.java, "cookbook.db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideRecipeDao(db: CookbookDatabase): RecipeDao = db.recipeDao()

    @Provides
    fun provideCategoryDao(db: CookbookDatabase): CategoryDao = db.categoryDao()
}
