package com.cookbook.app.data.repository;

import com.cookbook.app.data.local.dao.CategoryDao;
import com.cookbook.app.data.local.dao.RecipeDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation"
})
public final class RecipeRepository_Factory implements Factory<RecipeRepository> {
  private final Provider<RecipeDao> recipeDaoProvider;

  private final Provider<CategoryDao> categoryDaoProvider;

  public RecipeRepository_Factory(Provider<RecipeDao> recipeDaoProvider,
      Provider<CategoryDao> categoryDaoProvider) {
    this.recipeDaoProvider = recipeDaoProvider;
    this.categoryDaoProvider = categoryDaoProvider;
  }

  @Override
  public RecipeRepository get() {
    return newInstance(recipeDaoProvider.get(), categoryDaoProvider.get());
  }

  public static RecipeRepository_Factory create(Provider<RecipeDao> recipeDaoProvider,
      Provider<CategoryDao> categoryDaoProvider) {
    return new RecipeRepository_Factory(recipeDaoProvider, categoryDaoProvider);
  }

  public static RecipeRepository newInstance(RecipeDao recipeDao, CategoryDao categoryDao) {
    return new RecipeRepository(recipeDao, categoryDao);
  }
}
