package com.cookbook.app.viewmodel;

import com.cookbook.app.data.repository.RecipeRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class FavoritesViewModel_Factory implements Factory<FavoritesViewModel> {
  private final Provider<RecipeRepository> repositoryProvider;

  public FavoritesViewModel_Factory(Provider<RecipeRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public FavoritesViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static FavoritesViewModel_Factory create(Provider<RecipeRepository> repositoryProvider) {
    return new FavoritesViewModel_Factory(repositoryProvider);
  }

  public static FavoritesViewModel newInstance(RecipeRepository repository) {
    return new FavoritesViewModel(repository);
  }
}
