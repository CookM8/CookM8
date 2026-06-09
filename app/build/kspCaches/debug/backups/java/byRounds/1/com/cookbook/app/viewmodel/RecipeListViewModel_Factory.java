package com.cookbook.app.viewmodel;

import androidx.lifecycle.SavedStateHandle;
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
public final class RecipeListViewModel_Factory implements Factory<RecipeListViewModel> {
  private final Provider<RecipeRepository> repositoryProvider;

  private final Provider<SavedStateHandle> savedStateHandleProvider;

  public RecipeListViewModel_Factory(Provider<RecipeRepository> repositoryProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    this.repositoryProvider = repositoryProvider;
    this.savedStateHandleProvider = savedStateHandleProvider;
  }

  @Override
  public RecipeListViewModel get() {
    return newInstance(repositoryProvider.get(), savedStateHandleProvider.get());
  }

  public static RecipeListViewModel_Factory create(Provider<RecipeRepository> repositoryProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    return new RecipeListViewModel_Factory(repositoryProvider, savedStateHandleProvider);
  }

  public static RecipeListViewModel newInstance(RecipeRepository repository,
      SavedStateHandle savedStateHandle) {
    return new RecipeListViewModel(repository, savedStateHandle);
  }
}
