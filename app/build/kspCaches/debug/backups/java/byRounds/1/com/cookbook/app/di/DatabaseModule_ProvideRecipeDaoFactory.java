package com.cookbook.app.di;

import com.cookbook.app.data.local.CookbookDatabase;
import com.cookbook.app.data.local.dao.RecipeDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class DatabaseModule_ProvideRecipeDaoFactory implements Factory<RecipeDao> {
  private final Provider<CookbookDatabase> dbProvider;

  public DatabaseModule_ProvideRecipeDaoFactory(Provider<CookbookDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public RecipeDao get() {
    return provideRecipeDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideRecipeDaoFactory create(
      Provider<CookbookDatabase> dbProvider) {
    return new DatabaseModule_ProvideRecipeDaoFactory(dbProvider);
  }

  public static RecipeDao provideRecipeDao(CookbookDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideRecipeDao(db));
  }
}
