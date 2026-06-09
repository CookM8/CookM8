package com.cookbook.app.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.cookbook.app.data.local.entities.RecipeEntity;
import com.cookbook.app.data.local.entities.StringListConverter;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class RecipeDao_Impl implements RecipeDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<RecipeEntity> __insertionAdapterOfRecipeEntity;

  private final StringListConverter __stringListConverter = new StringListConverter();

  private final EntityDeletionOrUpdateAdapter<RecipeEntity> __deletionAdapterOfRecipeEntity;

  private final EntityDeletionOrUpdateAdapter<RecipeEntity> __updateAdapterOfRecipeEntity;

  private final SharedSQLiteStatement __preparedStmtOfUpdateFavoriteStatus;

  public RecipeDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfRecipeEntity = new EntityInsertionAdapter<RecipeEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `recipes` (`id`,`title`,`category`,`cookingTimeMinutes`,`servings`,`description`,`ingredients`,`instructions`,`imageUrls`,`videoUrl`,`audioUrl`,`isFavorite`,`rating`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final RecipeEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getTitle());
        statement.bindString(3, entity.getCategory());
        statement.bindLong(4, entity.getCookingTimeMinutes());
        statement.bindLong(5, entity.getServings());
        statement.bindString(6, entity.getDescription());
        final String _tmp = __stringListConverter.fromList(entity.getIngredients());
        statement.bindString(7, _tmp);
        final String _tmp_1 = __stringListConverter.fromList(entity.getInstructions());
        statement.bindString(8, _tmp_1);
        final String _tmp_2 = __stringListConverter.fromList(entity.getImageUrls());
        statement.bindString(9, _tmp_2);
        if (entity.getVideoUrl() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getVideoUrl());
        }
        if (entity.getAudioUrl() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getAudioUrl());
        }
        final int _tmp_3 = entity.isFavorite() ? 1 : 0;
        statement.bindLong(12, _tmp_3);
        statement.bindDouble(13, entity.getRating());
        statement.bindLong(14, entity.getCreatedAt());
      }
    };
    this.__deletionAdapterOfRecipeEntity = new EntityDeletionOrUpdateAdapter<RecipeEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `recipes` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final RecipeEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfRecipeEntity = new EntityDeletionOrUpdateAdapter<RecipeEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `recipes` SET `id` = ?,`title` = ?,`category` = ?,`cookingTimeMinutes` = ?,`servings` = ?,`description` = ?,`ingredients` = ?,`instructions` = ?,`imageUrls` = ?,`videoUrl` = ?,`audioUrl` = ?,`isFavorite` = ?,`rating` = ?,`createdAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final RecipeEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getTitle());
        statement.bindString(3, entity.getCategory());
        statement.bindLong(4, entity.getCookingTimeMinutes());
        statement.bindLong(5, entity.getServings());
        statement.bindString(6, entity.getDescription());
        final String _tmp = __stringListConverter.fromList(entity.getIngredients());
        statement.bindString(7, _tmp);
        final String _tmp_1 = __stringListConverter.fromList(entity.getInstructions());
        statement.bindString(8, _tmp_1);
        final String _tmp_2 = __stringListConverter.fromList(entity.getImageUrls());
        statement.bindString(9, _tmp_2);
        if (entity.getVideoUrl() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getVideoUrl());
        }
        if (entity.getAudioUrl() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getAudioUrl());
        }
        final int _tmp_3 = entity.isFavorite() ? 1 : 0;
        statement.bindLong(12, _tmp_3);
        statement.bindDouble(13, entity.getRating());
        statement.bindLong(14, entity.getCreatedAt());
        statement.bindLong(15, entity.getId());
      }
    };
    this.__preparedStmtOfUpdateFavoriteStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE recipes SET isFavorite = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertRecipe(final RecipeEntity recipe,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfRecipeEntity.insertAndReturnId(recipe);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertRecipes(final List<RecipeEntity> recipes,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfRecipeEntity.insert(recipes);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteRecipe(final RecipeEntity recipe,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfRecipeEntity.handle(recipe);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateRecipe(final RecipeEntity recipe,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfRecipeEntity.handle(recipe);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateFavoriteStatus(final long id, final boolean isFavorite,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateFavoriteStatus.acquire();
        int _argIndex = 1;
        final int _tmp = isFavorite ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateFavoriteStatus.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<RecipeEntity>> getAllRecipes() {
    final String _sql = "SELECT * FROM recipes ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"recipes"}, new Callable<List<RecipeEntity>>() {
      @Override
      @NonNull
      public List<RecipeEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfCookingTimeMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "cookingTimeMinutes");
          final int _cursorIndexOfServings = CursorUtil.getColumnIndexOrThrow(_cursor, "servings");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfIngredients = CursorUtil.getColumnIndexOrThrow(_cursor, "ingredients");
          final int _cursorIndexOfInstructions = CursorUtil.getColumnIndexOrThrow(_cursor, "instructions");
          final int _cursorIndexOfImageUrls = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrls");
          final int _cursorIndexOfVideoUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "videoUrl");
          final int _cursorIndexOfAudioUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "audioUrl");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<RecipeEntity> _result = new ArrayList<RecipeEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final RecipeEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final int _tmpCookingTimeMinutes;
            _tmpCookingTimeMinutes = _cursor.getInt(_cursorIndexOfCookingTimeMinutes);
            final int _tmpServings;
            _tmpServings = _cursor.getInt(_cursorIndexOfServings);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final List<String> _tmpIngredients;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfIngredients);
            _tmpIngredients = __stringListConverter.toList(_tmp);
            final List<String> _tmpInstructions;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfInstructions);
            _tmpInstructions = __stringListConverter.toList(_tmp_1);
            final List<String> _tmpImageUrls;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfImageUrls);
            _tmpImageUrls = __stringListConverter.toList(_tmp_2);
            final String _tmpVideoUrl;
            if (_cursor.isNull(_cursorIndexOfVideoUrl)) {
              _tmpVideoUrl = null;
            } else {
              _tmpVideoUrl = _cursor.getString(_cursorIndexOfVideoUrl);
            }
            final String _tmpAudioUrl;
            if (_cursor.isNull(_cursorIndexOfAudioUrl)) {
              _tmpAudioUrl = null;
            } else {
              _tmpAudioUrl = _cursor.getString(_cursorIndexOfAudioUrl);
            }
            final boolean _tmpIsFavorite;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp_3 != 0;
            final float _tmpRating;
            _tmpRating = _cursor.getFloat(_cursorIndexOfRating);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new RecipeEntity(_tmpId,_tmpTitle,_tmpCategory,_tmpCookingTimeMinutes,_tmpServings,_tmpDescription,_tmpIngredients,_tmpInstructions,_tmpImageUrls,_tmpVideoUrl,_tmpAudioUrl,_tmpIsFavorite,_tmpRating,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<RecipeEntity>> getRecipesByCategory(final String category) {
    final String _sql = "SELECT * FROM recipes WHERE category = ? ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, category);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"recipes"}, new Callable<List<RecipeEntity>>() {
      @Override
      @NonNull
      public List<RecipeEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfCookingTimeMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "cookingTimeMinutes");
          final int _cursorIndexOfServings = CursorUtil.getColumnIndexOrThrow(_cursor, "servings");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfIngredients = CursorUtil.getColumnIndexOrThrow(_cursor, "ingredients");
          final int _cursorIndexOfInstructions = CursorUtil.getColumnIndexOrThrow(_cursor, "instructions");
          final int _cursorIndexOfImageUrls = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrls");
          final int _cursorIndexOfVideoUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "videoUrl");
          final int _cursorIndexOfAudioUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "audioUrl");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<RecipeEntity> _result = new ArrayList<RecipeEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final RecipeEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final int _tmpCookingTimeMinutes;
            _tmpCookingTimeMinutes = _cursor.getInt(_cursorIndexOfCookingTimeMinutes);
            final int _tmpServings;
            _tmpServings = _cursor.getInt(_cursorIndexOfServings);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final List<String> _tmpIngredients;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfIngredients);
            _tmpIngredients = __stringListConverter.toList(_tmp);
            final List<String> _tmpInstructions;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfInstructions);
            _tmpInstructions = __stringListConverter.toList(_tmp_1);
            final List<String> _tmpImageUrls;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfImageUrls);
            _tmpImageUrls = __stringListConverter.toList(_tmp_2);
            final String _tmpVideoUrl;
            if (_cursor.isNull(_cursorIndexOfVideoUrl)) {
              _tmpVideoUrl = null;
            } else {
              _tmpVideoUrl = _cursor.getString(_cursorIndexOfVideoUrl);
            }
            final String _tmpAudioUrl;
            if (_cursor.isNull(_cursorIndexOfAudioUrl)) {
              _tmpAudioUrl = null;
            } else {
              _tmpAudioUrl = _cursor.getString(_cursorIndexOfAudioUrl);
            }
            final boolean _tmpIsFavorite;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp_3 != 0;
            final float _tmpRating;
            _tmpRating = _cursor.getFloat(_cursorIndexOfRating);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new RecipeEntity(_tmpId,_tmpTitle,_tmpCategory,_tmpCookingTimeMinutes,_tmpServings,_tmpDescription,_tmpIngredients,_tmpInstructions,_tmpImageUrls,_tmpVideoUrl,_tmpAudioUrl,_tmpIsFavorite,_tmpRating,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<RecipeEntity>> getFavoriteRecipes() {
    final String _sql = "SELECT * FROM recipes WHERE isFavorite = 1 ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"recipes"}, new Callable<List<RecipeEntity>>() {
      @Override
      @NonNull
      public List<RecipeEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfCookingTimeMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "cookingTimeMinutes");
          final int _cursorIndexOfServings = CursorUtil.getColumnIndexOrThrow(_cursor, "servings");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfIngredients = CursorUtil.getColumnIndexOrThrow(_cursor, "ingredients");
          final int _cursorIndexOfInstructions = CursorUtil.getColumnIndexOrThrow(_cursor, "instructions");
          final int _cursorIndexOfImageUrls = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrls");
          final int _cursorIndexOfVideoUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "videoUrl");
          final int _cursorIndexOfAudioUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "audioUrl");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<RecipeEntity> _result = new ArrayList<RecipeEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final RecipeEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final int _tmpCookingTimeMinutes;
            _tmpCookingTimeMinutes = _cursor.getInt(_cursorIndexOfCookingTimeMinutes);
            final int _tmpServings;
            _tmpServings = _cursor.getInt(_cursorIndexOfServings);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final List<String> _tmpIngredients;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfIngredients);
            _tmpIngredients = __stringListConverter.toList(_tmp);
            final List<String> _tmpInstructions;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfInstructions);
            _tmpInstructions = __stringListConverter.toList(_tmp_1);
            final List<String> _tmpImageUrls;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfImageUrls);
            _tmpImageUrls = __stringListConverter.toList(_tmp_2);
            final String _tmpVideoUrl;
            if (_cursor.isNull(_cursorIndexOfVideoUrl)) {
              _tmpVideoUrl = null;
            } else {
              _tmpVideoUrl = _cursor.getString(_cursorIndexOfVideoUrl);
            }
            final String _tmpAudioUrl;
            if (_cursor.isNull(_cursorIndexOfAudioUrl)) {
              _tmpAudioUrl = null;
            } else {
              _tmpAudioUrl = _cursor.getString(_cursorIndexOfAudioUrl);
            }
            final boolean _tmpIsFavorite;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp_3 != 0;
            final float _tmpRating;
            _tmpRating = _cursor.getFloat(_cursorIndexOfRating);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new RecipeEntity(_tmpId,_tmpTitle,_tmpCategory,_tmpCookingTimeMinutes,_tmpServings,_tmpDescription,_tmpIngredients,_tmpInstructions,_tmpImageUrls,_tmpVideoUrl,_tmpAudioUrl,_tmpIsFavorite,_tmpRating,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<RecipeEntity> getRecipeById(final long id) {
    final String _sql = "SELECT * FROM recipes WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"recipes"}, new Callable<RecipeEntity>() {
      @Override
      @Nullable
      public RecipeEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfCookingTimeMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "cookingTimeMinutes");
          final int _cursorIndexOfServings = CursorUtil.getColumnIndexOrThrow(_cursor, "servings");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfIngredients = CursorUtil.getColumnIndexOrThrow(_cursor, "ingredients");
          final int _cursorIndexOfInstructions = CursorUtil.getColumnIndexOrThrow(_cursor, "instructions");
          final int _cursorIndexOfImageUrls = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrls");
          final int _cursorIndexOfVideoUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "videoUrl");
          final int _cursorIndexOfAudioUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "audioUrl");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final RecipeEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final int _tmpCookingTimeMinutes;
            _tmpCookingTimeMinutes = _cursor.getInt(_cursorIndexOfCookingTimeMinutes);
            final int _tmpServings;
            _tmpServings = _cursor.getInt(_cursorIndexOfServings);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final List<String> _tmpIngredients;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfIngredients);
            _tmpIngredients = __stringListConverter.toList(_tmp);
            final List<String> _tmpInstructions;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfInstructions);
            _tmpInstructions = __stringListConverter.toList(_tmp_1);
            final List<String> _tmpImageUrls;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfImageUrls);
            _tmpImageUrls = __stringListConverter.toList(_tmp_2);
            final String _tmpVideoUrl;
            if (_cursor.isNull(_cursorIndexOfVideoUrl)) {
              _tmpVideoUrl = null;
            } else {
              _tmpVideoUrl = _cursor.getString(_cursorIndexOfVideoUrl);
            }
            final String _tmpAudioUrl;
            if (_cursor.isNull(_cursorIndexOfAudioUrl)) {
              _tmpAudioUrl = null;
            } else {
              _tmpAudioUrl = _cursor.getString(_cursorIndexOfAudioUrl);
            }
            final boolean _tmpIsFavorite;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp_3 != 0;
            final float _tmpRating;
            _tmpRating = _cursor.getFloat(_cursorIndexOfRating);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new RecipeEntity(_tmpId,_tmpTitle,_tmpCategory,_tmpCookingTimeMinutes,_tmpServings,_tmpDescription,_tmpIngredients,_tmpInstructions,_tmpImageUrls,_tmpVideoUrl,_tmpAudioUrl,_tmpIsFavorite,_tmpRating,_tmpCreatedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<RecipeEntity>> searchRecipes(final String query) {
    final String _sql = "SELECT * FROM recipes WHERE title LIKE '%' || ? || '%' OR description LIKE '%' || ? || '%'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, query);
    _argIndex = 2;
    _statement.bindString(_argIndex, query);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"recipes"}, new Callable<List<RecipeEntity>>() {
      @Override
      @NonNull
      public List<RecipeEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfCookingTimeMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "cookingTimeMinutes");
          final int _cursorIndexOfServings = CursorUtil.getColumnIndexOrThrow(_cursor, "servings");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfIngredients = CursorUtil.getColumnIndexOrThrow(_cursor, "ingredients");
          final int _cursorIndexOfInstructions = CursorUtil.getColumnIndexOrThrow(_cursor, "instructions");
          final int _cursorIndexOfImageUrls = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrls");
          final int _cursorIndexOfVideoUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "videoUrl");
          final int _cursorIndexOfAudioUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "audioUrl");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<RecipeEntity> _result = new ArrayList<RecipeEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final RecipeEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final int _tmpCookingTimeMinutes;
            _tmpCookingTimeMinutes = _cursor.getInt(_cursorIndexOfCookingTimeMinutes);
            final int _tmpServings;
            _tmpServings = _cursor.getInt(_cursorIndexOfServings);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final List<String> _tmpIngredients;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfIngredients);
            _tmpIngredients = __stringListConverter.toList(_tmp);
            final List<String> _tmpInstructions;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfInstructions);
            _tmpInstructions = __stringListConverter.toList(_tmp_1);
            final List<String> _tmpImageUrls;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfImageUrls);
            _tmpImageUrls = __stringListConverter.toList(_tmp_2);
            final String _tmpVideoUrl;
            if (_cursor.isNull(_cursorIndexOfVideoUrl)) {
              _tmpVideoUrl = null;
            } else {
              _tmpVideoUrl = _cursor.getString(_cursorIndexOfVideoUrl);
            }
            final String _tmpAudioUrl;
            if (_cursor.isNull(_cursorIndexOfAudioUrl)) {
              _tmpAudioUrl = null;
            } else {
              _tmpAudioUrl = _cursor.getString(_cursorIndexOfAudioUrl);
            }
            final boolean _tmpIsFavorite;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp_3 != 0;
            final float _tmpRating;
            _tmpRating = _cursor.getFloat(_cursorIndexOfRating);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new RecipeEntity(_tmpId,_tmpTitle,_tmpCategory,_tmpCookingTimeMinutes,_tmpServings,_tmpDescription,_tmpIngredients,_tmpInstructions,_tmpImageUrls,_tmpVideoUrl,_tmpAudioUrl,_tmpIsFavorite,_tmpRating,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getRecipeCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM recipes";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
