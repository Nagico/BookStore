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
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import com.nagico.bookstore.models.entity.Book;

import com.nagico.bookstore.models.entity.OrderItem;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "ORDER_ITEM".
*/
public class OrderItemDao extends AbstractDao<OrderItem, Long> {

    public static final String TABLENAME = "ORDER_ITEM";

    /**
     * Properties of entity OrderItem.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property OrderId = new Property(1, Long.class, "orderId", false, "ORDER_ID");
        public final static Property CartId = new Property(2, Long.class, "cartId", false, "CART_ID");
        public final static Property BookId = new Property(3, Long.class, "bookId", false, "BOOK_ID");
        public final static Property Quantity = new Property(4, int.class, "quantity", false, "QUANTITY");
        public final static Property Price = new Property(5, double.class, "price", false, "PRICE");
        public final static Property CreatedAt = new Property(6, java.util.Date.class, "CreatedAt", false, "CREATED_AT");
        public final static Property UpdatedAt = new Property(7, java.util.Date.class, "UpdatedAt", false, "UPDATED_AT");
    }

    private DaoSession daoSession;

    private Query<OrderItem> order_OrderItemsQuery;
    private Query<OrderItem> user_CartQuery;

    public OrderItemDao(DaoConfig config) {
        super(config);
    }
    
    public OrderItemDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ORDER_ITEM\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"ORDER_ID\" INTEGER," + // 1: orderId
                "\"CART_ID\" INTEGER," + // 2: cartId
                "\"BOOK_ID\" INTEGER NOT NULL ," + // 3: bookId
                "\"QUANTITY\" INTEGER NOT NULL ," + // 4: quantity
                "\"PRICE\" REAL NOT NULL ," + // 5: price
                "\"CREATED_AT\" INTEGER," + // 6: CreatedAt
                "\"UPDATED_AT\" INTEGER);"); // 7: UpdatedAt
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ORDER_ITEM\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, OrderItem entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long orderId = entity.getOrderId();
        if (orderId != null) {
            stmt.bindLong(2, orderId);
        }
 
        Long cartId = entity.getCartId();
        if (cartId != null) {
            stmt.bindLong(3, cartId);
        }
        stmt.bindLong(4, entity.getBookId());
        stmt.bindLong(5, entity.getQuantity());
        stmt.bindDouble(6, entity.getPrice());
 
        java.util.Date CreatedAt = entity.getCreatedAt();
        if (CreatedAt != null) {
            stmt.bindLong(7, CreatedAt.getTime());
        }
 
        java.util.Date UpdatedAt = entity.getUpdatedAt();
        if (UpdatedAt != null) {
            stmt.bindLong(8, UpdatedAt.getTime());
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, OrderItem entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long orderId = entity.getOrderId();
        if (orderId != null) {
            stmt.bindLong(2, orderId);
        }
 
        Long cartId = entity.getCartId();
        if (cartId != null) {
            stmt.bindLong(3, cartId);
        }
        stmt.bindLong(4, entity.getBookId());
        stmt.bindLong(5, entity.getQuantity());
        stmt.bindDouble(6, entity.getPrice());
 
        java.util.Date CreatedAt = entity.getCreatedAt();
        if (CreatedAt != null) {
            stmt.bindLong(7, CreatedAt.getTime());
        }
 
        java.util.Date UpdatedAt = entity.getUpdatedAt();
        if (UpdatedAt != null) {
            stmt.bindLong(8, UpdatedAt.getTime());
        }
    }

    @Override
    protected final void attachEntity(OrderItem entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public OrderItem readEntity(Cursor cursor, int offset) {
        OrderItem entity = new OrderItem( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // orderId
            cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2), // cartId
            cursor.getLong(offset + 3), // bookId
            cursor.getInt(offset + 4), // quantity
            cursor.getDouble(offset + 5), // price
            cursor.isNull(offset + 6) ? null : new java.util.Date(cursor.getLong(offset + 6)), // CreatedAt
            cursor.isNull(offset + 7) ? null : new java.util.Date(cursor.getLong(offset + 7)) // UpdatedAt
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, OrderItem entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setOrderId(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setCartId(cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));
        entity.setBookId(cursor.getLong(offset + 3));
        entity.setQuantity(cursor.getInt(offset + 4));
        entity.setPrice(cursor.getDouble(offset + 5));
        entity.setCreatedAt(cursor.isNull(offset + 6) ? null : new java.util.Date(cursor.getLong(offset + 6)));
        entity.setUpdatedAt(cursor.isNull(offset + 7) ? null : new java.util.Date(cursor.getLong(offset + 7)));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(OrderItem entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(OrderItem entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(OrderItem entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "orderItems" to-many relationship of Order. */
    public List<OrderItem> _queryOrder_OrderItems(Long orderId) {
        synchronized (this) {
            if (order_OrderItemsQuery == null) {
                QueryBuilder<OrderItem> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.OrderId.eq(null));
                order_OrderItemsQuery = queryBuilder.build();
            }
        }
        Query<OrderItem> query = order_OrderItemsQuery.forCurrentThread();
        query.setParameter(0, orderId);
        return query.list();
    }

    /** Internal query to resolve the "cart" to-many relationship of User. */
    public List<OrderItem> _queryUser_Cart(Long cartId) {
        synchronized (this) {
            if (user_CartQuery == null) {
                QueryBuilder<OrderItem> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.CartId.eq(null));
                user_CartQuery = queryBuilder.build();
            }
        }
        Query<OrderItem> query = user_CartQuery.forCurrentThread();
        query.setParameter(0, cartId);
        return query.list();
    }

    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getBookDao().getAllColumns());
            builder.append(" FROM ORDER_ITEM T");
            builder.append(" LEFT JOIN BOOK T0 ON T.\"BOOK_ID\"=T0.\"_id\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected OrderItem loadCurrentDeep(Cursor cursor, boolean lock) {
        OrderItem entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        Book book = loadCurrentOther(daoSession.getBookDao(), cursor, offset);
         if(book != null) {
            entity.setBook(book);
        }

        return entity;    
    }

    public OrderItem loadDeep(Long key) {
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
    public List<OrderItem> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<OrderItem> list = new ArrayList<OrderItem>(count);
        
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
    
    protected List<OrderItem> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<OrderItem> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
