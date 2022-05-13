package com.nagico.bookstore.models.bean;

import org.greenrobot.greendao.annotation.*;

import java.util.Date;
import org.greenrobot.greendao.DaoException;
import com.nagico.bookstore.models.dao.DaoSession;
import com.nagico.bookstore.models.dao.BookDao;
import com.nagico.bookstore.models.dao.OrderItemDao;

@Entity
public class OrderItem {
    @Id(autoincrement = true)
    private Long id;

    private Long orderId;

    @NotNull
    private Long bookId;

    @ToOne(joinProperty = "bookId")
    private Book book;

    @Property
    private int quantity;

    @Property
    private double price;

    @Property
    private Date CreatedAt;

    @Property
    private Date UpdatedAt;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 681926587)
    private transient OrderItemDao myDao;

    @Generated(hash = 1888284401)
    public OrderItem(Long id, Long orderId, @NotNull Long bookId, int quantity,
            double price, Date CreatedAt, Date UpdatedAt) {
        this.id = id;
        this.orderId = orderId;
        this.bookId = bookId;
        this.quantity = quantity;
        this.price = price;
        this.CreatedAt = CreatedAt;
        this.UpdatedAt = UpdatedAt;
    }

    @Generated(hash = 403153068)
    public OrderItem() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return this.orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getBookId() {
        return this.bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getCreatedAt() {
        return this.CreatedAt;
    }

    public void setCreatedAt(Date CreatedAt) {
        this.CreatedAt = CreatedAt;
    }

    public Date getUpdatedAt() {
        return this.UpdatedAt;
    }

    public void setUpdatedAt(Date UpdatedAt) {
        this.UpdatedAt = UpdatedAt;
    }

    @Generated(hash = 893611298)
    private transient Long book__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 577500745)
    public Book getBook() {
        Long __key = this.bookId;
        if (book__resolvedKey == null || !book__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            BookDao targetDao = daoSession.getBookDao();
            Book bookNew = targetDao.load(__key);
            synchronized (this) {
                book = bookNew;
                book__resolvedKey = __key;
            }
        }
        return book;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1979200873)
    public void setBook(@NotNull Book book) {
        if (book == null) {
            throw new DaoException(
                    "To-one property 'bookId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.book = book;
            bookId = book.getId();
            book__resolvedKey = bookId;
        }
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 496685728)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getOrderItemDao() : null;
    }
}
