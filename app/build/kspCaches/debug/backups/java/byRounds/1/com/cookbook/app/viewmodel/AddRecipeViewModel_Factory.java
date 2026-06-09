package com.cookbook.app.viewmodel;

import android.content.Context;
import com.cookbook.app.data.repository.RecipeRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class AddRecipeViewModel_Factory implements Factory<AddRecipeViewModel> {
  private final Provider<RecipeRepository> repositoryProvider;

  private final Provider<Context> contextProvider;

  public AddRecipeViewModel_Factory(Provider<RecipeRepository> repositoryProvider,
      Provider<Context> contextProvider) {
    this.repositoryProvider = repositoryProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public AddRecipeViewModel get() {
    return newInstance(repositoryProvider.get(), contextProvider.get());
  }

  public static AddRecipeViewModel_Factory create(Provider<RecipeRepository> repositoryProvider,
      Provider<Context> contextProvider) {
    return new AddRecipeViewModel_Factory(repositoryProvider, contextProvider);
  }

  public static AddRecipeViewModel newInstance(RecipeRepository repository, Context context) {
    return new AddRecipeViewModel(repository, context);
  }
}
