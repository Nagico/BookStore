package com.nagico.bookstore.dao;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.SqlUtils;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.nagico.bookstore.models.Category;

import com.nagico.bookstore.models.Book;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "BOOK".
*/
public class BookDao extends AbstractDao<Book, Long> {

    public static final String TABLENAME = "BOOK";

    /**
     * Properties of entity Book.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Title = new Property(1, String.class, "title", false, "TITLE");
        public final static Property Author = new Property(2, String.class, "author", false, "AUTHOR");
        public final static Property Isbn = new Property(3, String.class, "isbn", false, "ISBN");
        public final static Property Cover = new Property(4, byte[].class, "cover", false, "COVER");
        public final static Property Description = new Property(5, String.class, "description", false, "DESCRIPTION");
        public final static Property CategoryId = new Property(6, Long.class, "categoryId", false, "CATEGORY_ID");
        public final static Property Price = new Property(7, double.class, "price", false, "PRICE");
        public final static Property CreatedAt = new Property(8, java.util.Date.class, "createdAt", false, "CREATED_AT");
        public final static Property UpdatedAt = new Property(9, java.util.Date.class, "updatedAt", false, "UPDATED_AT");
    }

    private DaoSession daoSession;


    public BookDao(DaoConfig config) {
        super(config);
    }
    
    public BookDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"BOOK\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"TITLE\" TEXT NOT NULL ," + // 1: title
                "\"AUTHOR\" TEXT NOT NULL ," + // 2: author
                "\"ISBN\" TEXT NOT NULL ," + // 3: isbn
                "\"COVER\" BLOB NOT NULL ," + // 4: cover
                "\"DESCRIPTION\" TEXT NOT NULL ," + // 5: description
                "\"CATEGORY_ID\" INTEGER NOT NULL ," + // 6: categoryId
                "\"PRICE\" REAL NOT NULL ," + // 7: price
                "\"CREATED_AT\" INTEGER NOT NULL ," + // 8: createdAt
                "\"UPDATED_AT\" INTEGER NOT NULL );"); // 9: updatedAt
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"BOOK\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Book entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getTitle());
        stmt.bindString(3, entity.getAuthor());
        stmt.bindString(4, entity.getIsbn());
        stmt.bindBlob(5, entity.getCover());
        stmt.bindString(6, entity.getDescription());
        stmt.bindLong(7, entity.getCategoryId());
        stmt.bindDouble(8, entity.getPrice());
        stmt.bindLong(9, entity.getCreatedAt().getTime());
        stmt.bindLong(10, entity.getUpdatedAt().getTime());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Book entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getTitle());
        stmt.bindString(3, entity.getAuthor());
        stmt.bindString(4, entity.getIsbn());
        stmt.bindBlob(5, entity.getCover());
        stmt.bindString(6, entity.getDescription());
        stmt.bindLong(7, entity.getCategoryId());
        stmt.bindDouble(8, entity.getPrice());
        stmt.bindLong(9, entity.getCreatedAt().getTime());
        stmt.bindLong(10, entity.getUpdatedAt().getTime());
    }

    @Override
    protected final void attachEntity(Book entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Book readEntity(Cursor cursor, int offset) {
        Book entity = new Book( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // title
            cursor.getString(offset + 2), // author
            cursor.getString(offset + 3), // isbn
            cursor.getBlob(offset + 4), // cover
            cursor.getString(offset + 5), // description
            cursor.getLong(offset + 6), // categoryId
            cursor.getDouble(offset + 7), // price
            new java.util.Date(cursor.getLong(offset + 8)), // createdAt
            new java.util.Date(cursor.getLong(offset + 9)) // updatedAt
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Book entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setTitle(cursor.getString(offset + 1));
        entity.setAuthor(cursor.getString(offset + 2));
        entity.setIsbn(cursor.getString(offset + 3));
        entity.setCover(cursor.getBlob(offset + 4));
        entity.setDescription(cursor.getString(offset + 5));
        entity.setCategoryId(cursor.getLong(offset + 6));
        entity.setPrice(cursor.getDouble(offset + 7));
        entity.setCreatedAt(new java.util.Date(cursor.getLong(offset + 8)));
        entity.setUpdatedAt(new java.util.Date(cursor.getLong(offset + 9)));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Book entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Book entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Book entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getCategoryDao().getAllColumns());
            builder.append(" FROM BOOK T");
            builder.append(" LEFT JOIN CATEGORY T0 ON T.\"CATEGORY_ID\"=T0.\"_id\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected Book loadCurrentDeep(Cursor cursor, boolean lock) {
        Book entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        Category category = loadCurrentOther(daoSession.getCategoryDao(), cursor, offset);
         if(category != null) {
            entity.setCategory(category);
        }

        return entity;    
    }

    public Book loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<Book> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<Book> list = new ArrayList<Book>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<Book> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<Book> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}