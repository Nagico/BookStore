package com.nagico.bookstore.models;

import org.greenrobot.greendao.annotation.*;

import java.util.Date;
import java.util.List;
import org.greenrobot.greendao.DaoException;
import com.nagico.bookstore.dao.DaoSession;
import com.nagico.bookstore.dao.OrderItemDao;
import com.nagico.bookstore.dao.UserDao;
import com.nagico.bookstore.dao.OrderDao;

@Entity
public class Order {
    @Id(autoincrement = true)
    private Long id;

    @NotNull
    private Long userId;

    @ToOne(joinProperty = "userId")
    private User user;

    @ToMany(referencedJoinProperty = "orderId")
    private List<OrderItem> orderItems;

    private String paymentMethod;

    @Property
    private Date CreatedAt;

    @Property
    private Date PaidAt;

    @Property
    private Date UpdatedAt;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 949219203)
    private transient OrderDao myDao;

    @Generated(hash = 2123151073)
    public Order(Long id, @NotNull Long userId, String paymentMethod, Date CreatedAt, Date PaidAt,
            Date UpdatedAt) {
        this.id = id;
        this.userId = userId;
        this.paymentMethod = paymentMethod;
        this.CreatedAt = CreatedAt;
        this.PaidAt = PaidAt;
        this.UpdatedAt = UpdatedAt;
    }

    @Generated(hash = 1105174599)
    public Order() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Generated(hash = 251390918)
    private transient Long user__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 859885876)
    public User getUser() {
        Long __key = this.userId;
        if (user__resolvedKey == null || !user__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            UserDao targetDao = daoSession.getUserDao();
            User userNew = targetDao.load(__key);
            synchronized (this) {
                user = userNew;
                user__resolvedKey = __key;
            }
        }
        return user;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 462495677)
    public void setUser(@NotNull User user) {
        if (user == null) {
            throw new DaoException(
                    "To-one property 'userId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.user = user;
            userId = user.getId();
            user__resolvedKey = userId;
        }
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 847786723)
    public List<OrderItem> getOrderItems() {
        if (orderItems == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            OrderItemDao targetDao = daoSession.getOrderItemDao();
            List<OrderItem> orderItemsNew = targetDao._queryOrder_OrderItems(id);
            synchronized (this) {
                if (orderItems == null) {
                    orderItems = orderItemsNew;
                }
            }
        }
        return orderItems;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1029058822)
    public synchronized void resetOrderItems() {
        orderItems = null;
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
    @Generated(hash = 965731666)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getOrderDao() : null;
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

    public Date getPaidAt() {
        return this.PaidAt;
    }

    public void setPaidAt(Date PaidAt) {
        this.PaidAt = PaidAt;
    }

    public String getPaymentMethod() {
        return this.paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
